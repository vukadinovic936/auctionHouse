package com.aubgteam.auctionhouse.Repositories;
import com.aubgteam.auctionhouse.Models.Category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.category_name LIKE %?1%")
    public List<Category> searchCategory(@Param("word") String word);

}