package pl.coderslab.user;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.user_words.UserWords;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);


}