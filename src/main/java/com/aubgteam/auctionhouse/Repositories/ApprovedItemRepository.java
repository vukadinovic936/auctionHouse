package com.aubgteam.auctionhouse.Repositories;
import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovedItemRepository extends JpaRepository<ApprovedItem, Long>{

    @Query("select e from ApprovedItem e INNER JOIN Item b ON b.item_id = e.approved_item_id WHERE b.name LIKE %?1% OR b.description LIKE %?1% OR b.evaluation LIKE %?1% OR b.reserve_price LIKE %?1%")
   public List<ApprovedItem> search(@Param("keyword") String keyword);

}
