package com.aubgteam.auctionhouse.Services;

import com.aubgteam.auctionhouse.Models.Category;
import com.aubgteam.auctionhouse.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service                                 //this annotation implies that this class is at the service layer
public class CategoryService {
    @Autowired                           //Spring Framework automatically injects an instant of the category repository
    private CategoryRepository repo;    //refers to the category repository


    //implementation of the basic CRUD methods
    //they are already defined in Spring so no need to write any implementation code
    public List<Category> listAll()
    {
        return repo.findAll();
    }            //return a list of categories

    public void save(Category category)
    {
        repo.save(category);
    }

    public Category get(Long id)
    {
        return repo.findById(id).get();
    }    //returns the actual value object

    public void delete (Long id)
    {
        repo.deleteById(id);
    }
}
