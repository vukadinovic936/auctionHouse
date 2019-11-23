package com.aubgteam.auctionhouse.Services;
import com.aubgteam.auctionhouse.Models.CreditCard;
import com.aubgteam.auctionhouse.Models.User;
import com.aubgteam.auctionhouse.Repositories.CreditCardRepository;
import com.aubgteam.auctionhouse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditCardRepository cardRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    //Write user in DB
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public void deleteUser(Long id){

        userRepository.deleteById(id);
    }

    public void saveCreditCard(CreditCard card){
        cardRepository.save(card);
    }
    public String getLoggedInUsername(){
        String username="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {

            username = ((UserDetails)principal).getUsername();

        } else {

            username = principal.toString();

        }
        return username;
    }

    public List<User> getAllAdmins()
    {
        List<User> users = userRepository.findAll();

        for (User user: users)
        {
            if(user.getIs_admin()!=1)
            {
                users.remove(user);
            }
        }
        return users;
    }
    public long getLoggedInId(){
       User u= this.findByUsername(this.getLoggedInUsername());
       return u.getId();
    }
}
