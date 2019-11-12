package com.aubgteam.auctionhouse.Controllers;
import javax.mail.internet.MimeMessage;

import com.aubgteam.auctionhouse.Models.Follow;
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
            List<String> list =followService.getFollowersEmails();
            for( String s: list){
               sendEmail(s,"hi","hey dude");
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
        helper.setText(text);
        helper.setSubject(title);

        sender.send(message);
    }
}
