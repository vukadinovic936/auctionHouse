package com.aubgteam.auctionhouse.Repositories;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Category;
import com.aubgteam.auctionhouse.Models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface FollowRepsitory extends JpaRepository<Follow, Long>  {

    List<Follow> findByuserId(Long userId);

    @Query("select f from Follow f where f.userId LIKE ?1 AND f.itemId LIKE ?2")
    public List<Follow> findByUserAndItem(@Param("userId") Long userId, @Param("itemId") Long itemId);

}
