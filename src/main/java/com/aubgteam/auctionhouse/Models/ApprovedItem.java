package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Data
public class ApprovedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToOne
    private int approved_item;
    private Date start_date;
    private Date end_date;
    private double highest_price;
    @OneToOne
    private String highest_bidder;

}
