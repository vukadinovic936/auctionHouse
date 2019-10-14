package com.aubgteam.auctionhouse.Repositories;
import com.aubgteam.auctionhouse.Models.Category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}