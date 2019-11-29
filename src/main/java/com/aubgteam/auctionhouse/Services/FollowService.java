package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.*;
import com.aubgteam.auctionhouse.Repositories.FollowRepsitory;
import com.aubgteam.auctionhouse.Repositories.ItemRepository; import com.aubgteam.auctionhouse.Repositories.UserRepository;
//import javax.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepsitory followRepsitory;
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovedItemService approvedItemService;

    @Autowired
    private ItemService itemService;
    public void save(Follow f) {

        followRepsitory.save(f);

    }

    public Follow get(long id) {
        return followRepsitory.findById(id).orElse(null);
    }
    public List<Follow> findByuser_id(long user_id) { return followRepsitory.findByuserId(user_id);}

    public void delete(long id) {
        followRepsitory.deleteById(id);
    }

    public boolean match(long user_id, long item_id) {
        List<Follow> temp = followRepsitory.findByUserAndItem(user_id,item_id);
        if(temp.size()==0) return false;
        return true;
    }

    public List<ApprovedItem> getFavourites(long userId) {
        List<Follow> allFollows=this.findByuser_id(userId);
        List<ApprovedItem> favourites = new ArrayList<ApprovedItem>();
        for(Follow f : allFollows){
            favourites.add(approvedItemService.get(f.getItemId()));
        }
        return favourites;
    }
    public List<Tuple> getFollowersEmails() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date currentdate = new Date(System.currentTimeMillis());
        String curDateStr= (formatter.format(currentdate));
        List<Follow> temp = followRepsitory.findAll();
        List<Tuple> myList = new ArrayList<>();
        for (Follow f : temp) {
            if (approvedItemService.get(f.getItemId()).getStart_date().toString().equals(curDateStr)) {
                Tuple t = new Tuple();

                t.setEmail(userService.findById(f.getUserId()).getEmail());

                t.setUserName(userService.findById(f.getUserId()).getUsername());

                t.setItemName( itemService.get(f.getItemId()).getName());

                t.setItemLink(""+f.getItemId());
                myList.add(t);
            }
        }
        return myList;
    }
}
