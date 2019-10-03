package com.aubgteam.auctionhouse.Repositories;
        import com.aubgteam.auctionhouse.Models.CreditCard;
        import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<CreditCard, Long> {

}
