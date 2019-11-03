package com.aubgteam.auctionhouse.Controllers;
import com.aubgteam.auctionhouse.Models.CreditCard;
import com.aubgteam.auctionhouse.Models.RegistrationForm;
import com.aubgteam.auctionhouse.Models.User;
import com.aubgteam.auctionhouse.Repositories.CreditCardRepository;
import com.aubgteam.auctionhouse.Repositories.ItemRepository;
import com.aubgteam.auctionhouse.Services.SecurityService;
import com.aubgteam.auctionhouse.Services.UserService;
import com.aubgteam.auctionhouse.Validator.UserValidator;
import org.hibernate.validator.cfg.defs.CreditCardNumberDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    //load registration page with userForm displayed, if Submitted redirect to PostMapping(registration)
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new RegistrationForm());
        //registration .html file displays error if something wrong was entered in userForm since this is get mapping, there won't be any errors
        model.addAttribute("errors", " ");
        return "registration";
    }
    //Checks if all the fields are valid and if they are creates a new user in database
    //If there is an error reload the page and display the error
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") RegistrationForm userForm,BindingResult bindingResult,Model model ) {
        User userData= new User(userForm.getUsername(),userForm.getName(),userForm.getEmail(),userForm.getPassword(),userForm.getPasswordConfirm());
        CreditCard creditCardData= new CreditCard(userForm.getCredit_card_number(),userForm.getExpire_date(),userForm.getCvv(),userForm.getOwner_name(),userForm.getBilling_address());

        //perform validation
        if(bindingResult.hasErrors()){
            String errors="";
            User user = (User) userData;
            CreditCard card =(CreditCard) creditCardData;
            Errors binres=bindingResult;
            if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
                errors+="Please use between 6 and 32 characters.\n";
            }
            if (userService.findByUsername(user.getUsername()) != null) {
                errors+="Someone already has that username. Please try another one\n";
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(binres, "password", "NotEmpty");
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {

                errors +=" Try one with at least 8 characters.\n";
            }

            if (!user.getPasswordConfirm().equals(user.getPassword())) {
                binres.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
                errors +=" These password don't match \n" ;
            }
            model.addAttribute("errors",errors) ;
            //reload and display eror
            return "/registration";
        }else{
            //Create a new user in DB

            userData.setCredit_card(creditCardData);
            userService.save(userData);
            securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
            return "redirect:/welcome";
        }
    }

    @GetMapping("/login")
    //Login if there is no error
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/","/welcome"})
    //Welcome page for the users
    public String welcome(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";
        if (principal instanceof UserDetails) {

            username = ((UserDetails)principal).getUsername();

        } else {

            username = principal.toString();

        }
        model.addAttribute("currentUser", username);
        return "welcome";
    }
    @RequestMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable(name="id") Long id){
        userService.deleteUser(id);
    return "redirect:/admin/userInfo";
    }
}
