package pl.coderslab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.category.Category;
import pl.coderslab.category.CategoryRepository;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user.UserService;
import pl.coderslab.user_category.UserCategory;
import pl.coderslab.user_category.UserCategoryRepository;
import pl.coderslab.user_words.UserWordRepository;
import pl.coderslab.word.Word;
import pl.coderslab.word.WordRepository;

import java.util.List;

@Controller
public class LoginController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserWordRepository userWordRepository;
    private final WordRepository wordRepository;

    public LoginController(UserService userService, RoleRepository roleRepository, UserRepository userRepository, UserCategoryRepository userCategoryRepository, CategoryRepository categoryRepository, UserWordRepository userWordRepository, WordRepository wordRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userCategoryRepository = userCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userWordRepository = userWordRepository;
        this.wordRepository = wordRepository;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }



    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register() {
        return "regiestr";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String performRegister(User user) {
        userService.saveUser(user);
        System.out.println(user.getPoints());
        List<Category> categories = categoryRepository.basicCategory();
        categories.forEach(el -> {
            UserCategory userCategory = new UserCategory();
            userCategory.setCategory(el);
            userCategory.setUser(user);
            userCategory.setAchivedSentence(false);
            userCategory.setAchivedWord(false);
            userCategoryRepository.save(userCategory);
        });
        //userWordRepository.createUserWord();
        wordRepository.findAllBasicWords().forEach(el ->{
            User byEmail = userRepository.findByEmail(user.getEmail());
            userWordRepository.createUserWord(byEmail.getId(),el.getId());
        });

        return "redirect:/login";
    }

}
