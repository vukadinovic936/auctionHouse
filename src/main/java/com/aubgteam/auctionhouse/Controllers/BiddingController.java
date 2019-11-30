package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.*;
import com.aubgteam.auctionhouse.Repositories.CreditCardRepository;
import com.aubgteam.auctionhouse.Services.*;
import org.apache.tomcat.util.net.AprEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class BiddingController {

    @Autowired
    ItemService itemService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    EmailSender emailSender;
    @Autowired
    ApprovedItemService approvedItemService;
    @Autowired
    CreditCardRepository ccRepository;

    MailService mailService;
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
        //Check if the user is following the item
        boolean match= followService.match(userService.findByUsername(userService.getLoggedInUsername()).getId(),item.getItem_id());

        CreditCard biddersCard = userService.findByUsername(userService.getLoggedInUsername()).getCredit_card();
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
        model.addAttribute("follow",match);
        model.addAttribute("max_amount", biddersCard.getAmount()-biddersCard.getPending_amount());
        return "bid_item" ;
    }
    @RequestMapping("/bid_item/{id}")
    String bidItem(@ModelAttribute("bid_form") BidForm bidForm, @PathVariable (name="id") int id, Model model){
        Item it= itemService.get(id);
        String username=userService.getLoggedInUsername();
        //change items highest bidder and evaluation price
//        if(userService.findByUsername(username).getCredit_card().getAmount()>=bidForm.getNew_offer()) {
            User u=it.getHighestBidder();
        if(u!=null)
        {
            CreditCard prevHighestBiddersCard = u.getCredit_card();
            prevHighestBiddersCard.setPending_amount(prevHighestBiddersCard.getPending_amount() - it.getEvaluation());
            ccRepository.save(prevHighestBiddersCard);
        }

            it.setHighestBidder(userService.findByUsername(username));
            it.setEvaluation(bidForm.getNew_offer());
            try {
                emailSender.newHighestBidderEmail(u,it);
            }catch (Exception e){
            }
            //save item
            itemService.save(it);

        CreditCard biddersCard = userService.findByUsername(userService.getLoggedInUsername()).getCredit_card();
        biddersCard.setPending_amount(biddersCard.getPending_amount()+bidForm.getNew_offer());
        ccRepository.save(biddersCard);
            return "redirect:/item/{id}";

//        else
//        {
//            return
//        }
    }

    @RequestMapping("/admin/charge")
    public String chargeBuyers()
    {
        Calendar cal = Calendar.getInstance();
        Date curDate=cal.getTime();

        // approved items whose auction end date is at most yesterday and have bids
        List<ApprovedItem> approvedItems =  approvedItemService.listAll();
        for (ApprovedItem appItem: approvedItems)
        {

            if(appItem.getEnd_date().compareTo(curDate)<0)
            {
                Item item = itemService.get(appItem.getApproved_item_id());
                if(item.getHighestBidder()!=null && item.getPaidFor()==0)
                {
                    User buyer = item.getHighestBidder();
                    CreditCard buyersCard = buyer.getCredit_card();
                    buyersCard.setAmount(buyersCard.getAmount() - item.getEvaluation());
                    buyersCard.setPending_amount(buyersCard.getPending_amount() - item.getEvaluation());
                    item.setPaidFor(1);
                    itemService.save(item);
                    ccRepository.save(buyersCard);

                }
            }

        }
        return "redirect:/welcome";
    }

    @RequestMapping("/admin/deleteSoldItems")
    public String deleteSoldItems()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date curDate=cal.getTime();

        // approved items whose auction end date is at most yesterday and have bids
        List<ApprovedItem> approvedItems =  approvedItemService.listAll();
        for (ApprovedItem appItem: approvedItems)
        {
            if(appItem.getEnd_date().compareTo(curDate)<0)
            {
                approvedItemService.delete(appItem.getApproved_item_id());
                itemService.delete(appItem.getApproved_item_id());
            }

        }
        return "redirect:/welcome";
    }

}
