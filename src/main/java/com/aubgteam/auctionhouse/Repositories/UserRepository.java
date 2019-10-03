package com.aubgteam.auctionhouse.Repositories;

import com.aubgteam.auctionhouse.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

