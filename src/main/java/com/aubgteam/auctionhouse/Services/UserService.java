package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.CreditCard;
import com.aubgteam.auctionhouse.Models.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
    User findById(long id);
    void deleteUser(Long id);
    void saveCreditCard(CreditCard card);
    List<User> getAllAdmins();
    String getLoggedInUsername();
    long getLoggedInId();
}
