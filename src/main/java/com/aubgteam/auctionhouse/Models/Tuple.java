package com.aubgteam.auctionhouse.Models;

import lombok.Data;

@Data
public class Tuple {
    public Tuple(String email,String userName,String itemName,String itemLink){
        this.email=email;
        this.userName= userName;
        this.itemLink=itemLink;
        this.itemName=itemName;
    }
    public Tuple(){

    }
    private String email;
    private String userName;
    private String itemName;
    private String itemLink;

}
