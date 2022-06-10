package pl.coderslab.category;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user_category.UserCategory;
import pl.coderslab.user_category.UserCategoryRepository;
import pl.coderslab.user_words.UserWordRepository;
import pl.coderslab.user_words.UserWords;
import pl.coderslab.word.Word;
import pl.coderslab.word.WordRepository;

@Controller
@RequestMapping("/app")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;

    public CategoryController(CategoryRepository categoryRepository, UserRepository userRepository, UserCategoryRepository userCategoryRepository, WordRepository wordRepository, UserWordRepository userWordRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.userCategoryRepository = userCategoryRepository;
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
    }

    @GetMapping("/category")
    public String printCategory(Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "category";
    }

    @GetMapping("/achivments")
    public String printAchivment(){
        return "achivments";
    }

    @GetMapping("/ranking")
    public String printRanking(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("ranking",userRepository.ranking());
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "ranking";
    }

    @GetMapping("/categoryAdd")
    public String addCategory(Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "addCategory";
    }

    @PostMapping("/categoryAdd")
    public String addCategoryAction(Category category,@AuthenticationPrincipal UserDetails userDetails){
        category.setBasic(false);
        category.setAchivedWord(false);
        category.setAchivedSentence(false);
        categoryRepository.save(category);
        UserCategory userCategory = new UserCategory();
        userCategory.setCategory(category);
        userCategory.setUser(userRepository.findByEmail(userDetails.getUsername()));
        userCategoryRepository.save(userCategory);
        return "redirect:/app/category";
    }
    @GetMapping("/category/{id}")
    public String deleteCategory(@PathVariable long id,@AuthenticationPrincipal UserDetails userDetails){
        userCategoryRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
        return "redirect:/app/category";
    }

    @GetMapping("/category/words/{id}")
    public String printWords(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model){
        model.addAttribute("words",wordRepository.findAllByCategory_Id(id));
        return "categoryWords";
    }

    @GetMapping("/category/sentence/{id}")
    public String printSentence(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model){
        model.addAttribute("sentence",wordRepository.findAllByCategory_Id(id));
        return "categorySentence";
    }

    @GetMapping("/category/words/own/{id}")
    public String printOwnWords(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model){
        model.addAttribute("words",wordRepository.findAllByCategory_Id(id));
        model.addAttribute("categoryId",id);
        return "ownWords";
    }

    @GetMapping("/category/sentence/own/{id}")
    public String printOwnSentence(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model){
        model.addAttribute("sentence",wordRepository.findAllByCategory_Id(id));
        return "ownSentence";
    }

    @GetMapping("/wordAdd/{id}")
    public String addWord(@PathVariable long id,Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        model.addAttribute("categoryId",id);
        return "wordAdd";
    }

    @PostMapping("/wordAdd/{id}")
    public String addWordAction(@PathVariable long id,Word word,@AuthenticationPrincipal UserDetails userDetails){
        Category category = categoryRepository.selectCat(id);
        word.setSentenceEn(null);
        word.setSentencePl(null);
        word.setCategory(category);
        wordRepository.save(word);
        User byEmail = userRepository.findByEmail(userDetails.getUsername());
        userWordRepository.createUserWord(byEmail.getId(),word.getId());
        return "redirect:/app/category/words/own/" + id;
    }

}
