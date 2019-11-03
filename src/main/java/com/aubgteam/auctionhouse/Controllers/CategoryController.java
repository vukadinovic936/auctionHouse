package com.aubgteam.auctionhouse.Controllers;

import com.aubgteam.auctionhouse.Models.Category;
import com.aubgteam.auctionhouse.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller                                 /* indicates that this is a controller class */
public class CategoryController {
    @Autowired
    private CategoryService service;        /*reference to an instance of CategoryService */


    //implementation of the handler methods

    //handles requests to view the homepage
    @RequestMapping("/categories")                                            /* annotation from Spring MVC and the URL */
    public String viewHomePage(Model model)                        /* declare model parameter from Spring MVC */
    {
        List<Category> listCategories = service.listAll();         /* in the home page a list of categories is displayed */
        model.addAttribute("listCategories", listCategories);   /* set object listCategories to the model */
        return ("categories");                                           /* returns the name of the view */
    }

    /* handler method for creating a new category */
    @RequestMapping("/admin/new_category")                               /*mapping the method to a URL */
    public String showNewCategoryForm(Model model)
    {
        Category category = new Category();              /* new category object */
        model.addAttribute("category", category);    /* set the object as a new model attribute */
        return "/admin/new_category";                          /* returns the html page with this name */
    }

    /* handler methods for the save actions when creating a new category */
    @RequestMapping(value = "/admin/save_category", method = RequestMethod.POST)                   // URL and method specification
    public String saveCategory(@ModelAttribute("category") @Valid Category category, BindingResult binding_result)     // binds a method parameter to a named model attribute and then exposes it to a web view
    {


        if(binding_result.hasErrors())
        {
            return "/admin/new_category";
        }
        else
        {
            service.save(category);
        }
        return "redirect:/";                                                    // redirect stream that redirects to the home page
    }

    //handler method for edit
    @RequestMapping("/admin/edit_category/{id}")
    public ModelAndView showEditCategoryForm(@PathVariable(name = "id") Long id)  //@PathVariable identifies the pattern that is used in the URI for the incoming request
    {
        ModelAndView mav = new ModelAndView("/admin/edit_category");        //create a model and view object

        Category category = service.get(id);                                //gets the details of the selected category by id
        mav.addObject("category", category);                  //add the category object to the model and view
        return mav;                                                        //return the model and view object
    }

    //handler method for delete
    @RequestMapping("/admin/delete_category/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id)    //read the product id
    {
        service.delete(id);               //call service.delete
        return "redirect:/categories";              //redirect to the home page after the category has been deleted
    }
}
