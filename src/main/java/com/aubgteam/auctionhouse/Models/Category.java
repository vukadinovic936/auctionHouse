package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import org.aspectj.lang.annotation.control.CodeGenerationHint;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
//Model for the table in the Category
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int category_id;

    private int category_name;
}
