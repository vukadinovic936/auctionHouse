package com.aubgteam.auctionhouse.Repositories;

import com.aubgteam.auctionhouse.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
//Repository for the model user
//Function findByUsername(our case ID)
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

