package com.aubgteam.auctionhouse.Models;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int item_id;

    @ManyToOne
    private Category category_id;

    private double initial_price;

    @ManyToOne
    private User seller_username;

    @OneToOne
    private Image image_id;

    @Lob
    private String description;
}
