package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Models.WinnerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WinnerService {

    @Autowired
    ApprovedItemService approvedItemService;

    @Autowired
    ItemService itemService;
    public List<WinnerInfo > getWinners() {
        List<WinnerInfo> winnerList= new ArrayList<WinnerInfo>();

        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "Europe/Helsinki" ) );  // Use proper "continent/region" time zone names; never use 3-4 letter codes like "EST" or "IST".
        java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate);
        List<ApprovedItem> list = approvedItemService.getSoldItems(sqlDate);
       for( ApprovedItem a : list){
            WinnerInfo w= new WinnerInfo();
           Item i = itemService.get(a.getApproved_item_id());
            w.setEmail(i.getHighestBidder().getEmail());
            w.setItemName(i.getName());
            w.setPrice(i.getEvaluation());
            w.setUsername(i.getHighestBidder().getUsername());
            winnerList.add(w);
       }
       return winnerList;
    }
}
