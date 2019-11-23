package com.aubgteam.auctionhouse.Controllers;
import javax.mail.internet.MimeMessage;

import com.aubgteam.auctionhouse.Models.Follow;
import com.aubgteam.auctionhouse.Models.Tuple;
import com.aubgteam.auctionhouse.Services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class EmailSender {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    FollowService followService;

    @RequestMapping("/emailFollowers")
    @ResponseBody
    public  String emailFollowers(){
        try{
            List<Tuple> list =followService.getFollowersEmails();
            for( Tuple t: list){
                String title="Bidding For "+t.getItemName() + " Open!";
                String message = "<html><body> <h2>Dear "+t.getUserName()+ ",</h2>"+
                               "\n <p>The item you are following is now open for bidding. Hurry up and get "+t.getItemName()+ " \n Click here to see the <a href='http://localhost:8080/item/"+ t.getItemLink()+"'> item  </a></p></body></html>";
               sendEmail(t.getEmail(),title,message);
                       ;
            }
            return "Success";
        }catch(Exception ex){
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
}
