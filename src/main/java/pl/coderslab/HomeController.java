package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/app/start")
    public String hello(Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "home";
    }



    @GetMapping("/about")
    @ResponseBody
    public String about() { return "Here you can find some details for logged users"; }


    @GetMapping("/admin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal UserDetails customUser) {
        return "You are logged as " + customUser.getAuthorities();
    }


}
