package com.aubgteam.auctionhouse.Service;

public interface SecurityService {
    // Display a username of LoggedInUser
    String findLoggedInUsername();
    //Put parameters username and password and login
    void autoLogin(String username, String password);
}
