package pl.coderslab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
