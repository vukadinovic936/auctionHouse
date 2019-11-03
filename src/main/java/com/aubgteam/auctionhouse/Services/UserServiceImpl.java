package com.aubgteam.auctionhouse.Services;
import com.aubgteam.auctionhouse.Models.CreditCard;
import com.aubgteam.auctionhouse.Models.User;
import com.aubgteam.auctionhouse.Repositories.CreditCardRepository;
import com.aubgteam.auctionhouse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void deleteUser(Long id){

        userRepository.deleteById(id);
    }

    public void saveCreditCard(CreditCard card){
        cardRepository.save(card);
    }

}
