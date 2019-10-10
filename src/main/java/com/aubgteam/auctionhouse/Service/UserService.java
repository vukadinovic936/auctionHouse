package com.aubgteam.auctionhouse.Service;

import com.aubgteam.auctionhouse.Models.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
