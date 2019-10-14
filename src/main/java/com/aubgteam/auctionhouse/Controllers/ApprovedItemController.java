package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Services.ApprovedItemService;
import com.aubgteam.auctionhouse.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
public class ApprovedItemController {

    @Autowired
    private ApprovedItemService approvedItemService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/approve/{id}")
    public String showNewApprovedItemPage(Model model, @PathVariable(name = "id") long item_id
    ) {
        ApprovedItem approvedItem1 = new ApprovedItem();
//        approvedItem1.setApproved_item(itemService.get(item_id));
        approvedItem1.setApproved_item_id(item_id);

        model.addAttribute("approvedItem", approvedItem1);
        model.addAttribute("item", itemService.get(item_id));

//
        return "new_approved_item";
    }

    @RequestMapping(value = "/save_approved_item", method = RequestMethod.POST)
    public String saveApprovedItem(@ModelAttribute("approved_item") ApprovedItem approvedItem //, @ModelAttribute("item") Item aItem
    ) {
//        approvedItem.setApproved_item_id(aItem.getItem_id());
//        approvedItem.setApproved_item(aItem);
        Item item = itemService.get(approvedItem.getApproved_item_id());
        itemService.saveApprovedItem(item, approvedItem);
        return "redirect:/";
//        return (Long.toString(approvedItem.getApproved_item_id()));
    }

    @RequestMapping("/approved_items")
    public String viewApprovedItemHomePage(Model model) {
        List<ApprovedItem> listOfApprovedItems = approvedItemService.listAll();
        model.addAttribute("listOfApprovedItems", listOfApprovedItems);
        return "approved_items";
    }
}
