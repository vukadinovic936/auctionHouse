package com.aubgteam.auctionhouse.Models;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
//Model for the table in the Image
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int image_id;

    private String image_type;

    private Blob image;

    private String image_size;

    private String image_ctgy;

    private String image_name;
}
