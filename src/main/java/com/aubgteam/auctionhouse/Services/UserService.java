package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
