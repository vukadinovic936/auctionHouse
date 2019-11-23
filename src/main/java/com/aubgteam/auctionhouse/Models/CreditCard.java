package com.aubgteam.auctionhouse.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
public class CreditCard {

    CreditCard(){}

    public CreditCard(String credit_card_number, Date expire_date,String cvv, String owner_name, String billing_address){
        this.credit_card_number=credit_card_number;
        this.expire_date=expire_date;
        this.cvv=cvv;
        this.owner_name=owner_name;
        this.billing_address=billing_address;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="credit_card_id")
    private long credit_card_id;

    private String credit_card_number;

    private Date expire_date;

    private String cvv;

    private String owner_name;

    private String billing_address;

    private double amount=0;

    private double pending_amount=0;

}
