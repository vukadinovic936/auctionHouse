package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.hibernate.annotations.GeneratorType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
public class ApprovedItem {
    @Id
    private long approved_item_id;

    @OneToOne
    @JoinColumn
    @MapsId
    private Item approved_item;

    private Date start_date;
    private Date end_date;


    private double highest_price=0;

//    @OneToOne
//    @JoinColumn
//    private User highest_bidder;

}
