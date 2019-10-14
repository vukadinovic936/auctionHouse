package com.aubgteam.auctionhouse.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long credit_card_id;

    private Date expire_date;

    private String cvv;

    private String owner_name;

    private String billing_adress;
}
