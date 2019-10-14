package com.aubgteam.auctionhouse.Controllers;


import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/new_image")
    public String setNewImagePage(Model model)
    {
//        String imagePath = new String();
//        model.addAttribute("imagePath",imagePath);
        return "new_image";
    }

    @RequestMapping(value = "/save_new_image", method = RequestMethod.POST)
    public String saveImage(@ModelAttribute("imagePath") MultipartFile imagePath)  {

       try{
//           if(imagePath.isEmpty())
           imageService.storeImage(imagePath);
//        itemService.save(item);

        return "redirect:/";}
       catch (IOException e)
        {
            return "1";
        }
    }

    @RequestMapping("/images")
    public String viewItemHomePage(Model model) {
        Image image = imageService.getImage(2);
        model.addAttribute("imageD", image);
//        model.addAttribute("base64Path", Base64.getEncoder().encodeToString(image.getData()));
        return "image";
    }
}
