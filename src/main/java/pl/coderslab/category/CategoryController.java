package pl.coderslab.category;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.RankingRepository;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user_category.UserCategory;
import pl.coderslab.user_category.UserCategoryRepository;
import pl.coderslab.user_words.UserWordRepository;
import pl.coderslab.user_words.UserWords;
import pl.coderslab.word.Word;
import pl.coderslab.word.WordRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final RankingRepository rankingRepository;

    public CategoryController(CategoryRepository categoryRepository, UserRepository userRepository, UserCategoryRepository userCategoryRepository, WordRepository wordRepository, UserWordRepository userWordRepository, RankingRepository rankingRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.userCategoryRepository = userCategoryRepository;
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
        this.rankingRepository = rankingRepository;
    }

    @GetMapping("/category")
    public String printCategory(Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("basic",categoryRepository.basicCategory());
        model.addAttribute("noBasic",categoryRepository.userCategory(userRepository.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "category";

    }

    @GetMapping("/achivments")
    public String printAchivment(@AuthenticationPrincipal UserDetails userDetails,Model model){
        User byEmail = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user",byEmail);
        model.addAttribute("words",userCategoryRepository.findByWordAchived(byEmail.getId()));
        model.addAttribute("sentence",userCategoryRepository.findBySentenceAchived(byEmail.getId()));
        return "achivments";
    }

    @GetMapping("/ranking")
    public String printRanking(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("ranking",userRepository.ranking());
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        model.addAttribute("achiv",userCategoryRepository.countSentenceAchivments(userRepository.findByEmail(userDetails.getUsername()).getId()) + userCategoryRepository.countWordAchivments(userRepository.findByEmail(userDetails.getUsername()).getId()));
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
    @GetMapping(value = "/category/{id}")
    public String deleteCategory(@PathVariable long id,@AuthenticationPrincipal UserDetails userDetails){
        userCategoryRepository.deleteByCategoryId(id);
        //categoryRepository.deleteById(id);
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
        model.addAttribute("words",categoryRepository.findAllOwnWords(id));
        categoryRepository.findAllOwnWords(id).forEach(el -> System.out.println(el.getWordPl()));
        model.addAttribute("categoryId",id);
        return "ownWords";
    }

    @GetMapping("/category/sentence/own/{id}")
    public String printOwnSentence(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails,Model model){
        model.addAttribute("sentence",categoryRepository.findAllOwnSentence(id));
        model.addAttribute("categoryId",id);
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
        List<Word> allByCategory_id = wordRepository.findAllByCategory_Id(category.getId());
        User byEmail = userRepository.findByEmail(userDetails.getUsername());
        userWordRepository.createUserWord(byEmail.getId(),allByCategory_id.get(allByCategory_id.size() - 1).getId());
        return "redirect:/app/category/words/own/" + id;
    }

    @GetMapping("/sentenceAdd/{id}")
    public String addSentence(@PathVariable long id,Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        model.addAttribute("categoryId",id);
        return "sentenceAdd";
    }

    @PostMapping("/sentenceAdd/{id}")
    public String addSentenceAction(@PathVariable long id,Word word,@AuthenticationPrincipal UserDetails userDetails){
        Category category = categoryRepository.selectCat(id);
        word.setWordEn(null);
        word.setWordPl(null);
        word.setCategory(category);
        wordRepository.save(word);
        List<Word> allByCategory_id = wordRepository.findAllByCategory_Id(category.getId());
        User byEmail = userRepository.findByEmail(userDetails.getUsername());
        userWordRepository.createUserWord(byEmail.getId(),allByCategory_id.get(allByCategory_id.size() - 1).getId());
        return "redirect:/app/category/sentence/own/" + id;
    }

    @GetMapping("/category/wordRemove/{id}")
    public String deleteWord(@PathVariable long id){
        Optional<Word> byId = wordRepository.findById(id);
        userWordRepository.deleteUserWord(id);
        wordRepository.deleteById(id);
        return "redirect:/app/category/words/own/" + byId.get().getCategory().getId();
    }
    @GetMapping("/category/sentenceRemove/{id}")
    public String deleteSentence(@PathVariable long id){
        Optional<Word> byId = wordRepository.findById(id);
        userWordRepository.deleteUserWord(id);
        wordRepository.deleteById(id);
        return "redirect:/app/category/sentence/own/" + byId.get().getCategory().getId();
    }

    @GetMapping("/category/wordEdit/{id}")
    public String editWord(@PathVariable long id,Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        Optional<Word> byId = wordRepository.findById(id);
        model.addAttribute("word",byId.get());
        model.addAttribute("categoryId",wordRepository.findById(id).get().getCategory().getId());
        return "editWord";
    }

    @PostMapping("/category/wordEdit/{id}")
    public String editWordAction(@PathVariable long id,Word word){
        Long categoryId = wordRepository.findCategoryId(id);
        word.setCategory(categoryRepository.selectCat(categoryId));
        wordRepository.save(word);
        return "redirect:/app/category/words/own/" + categoryId;
    }

    @GetMapping("/category/sentenceEdit/{id}")
    public String editSentence(@PathVariable long id,Model model,@AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        Optional<Word> byId = wordRepository.findById(id);
        model.addAttribute("word",byId.get());
        model.addAttribute("categoryId",wordRepository.findById(id).get().getCategory().getId());
        return "editSentence";
    }

    @PostMapping("/category/sentenceEdit/{id}")
    public String editSentenceAction(@PathVariable long id,Word word){
        Long categoryId = wordRepository.findCategoryId(id);
        word.setCategory(categoryRepository.selectCat(categoryId));
        wordRepository.save(word);
        return "redirect:/app/category/sentence/own/" + categoryId;
    }

    @GetMapping("/worstWords")
    public String printWorstWords(Model model,@AuthenticationPrincipal UserDetails userDetails){
        List<UserWords> allUserWords = rankingRepository.findAllUserWords(userRepository.findByEmail(userDetails.getUsername()).getId());
        model.addAttribute("words",allUserWords);
        return "worstWords";
    }

    @GetMapping("/worstSentence")
    public String printWorstSentence(Model model,@AuthenticationPrincipal UserDetails userDetails){
        List<UserWords> allUserWords = rankingRepository.findAllUserSentence(userRepository.findByEmail(userDetails.getUsername()).getId());
        model.addAttribute("words",allUserWords);
        return "worstSentence";
    }



}
