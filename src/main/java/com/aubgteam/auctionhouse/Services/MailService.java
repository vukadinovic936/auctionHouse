

package com.aubgteam.auctionhouse.Services;
import com.aubgteam.auctionhouse.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailService {
//    private static Properties props = loadProps();


//    private static Session mailSession = Session.getInstance(props);


//    public static void main(String[]arg) throws MessagingException
//    {
//        sendEmailToAdmin(user);
//    }

//    Properties prop = new Properties();
//    @Autowired
//    static UserService userService;

private static Properties getProps()
{
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    return props;
}


    public static void sendEmailToAdmin(List<User> admins, long id)
    {
        final String username = "softwareaubg@gmail.com";
        final String password = "op3n737am3!";

        Properties prop = getProps(); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
//        String from = "bilo9797@outlook.com";
//        String password = "op3n737am3!";
//        MimeMessage message = constructMessage(from);


//        mailSession.setProvider(new Provider());
//        Transport tr = mailSession.getTransport();

        try
        {
            MimeMessage message = composeMessage(session, id);
            setAdminsAsRecipients(message, admins);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private static void setAdminsAsRecipients(MimeMessage message, List<User> admins)  throws MessagingException
    {



        for (User admin: admins) {
            message.addRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(admin.getEmail())

            );
//
        }


    }



    private static MimeMessage composeMessage(Session session, long id)  throws MessagingException
    {
        MimeMessage message = new MimeMessage(session);
//        InternetAddress[] addresses = getAdminEmailAddresses();
        message.setFrom(new InternetAddress("softwareaubg@gmail.com"));

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        message.setSubject("New Item Added");
        helper.setText("A new item was added. \n" +
                "<html><body><a href='http://localhost:8080/admin/details/" + id +"'>Click to view it </a></body></html>", true);
        return message;
    }


//        private static List<String> getAdminEmails()
//        {
//            return "test";
//        }




//    private static void addRecipients(MimeMessage message) throws MessagingException
//    {
//        String to = "jasminkast@hotmail.com";
//        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
//    }
//
//
//
//    private static MimeMessage constructMessage(String from) throws MessagingException {
//
//        String subject = "New item added";
//        MimeMessage message = new MimeMessage(mailSession);
//        message.setFrom(new InternetAddress(from));
//        addRecipients(message);
//
//        message.setSubject(subject);
//        message.setText(getMessage());
//        return message;

    }

//    private static String getMessage()
//    {
//        return "this is just a test\ntrust me";


        // mailSession.setDebug(true);


//    }}
