package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Repositories.CategoryRepository;
import com.aubgteam.auctionhouse.Services.ImageService;
import com.aubgteam.auctionhouse.Services.ItemService;
import com.aubgteam.auctionhouse.Services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

//    @PostMapping(path="/") // Map ONLY POST Requests
//    public @ResponseBody
//    String addNewItem ( @RequestParam Category category_id, @RequestParam double initial_price, @RequestParam String description) {
//
//        Item i  = new Item();
//        i.setReserve_price(initial_price);
//        i.setDescription(description);
////        i.setCategory_id(category_id);
//
//
//        itemRepository.save(i);
//
//
//
//        return "Saved";
//    }
//
//    @GetMapping(path="/items/all")
//    public @ResponseBody Iterable<Item> getAllItems() {
//        // This returns a JSON or XML with the users
//        return itemRepository.findAll();
//    }

    @RequestMapping("/items/{username}")
    public String viewItemHomePage(Model model, @PathVariable(name = "username") String username)
     {
        List<Item> listOfItems = itemService.listAll();
        model.addAttribute("listOfItems", listOfItems);
        model.addAttribute("username",username);
        model.addAttribute("imageService", imageService);

        return "items";
    }

//    @RequestMapping("/t")
//            public String
//    {
//
//    }


    @RequestMapping("/new/{username}")
    public String showNewItemPage(Model model, @PathVariable(name="username") String username) {
        Item item = new Item();
//        Image image = new Image();
        model.addAttribute("item", item);
////        model.addAttribute("image", image);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("username", username);
        return "new_item";
    }

//    @RequestMapping("/cat")
//    public String showNewItemPae(Model model) {
//
//        model.addAttribute("categories", categoryRepository.findAll());
//        return "index";
//    }

    @RequestMapping(value = "/save_item/{username}", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("imagePath") MultipartFile imagePath, @ModelAttribute("item") Item item, @PathVariable(name = "username")String username) {

        try
        {
            String t= StringUtils.cleanPath(imagePath.getOriginalFilename());

            if(!t.equals("")) {
                if (item.getItem_id() != 0 && itemService.get(item.getItem_id()).getImage() != null) {
                    imageService.deleteImage(itemService.get(item.getItem_id()).getImage().getId());
                }
                Image savedImage = imageService.storeImage(imagePath);
//                item.setSellerId(userService.findByUsername(username));

                item.setImage(savedImage);
                itemService.save(item);
            }
                return "redirect:/";

        }
        catch (Exception e) {
            return "1";
        }
    }

//    @RequestMapping(value = "/save_item", method = RequestMethod.POST)
//    public String saveImage(@ModelAttribute("imagePath") MultipartFile imagePath)  {
//
//        try{
////           if(imagePath.isEmpty())
//            imageService.storeImage(imagePath);
////        itemService.save(item);
//
//            return "redirect:/";}
//        catch (IOException e)
//        {
//            return "1";
//        }
//    }


    @RequestMapping("/edit/{id}/{username}")
    public ModelAndView showEditItemPage(@PathVariable(name = "id") long id, @PathVariable(name = "username") String username) {
        ModelAndView mav = new ModelAndView("edit_item");
        Item item = itemService.get(id);
        mav.addObject("item", item);
        mav.addObject("username",username);
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteItem(@PathVariable(name = "id") int id) {
        itemService.delete(id);
        return "redirect:/";
    }
}
