package com.aubgteam.auctionhouse.Models;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.util.List;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(exclude = "items")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull(message = "ID is mandatory")
    @Range(min=1, max = 2147, message = "Size must be between 1 and 2147")
    @Column(name = "category_id", unique = true)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    @Size(min=1, max=255, message = "Size must be between 1 and 255 characters ")
    private String category_name;

    @OneToMany(mappedBy = "category_id")
    private List<Item> items;




}
