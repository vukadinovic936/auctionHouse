package com.aubgteam.auctionhouse.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int follow_id;
    private long user_id;

    private long item_id;
    public Follow(){

    }
    public Follow(long user_id,long item_id){
        this.user_id=user_id;
        this.item_id=item_id;
    }
}
