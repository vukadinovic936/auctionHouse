package com.aubgteam.auctionhouse.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Role {
@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
