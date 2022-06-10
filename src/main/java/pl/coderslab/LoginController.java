package pl.coderslab;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;
import pl.coderslab.user.UserService;

@Controller
public class LoginController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public LoginController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }



    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register() {
        return "admin/register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String performRegister(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

}
