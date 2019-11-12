package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Models.*;
import com.aubgteam.auctionhouse.Repositories.CategoryRepository;
import com.aubgteam.auctionhouse.Repositories.ItemRepository;
import com.aubgteam.auctionhouse.Repositories.UserRepository;
import com.aubgteam.auctionhouse.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApprovedItemService approvedItemService;

    @Autowired
    private FollowService followService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;




//    @RequestMapping("/admin/items_admin")
//    public String viewItemAsAdmin(Model model)
    @RequestMapping({"/admin/items_admin/{pending}","/admin/items_admin/"})
    public String viewItemAsAdmin(Model model, @PathVariable(name="pending",required = false) boolean pending)
     {
         List<Item> listOfItems =  itemService.listAll();
         if(pending) {
             List<ApprovedItem> listOfApprovedItems = approvedItemService.listAll();

             for(ApprovedItem t: listOfApprovedItems)
             {
                 listOfItems.remove(itemService.get(t.getApproved_item_id()));
             }
         }
         model.addAttribute("term", "");
        model.addAttribute("listOfItems", listOfItems);
        model.addAttribute("imageService", imageService);
//        model.addAttribute("all", true);

        return "/admin/items_admin";
    }

    @RequestMapping("/new/{username}")
    public String showNewItemPage(Model model, @PathVariable(name="username") String username) throws Exception{
        Item item = new Item();



            model.addAttribute("item", item);

            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("username", username);
            return "new_item";

    }


    @RequestMapping(value = {"/save_item/{username}","/save_item/"}, method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("imagePath") MultipartFile imagePath, @ModelAttribute("item") Item item, @PathVariable(name = "username", required = false) String username) {

        try
        {
            String t= StringUtils.cleanPath(imagePath.getOriginalFilename());

            if(!t.equals("")) {
                if (item.getItem_id() != 0 && itemService.get(item.getItem_id()).getImage() != null) {
                    imageService.delete(itemService.get(item.getItem_id()).getImage().getId());

                }
                Image savedImage = imageService.save(imagePath);
                item.setImage(savedImage);
                if (username != null) {
                    item.setSellerId(userService.findByUsername(username));
//                   MailService.sendEmailToAdmin(userService.findByUsername(username), userService.getAllAdmins());
                }
                else
                {
                    item.setSellerId(itemService.get(item.getItem_id()).getSellerId());
                }

            }
            else
            {
                Item tempItem = itemService.get(item.getItem_id());
                item.setImage(tempItem.getImage());
                item.setSellerId(tempItem.getSellerId());
            }



                itemService.save(item);
            if(username!=null)
            {
                MailService.sendEmailToAdmin(userService.findByUsername(username), userService.getAllAdmins(), item.getItem_id());
            }

                return "redirect:/admin/items_admin/";

        }
        catch (Exception e) {
            return "redirect:/admin/items_admin";
        }
    }

    @RequestMapping("/admin/edit/{id}")
    public ModelAndView showEditItemPage(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("/admin/edit_item");
        Item item = itemService.get(id);
        mav.addObject("item", item);
//        mav.addObject("username",username);
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteItem(@PathVariable(name = "id") int id) {
        if(approvedItemService.get(id)!=null) {
            approvedItemService.delete(id);
        }
        itemService.delete(id);
        return "redirect:/admin/items_admin/";
    }

    @RequestMapping({"/admin/details/{id}"})
    public String viewDetailsOfItem(Model model, @PathVariable(name="id") int id)
    {
        Item item =  itemService.get(id);

        model.addAttribute("item", item);
        model.addAttribute("imageService", imageService);
//        model.addAttribute("all", true);

        return "/admin/details_item";
    }

    @RequestMapping(value = "/admin/search_pending/{term}")
    public String showMatchedItems(Model model, @PathVariable (name = "term") String term)
    {
        List<Item> listOfMatchedApprovedItems = itemService.search(term);

        model.addAttribute("term", term);
        model.addAttribute("imageService", imageService);
        model.addAttribute("listOfItems", listOfMatchedApprovedItems);
        return "/admin/items_admin";
    }

    @RequestMapping("/follow/{id}")
    public String followItem(@PathVariable(name="id") int id)
    {

        String username = userService.getLoggedInUsername();
        User user= userService.findByUsername(username);
        Item item = itemService.get(id);

        Follow follow = new Follow(user.getId(), item.getItem_id());
        followService.save(follow)        ;
        return "redirect:/item/{id}";
    }

}
