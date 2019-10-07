package com.aubgteam.auctionhouse.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    private String name;

    private String email;

    @Transient
    private String passwordConfirm;

    @OneToOne
    private CreditCard credit_card;

    private String password;

    private int is_admin;


}
