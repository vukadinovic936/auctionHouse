package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Services.UserInfoService;
import com.aubgteam.auctionhouse.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UserInfoController {

    @Autowired
    UserInfoService service;

    @GetMapping("/admin/userInfo")
    public String userInfo(Model model) {
        model.addAttribute("users",service.findAll());
    return "/admin/userInfo";
    }
}
