package com.aubgteam.auctionhouse.Controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @RequestMapping("/Hello")
    public String index(){ return "Hello Beautiful";
    }
}
