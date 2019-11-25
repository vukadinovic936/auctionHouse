package com.aubgteam.auctionhouse.Models;

import lombok.Data;

@Data
public class WinnerInfo {
    private String username;
    private String itemName;
    private String email;
    private double price;

}
