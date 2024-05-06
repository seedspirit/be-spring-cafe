package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.db.article.CommentDatabase;
import codesquad.springcafe.exceptions.AccessNotAllowedException;
import codesquad.springcafe.exceptions.ResourceNotFoundException;
import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleCreationDto;
import codesquad.springcafe.model.article.dto.ArticleModificationDto;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
import codesquad.springcafe.model.comment.CommentPreviewDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static codesquad.springcafe.controller.LoginController.LOGIN_SESSION_NAME;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final AtomicLong sequenceGenerator;
    private final ArticleDatabase articleDatabase;
    private final CommentDatabase commentDatabase;

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase, CommentDatabase commentDatabase){
        this.articleDatabase = articleDatabase;
        this.commentDatabase = commentDatabase;
        this.sequenceGenerator = new AtomicLong();
    }

    @GetMapping("/add")
    public String getArticleCreationForm(){
        return "article/form";
    }

    @PostMapping("/add")
    public String createArticle(@ModelAttribute ArticleCreationDto articleCreationDto, HttpSession session) {
        Article article = articleCreationDto.toEntity();
        article.setSequence(sequenceGenerator.incrementAndGet());
        article.setWriter((String) session.getAttribute(LOGIN_SESSION_NAME));
        articleDatabase.addArticle(article);
        return "redirect:/";
    }


    @GetMapping("/detail/{sequence}")
    public String loadArticleContent(@PathVariable long sequence, Model model, HttpSession session) {
        ArticleProfileDto articleProfile = articleDatabase.findArticleBySequence(sequence)
                .orElseThrow(ResourceNotFoundException::new);

        if(articleProfile.isDeleted()){
            throw new ResourceNotFoundException();
        }

        List<CommentPreviewDto> comments = commentDatabase.getCommentsOfArticle(sequence);

        String userId = (String) session.getAttribute(LOGIN_SESSION_NAME);
        boolean isArticleAuthor = userId.equals(articleProfile.getUserId());
        comments.forEach(comment -> comment.setCommentOfCurrentSession(
                userId.equals(comment.getWriter())
            )
        );

        model.addAttribute("article", articleProfile);
        model.addAttribute("comments", comments);
        model.addAttribute("commentsAmount", comments.size());
        model.addAttribute("isArticleAuthor", isArticleAuthor);
        return "article/show";
    }

    @GetMapping("/update/{sequence}")
    public String getArticleUpdatePage(@PathVariable long sequence, Model model, HttpSession session) {
        if(isUnauthUserForModification(sequence, session)){
            throw new AccessNotAllowedException();
        }
        model.addAttribute("sequence", sequence);
        return "article/updateForm";
    }

    @PutMapping("/update/{sequence}")
    public String updateArticle(
            @PathVariable long sequence,
            HttpSession session,
            @ModelAttribute ArticleModificationDto modificationData)
    {
        if(isUnauthUserForModification(sequence, session)){
            throw new AccessNotAllowedException();
        }
        articleDatabase.update(sequence, modificationData);
        return "redirect:/articles/detail/" + sequence;
    }

    @DeleteMapping("/update/{sequence}")
    public ResponseEntity<?> deleteArticle(@PathVariable long sequence, HttpSession session, HttpServletResponse response) {
        if(isUnauthUserForModification(sequence, session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근할 수 없는 리소스입니다");
        }

        articleDatabase.delete(sequence);

        // 캐시된 리소스를 사용하지 못하도록 요청
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return ResponseEntity.ok("/");
    }

    private boolean isUnauthUserForModification(long articleSequence, HttpSession session) {
        ArticleProfileDto articleProfile = articleDatabase.findArticleBySequence(articleSequence)
                .orElseThrow(ResourceNotFoundException::new);

        String userId = (String) session.getAttribute(LOGIN_SESSION_NAME);
        return !userId.equals(articleProfile.getUserId());
    }
}