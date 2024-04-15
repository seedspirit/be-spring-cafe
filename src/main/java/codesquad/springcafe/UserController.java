package codesquad.springcafe;

import codesquad.springcafe.db.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserDatabase userDatabase;

    @Autowired
    public UserController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
        userDatabase.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model){
        List<User> users = userDatabase.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("totalUserNumber", userDatabase.getTotalUserNumber());
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable String userId, Model model){
        Optional<User> user = userDatabase.findUserByUserId(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException();
        }
        model.addAttribute("user", user.get());
        return "users/profile";
    }
}
