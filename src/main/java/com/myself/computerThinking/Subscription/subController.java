package com.myself.computerThinking.Subscription;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class subController {

    @RequestMapping(value = "/stomp")
    public String getStopm(){
        return "stomp";
    }

}
