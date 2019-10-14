package com.aubgteam.auctionhouse.Repositories;

        import com.aubgteam.auctionhouse.Models.Item;

        import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

}
