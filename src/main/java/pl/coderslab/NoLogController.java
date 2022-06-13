package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoLogController {


    @GetMapping("/")
    public String hello(){
        return "homeNoLog";
    }
    @GetMapping("/contact")
    public String printContact(){
        return "contact";
    }
    @GetMapping("/about")
    public String printAbout(){
        return "about";
    }
}
