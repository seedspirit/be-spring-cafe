package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.CommentDatabase;
import codesquad.springcafe.exceptions.ResourceNotFoundException;
import codesquad.springcafe.model.comment.CommentCreationDto;
import codesquad.springcafe.model.comment.CommentPreviewDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{sequence}")
    public ResponseEntity<?> deleteComment(@PathVariable long sequence, HttpSession session, HttpServletResponse response) {

        if(isUnauthUserForModification(sequence, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근할 수 없는 리소스입니다");
        }

        commentDatabase.delete(sequence);

        // 캐시된 리소스를 사용하지 못하도록 요청
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return ResponseEntity.ok().build();
    }

    private boolean isUnauthUserForModification(long sequence, HttpSession session) {
        CommentPreviewDto comment = commentDatabase.findCommentBySequence(sequence)
                .orElseThrow(ResourceNotFoundException::new);

        String userId = (String) session.getAttribute(LOGIN_SESSION_NAME);
        return !userId.equals(comment.getWriter());
    }
}
