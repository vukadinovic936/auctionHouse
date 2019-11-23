package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Follow;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Services.FollowService;
import com.aubgteam.auctionhouse.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FavouriteItemsController {
@Autowired
    FollowService followService;
@Autowired
    UserService userService;

@RequestMapping("/favourite_items")
String favouriteItems(Model model)
{

    followService.getFavourites(userService.getLoggedInId());
    List<ApprovedItem> listOfItems = followService.getFavourites(userService.getLoggedInId());
    model.addAttribute("listOfItems",listOfItems);
    return "/favourite_items";
}
}
