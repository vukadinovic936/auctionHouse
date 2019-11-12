package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Follow;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Repositories.FollowRepsitory;
import com.aubgteam.auctionhouse.Repositories.ItemRepository; import com.aubgteam.auctionhouse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void save(Follow f) {

        followRepsitory.save(f);

    }

    public Follow get(long id) {
        return followRepsitory.findById(id).orElse(null);
    }

    public void delete(long id) {
        followRepsitory.deleteById(id);
    }

    public boolean match(long user_id, long item_id) {
        List<Follow> temp = followRepsitory.findAll();
        for (Follow f : temp) {
            if (f.getUser_id() == user_id && f.getItem_id() == item_id) {
                return true;
            }
        }
        return false;
    }

    public List<String> getFollowersEmails() {
        Calendar cal = Calendar.getInstance();
        Date currentdate=cal.getTime();
        List<Follow> temp = followRepsitory.findAll();
        List<String> myList = new ArrayList<>();
        for (Follow f : temp) {
            if (approvedItemService.get(f.getItem_id()).getStart_date().compareTo(currentdate)<=0) {
                myList.add(userService.findById(f.getUser_id()).getEmail());
            }
        }
        return myList;
    }
}
