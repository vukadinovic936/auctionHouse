package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.cache.spi.TimestampsRegion;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(schema = "auctiondb")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long item_id;

    private String name;


    @ManyToOne
    @JoinColumn
    private Category category_id;


    private double reserve_price;

    @ManyToOne
    @JoinColumn
    private User sellerId;

    private double evaluation;


//    private String image_URL;


//    @Lob
    private String description;



    @OneToOne(mappedBy = "approved_item", cascade = CascadeType.ALL)
    private ApprovedItem approvedItem;


    @OneToOne
    @JoinColumn
    private Image image;

    @CreationTimestamp
    @Column(name = "CREATE_STAMP", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;


}
