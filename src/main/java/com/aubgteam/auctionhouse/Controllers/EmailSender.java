package com.aubgteam.auctionhouse.Controllers;
import javax.mail.internet.MimeMessage;

import com.aubgteam.auctionhouse.Models.*;
import com.aubgteam.auctionhouse.Services.ApprovedItemService;
import com.aubgteam.auctionhouse.Services.FollowService;
import com.aubgteam.auctionhouse.Services.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
public class EmailSender {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    FollowService followService;

    @Autowired
    WinnerService winnerService;

    @RequestMapping("/emailFollowers")
    @ResponseBody
    public  String emailFollowers(){
        try{
            List<Tuple> list =followService.getFollowersEmails();
            for( Tuple t: list) {
                String title = "Bidding For " + t.getItemName() + " Open!";
                String message = "<html><body> <h2>Dear " + t.getUserName() + ",</h2>" +
                        "\n <p>The item you are following is now open for bidding. Hurry up and get " + t.getItemName() + " \n Click here to see the <a href='http://localhost:8080/item/" + t.getItemLink() + "'> item  </a></p></body></html>";
                sendEmail(t.getEmail(), title, message);
                ;
            }
            //getWinnerEmails
            List<WinnerInfo> winnerList = winnerService.getWinners();
            for(WinnerInfo w: winnerList){
               String title =" Congratulations "+w.getUsername()+ ", you won the bid for " + w.getItemName()+ "! " ;
               String message =" Dear "+w.getUsername()+ ", \n"+ "You have bought "+ w.getItemName() + " for $" +w.getPrice();

                sendEmail(w.getEmail(), title, message);
                ;
            }
            return "Success";
        }catch(Exception ex){
            ex.printStackTrace();
           return "Error";
        }
    }
    private void sendEmail(String email,String title,String text) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(email);
        helper.setText(text,true);
        helper.setSubject(title);

        sender.send(message);
    }
    public void newHighestBidderEmail(User u, Item it) throws Exception {
        String title="Item "+it.getName() + " has new highest bidder!";
        String text = "<html><body> <h2>Dear "+u.getUsername()+ ",</h2>"+
                "\n <p> Another person has offered more money for the following item : " + it.getName() + " </p>";
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(u.getEmail());
        helper.setText(text,true);
        helper.setSubject(title);

        sender.send(message);
    }
}
