package com.aubgteam.auctionhouse.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int follow_id;

    private long userId;

    private long itemId;
    public Follow(){

    }
    public Follow(long user_id,long item_id){
        this.userId=user_id;
        this.itemId=item_id;
    }
}
