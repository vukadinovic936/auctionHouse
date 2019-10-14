package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.ApprovedItem;
import com.aubgteam.auctionhouse.Models.Image;
import com.aubgteam.auctionhouse.Models.Item;
import com.aubgteam.auctionhouse.Repositories.ApprovedItemRepository;
import com.aubgteam.auctionhouse.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ApprovedItemRepository approvedItemRepository;

    public List<Item> listAll() {
        return itemRepository.findAll();
    }

    public void save(Item item) {

        itemRepository.save(item);
    }

    public Item get(long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public void saveApprovedItem(Item item, ApprovedItem approvedItem)
    {
//        approvedItem.setApproved_item_id(item.getItem_id());
//        item.setApprovedItem(approvedItem);
        itemRepository.save(item);
        approvedItem.setApproved_item(item);
        approvedItemRepository.save(approvedItem);
    }

}
