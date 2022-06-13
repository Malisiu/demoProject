package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class ResultController {


    @GetMapping("/ownWordsResult/{points}/{score}")
    public String selectOwnWords(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable int points,@PathVariable int score){
        model.addAttribute("score",score);
        model.addAttribute("points",points);
        return "printResultOwnWords";
    }

}
