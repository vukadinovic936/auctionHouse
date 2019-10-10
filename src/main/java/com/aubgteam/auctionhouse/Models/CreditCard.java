package com.aubgteam.auctionhouse.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
//Model for the table in the CreditCard
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String credit_card_id;

    private Date exppire_date;

    private String cvv;

    private String owner_name;

    private String billing_adress;
}
