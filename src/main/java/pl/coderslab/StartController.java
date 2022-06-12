package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.category.Category;
import pl.coderslab.category.CategoryRepository;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user_category.UserCategoryRepository;
import pl.coderslab.user_words.UserWordRepository;
import pl.coderslab.user_words.UserWords;
import pl.coderslab.word.Word;
import pl.coderslab.word.WordRepository;

import java.util.List;

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

    @GetMapping("/start/selectBasicWords")
    public String selectBasicWords(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "selectBasicWordsForm";
    }

    @GetMapping("/start/selectBasicSentence")
    public String selectBasicSentence(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "selectBasicSentenceForm";
    }

    @GetMapping("/start/test/basicWord")
    public String testBasicWord(Model model, @AuthenticationPrincipal UserDetails userDetails,@RequestParam Long categoryId,@RequestParam int num,@RequestParam int score,@RequestParam int isTrue){
        List<Word> allByCategory_id = wordRepository.findAllByCategory_Id(categoryId);

        if (num >= 1 && isTrue == 1){
            score++;
            UserWords byUserIdAndWordId = userWordRepository.findByUserIdAndWordId(userRepository.findByEmail(userDetails.getUsername()).getId(), allByCategory_id.get(num - 1).getId());
            byUserIdAndWordId.setCorrectWord(byUserIdAndWordId.getCorrectWord() + 1);
            userWordRepository.save(byUserIdAndWordId);
        }
        if (num >= 1 && isTrue == 0){
            UserWords byUserIdAndWordId = userWordRepository.findByUserIdAndWordId(userRepository.findByEmail(userDetails.getUsername()).getId(), allByCategory_id.get(num - 1).getId());
            byUserIdAndWordId.setWrongWord(byUserIdAndWordId.getWrongWord() + 1);
            userWordRepository.save(byUserIdAndWordId);
        }

        if (num == allByCategory_id.size()){
            return "redirect:/app/start";
        }

        Word word = allByCategory_id.get(num);
        System.out.println(word.getWordPl() + " " + word.getWordEn());
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        model.addAttribute("num",num);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("word",word);
        model.addAttribute("score",score);
        return "testBasicWords";
    }


    @GetMapping("/start/test/basicSentence")
    public String testBasicSentence(Model model, @AuthenticationPrincipal UserDetails userDetails,@RequestParam Long categoryId,@RequestParam int num,@RequestParam int score,@RequestParam int isTrue){
        List<Word> allByCategory_id = wordRepository.findAllByCategory_Id(categoryId);
        if (num >= 1 && isTrue == 1){
            score++;
            UserWords byUserIdAndWordId = userWordRepository.findByUserIdAndWordId(userRepository.findByEmail(userDetails.getUsername()).getId(), allByCategory_id.get(num - 1).getId());
            byUserIdAndWordId.setCorrectSentence(byUserIdAndWordId.getCorrectSentence() + 1);
            userWordRepository.save(byUserIdAndWordId);
        }
        if (num >= 1 && isTrue == 0){
            UserWords byUserIdAndWordId = userWordRepository.findByUserIdAndWordId(userRepository.findByEmail(userDetails.getUsername()).getId(), allByCategory_id.get(num - 1).getId());
            byUserIdAndWordId.setWrongSentence(byUserIdAndWordId.getWrongSentence() + 1);
            userWordRepository.save(byUserIdAndWordId);
        }

        if (num == allByCategory_id.size()){
            return "redirect:/app/start";
        }

        Word word = allByCategory_id.get(num);
        System.out.println(word.getWordPl() + " " + word.getWordEn());
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        model.addAttribute("num",num);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("word",word);
        model.addAttribute("score",score);
        return "testBasicSentence";
    }






}
