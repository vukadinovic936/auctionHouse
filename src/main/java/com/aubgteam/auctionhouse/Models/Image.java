package com.aubgteam.auctionhouse.Models;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String type;



    @Lob
    private String data;

    public Image(String fileName, String fileType, String data) {
        this.name = fileName;
        this.type = fileType;
        this.data = data;
    }

    @OneToOne(mappedBy = "image")
    private Item item;


    public Image() {

    }
}
