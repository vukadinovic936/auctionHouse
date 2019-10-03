package com.aubgteam.auctionhouse.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private String username;

    private String name;

    private String email;

    @OneToOne
    private CreditCard credit_card;

    private String password;

    private boolean is_admin;


}
