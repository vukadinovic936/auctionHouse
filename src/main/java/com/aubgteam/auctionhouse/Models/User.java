package com.aubgteam.auctionhouse.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private String username;

    private String name;

    private String email;

    private String credit_card;

    private String password;

    private boolean is_admin;


}
