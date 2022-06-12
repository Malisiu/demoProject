package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.category.CategoryRepository;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user_category.UserCategoryRepository;
import pl.coderslab.user_words.UserWordRepository;
import pl.coderslab.word.WordRepository;

@Controller
@RequestMapping("/app")
public class StartController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;

    public StartController(CategoryRepository categoryRepository, UserRepository userRepository, UserCategoryRepository userCategoryRepository, WordRepository wordRepository, UserWordRepository userWordRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.userCategoryRepository = userCategoryRepository;
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
    }

    @GetMapping("/start/selectOwnWords")
    public String selectOwnWords(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "selectOwnWordsForm";
    }

    @GetMapping("/start/selectOwnSentence")
    public String selectOwnSentence(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "selectOwnSentenceForm";
    }



}
