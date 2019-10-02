package com.aubgteam.auctionhouse.Models;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int item_id;

    private int category_id;

    private double initial_price;

    private String seller_username;

    private int image_id;

    @Lob
    private String description;
}
