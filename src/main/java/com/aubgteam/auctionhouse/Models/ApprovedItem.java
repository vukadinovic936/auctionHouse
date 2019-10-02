package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
public class ApprovedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int category_id;
    private String category_name;

}
