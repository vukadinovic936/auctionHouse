package com.aubgteam.auctionhouse.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data

@Table(schema = "auctiondb")
//Model for the table User
public class User {
    public User(){}
    public User(String username,String name,String email,String password,String passwordConfirm){
        this.username=username;
        this.name=name;
        this.email=email;
        this.password=password;
        this.passwordConfirm=passwordConfirm;
        this.credit_card=credit_card;
        this.is_admin=is_admin;
    }
    public User(Long id,String username,String name,String email) {
        this.id=id;
        this.username=username;
        this.name=name;
        this.email=email;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    @Transient
    private String passwordConfirm;

    @OneToOne (cascade = CascadeType.ALL)
    private CreditCard credit_card;

//    @OneToMany(mappedBy = "sellerId", cascade = CascadeType.ALL)
//    private List <Item> postedItems;

    private String password;

    private int is_admin;


}
