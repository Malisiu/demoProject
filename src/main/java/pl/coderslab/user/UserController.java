package pl.coderslab.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.Role;
import pl.coderslab.RoleRepository;

import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/app")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, UserService userService, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String printProfile(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(User user){
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userService.saveUser(user);
        return "profile";
    }

    @GetMapping("/settings")
    public String printSettings(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user",userRepository.findByEmail(userDetails.getUsername()));
        return "settings";
    }

    @PostMapping("/settings")
    public String updatePassword(User user ,@RequestParam String oldPassword,@RequestParam String newPassword,@AuthenticationPrincipal UserDetails userDetails){
        User byEmail = userRepository.findByEmail(userDetails.getUsername());
        boolean matches = passwordEncoder.matches(oldPassword,byEmail.getPassword());
        if(matches){
            user.setPassword(newPassword);
            userService.saveUser(user);
        }else {
            System.out.println("nie");
        }
        return "settings";
    }


}
