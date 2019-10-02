package com.aubgteam.auctionhouse.Models;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int credit_card_id;

    private Date exppire_date;

    private String cvv;

    private String owner_name;

    private String billing_adress;
}
