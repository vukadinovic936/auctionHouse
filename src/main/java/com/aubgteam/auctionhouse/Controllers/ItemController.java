package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Repositories.CategoryRepository;
import com.aubgteam.auctionhouse.Services.ApprovedItemService;
import com.aubgteam.auctionhouse.Services.ImageService;
import com.aubgteam.auctionhouse.Services.ItemService;
import com.aubgteam.auctionhouse.Services.UserServiceImpl;
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


    @RequestMapping("/admin/items_admin")
    public String viewItemAsAdmin(Model model)
     {
        List<Item> listOfItems = itemService.listAll();
        model.addAttribute("listOfItems", listOfItems);
        model.addAttribute("imageService", imageService);

        return "/admin/items_admin";
    }

    @RequestMapping("/new/{username}")
    public String showNewItemPage(Model model, @PathVariable(name="username") String username) {
        Item item = new Item();
        model.addAttribute("item", item);

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("username", username);
        return "new_item";
    }


    @RequestMapping(value = {"/admin/save_item/{username}","/admin/save_item/"}, method = RequestMethod.POST)
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
                }

            }
            else
            {
                Item tempItem = itemService.get(item.getItem_id());
                item.setImage(tempItem.getImage());
                item.setSellerId(tempItem.getSellerId());
            }



                itemService.save(item);

                return "redirect:/";

        }
        catch (Exception e) {
            return "1";
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
        approvedItemService.delete(id);
        itemService.delete(id);
        return "redirect:/admin/items_admin";
    }
}
