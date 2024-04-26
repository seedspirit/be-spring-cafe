package codesquad.springcafe.controller;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.exceptions.AccessNotAllowedException;
import codesquad.springcafe.exceptions.ResourceNotFoundException;
import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleCreationDto;
import codesquad.springcafe.model.article.dto.ArticleModificationDto;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static codesquad.springcafe.controller.LoginController.LOGIN_SESSION_NAME;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleDatabase articleDatabase;

    @Autowired
    public ArticleController(ArticleDatabase articleDatabase){
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/add")
    public String getArticleCreationForm(){
        return "article/form";
    }

    @PostMapping("/add")
    public String createArticle(@ModelAttribute ArticleCreationDto articleCreationDto, HttpSession session) {
        Article article = articleCreationDto.toEntity();
        article.setSequence();
        article.setWriter((String) session.getAttribute(LOGIN_SESSION_NAME));
        articleDatabase.addArticle(article);
        return "redirect:/";
    }


    @GetMapping("/detail/{sequence}")
    public String loadArticleContent(@PathVariable long sequence, Model model) {
        ArticleProfileDto articleProfile = articleDatabase.findArticleBySequence(sequence)
                .orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("article", articleProfile);
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

    private boolean isUnauthUserForModification(long articleSequence, HttpSession session) {
        ArticleProfileDto articleProfile = articleDatabase.findArticleBySequence(articleSequence)
                .orElseThrow(ResourceNotFoundException::new);

        String userId = (String) session.getAttribute(LOGIN_SESSION_NAME);
        return !userId.equals(articleProfile.getUserId());
    }
}