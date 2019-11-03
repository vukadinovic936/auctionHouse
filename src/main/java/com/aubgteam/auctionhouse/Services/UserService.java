package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.CreditCard;
import com.aubgteam.auctionhouse.Models.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
    void deleteUser(Long id);
    void saveCreditCard(CreditCard card);
}
