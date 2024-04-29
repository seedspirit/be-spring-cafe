package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.CommentDatabase;
import codesquad.springcafe.model.comment.CommentCreationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static codesquad.springcafe.controller.LoginController.LOGIN_SESSION_NAME;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentDatabase commentDatabase;

    @Autowired
    public CommentController(CommentDatabase commentDatabase) {
        this.commentDatabase = commentDatabase;
    }

    @PostMapping("/add")
    public String createComment(@ModelAttribute CommentCreationDto commentData, HttpServletRequest request, HttpSession session) {
        commentData.setWriter((String) session.getAttribute(LOGIN_SESSION_NAME));
        commentDatabase.addComment(commentData);
        return "redirect:/articles/detail/" + commentData.getArticleSequence();
    }
}
