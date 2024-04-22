package codesquad.springcafe.controller;

import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserLoginDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserDatabase userDatabase;

    @Autowired
    public LoginController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "users/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute UserLoginDto userLoginDto,
            @RequestParam(required = false) String redirectURL,
            HttpServletResponse response,
            HttpSession session,
            Model model
    ) throws IOException
    {
        User user = userLoginDto.toEntity();
        Optional<User> userCandidate = userDatabase.findUserByUserId(user.getUserId());
        if(userCandidate.isEmpty() || !userCandidate.get().isPasswordInputCorrect(user.getPassword())){
            model.addAttribute("validationError", true);
            return "users/login";
        }
        session.setAttribute("springCafeMember", userCandidate.get());
        logger.info("로그인 성공! id = {} , session = {}", user.getUserId(), session.getId());
        if(redirectURL != null && !redirectURL.isBlank()){
            response.sendRedirect(redirectURL);
            return null;
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
