package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.BidForm;
import com.aubgteam.auctionhouse.Models.Category;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Services.ApprovedItemService;
import com.aubgteam.auctionhouse.Services.CategoryService;
import com.aubgteam.auctionhouse.Services.ItemService;
import com.aubgteam.auctionhouse.Services.UserService;
import org.apache.tomcat.util.net.AprEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@Controller
public class BiddingController {
    @Autowired
    ItemService itemService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @RequestMapping("/item/{id}")
    public String bidItem(@PathVariable (name="id") int id, Model model) {
        //GetItem by searching with id
        Item item = itemService.get(id);
        ApprovedItem approvedItem = item.getApprovedItem();

        // highest bid ( or reserve price if there is no bid )
        double highest_bid=item.getReserve_price();
        if(item.getReserve_price()<item.getEvaluation()){
            highest_bid=item.getEvaluation();
        }
        // if Highest bidder is null print No bids yet, else display the bidder
        String highest_bidder=" ";
        if(item.getHighestBidder() !=null) {
            highest_bidder=item.getHighestBidder().getUsername();
        }else {
            highest_bidder="No bids yet" ;
        }
        //Get the current date
        Calendar cal = Calendar.getInstance();
        Date curDate=cal.getTime();
        // three bid states -1 if not started , 0 if in progress , 1 if already over
        int bid_state=-1;
        if(approvedItem.getStart_date().compareTo(curDate)>0){
            bid_state=-1;
        }else if( approvedItem.getStart_date().compareTo(curDate)<=0 && curDate.compareTo(approvedItem.getEnd_date())<0) {
            bid_state=0;
        }else{
            bid_state=1;
        }
        // Add all the necessary properties to display
        model.addAttribute("name",item.getName());
        model.addAttribute("seller", item.getSellerId().getName());
        model.addAttribute("reservation_price",item.getReserve_price());
        model.addAttribute("evaluation_price",item.getEvaluation());
        model.addAttribute("image",item.getImage());
        model.addAttribute("description",item.getDescription());
        model.addAttribute("category",item.getCategory_id().getCategory_name());
        model.addAttribute("highest_bid",highest_bid);
        model.addAttribute("highest_bidder",highest_bidder);
        model.addAttribute("bid_form",new BidForm());
        model.addAttribute("item_id",item.getItem_id());
        model.addAttribute("bid_state",bid_state);
        model.addAttribute("start_date",item.getApprovedItem().getStart_date().toString());
        model.addAttribute("end_date",item.getApprovedItem().getEnd_date().toString());
        return "bid_item" ;
    }
    @RequestMapping("/bid_item/{id}")
    String bidItem(@ModelAttribute("bid_form") BidForm bidForm, @PathVariable (name="id") int id, Model model){
        Item it= itemService.get(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";
        if (principal instanceof UserDetails) {

            username = ((UserDetails)principal).getUsername();

        } else {

            username = principal.toString();

        }
        //change items highest bidder and evaluation price
        it.setHighestBidder(userService.findByUsername(username));
        it.setEvaluation(bidForm.getNew_offer());
        //save item
        itemService.save(it);

        return "redirect:/item/{id}";
    }
}
