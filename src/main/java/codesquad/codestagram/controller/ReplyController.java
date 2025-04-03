package codesquad.codestagram.controller;

import codesquad.codestagram.domain.Reply;
import codesquad.codestagram.domain.User;
import codesquad.codestagram.service.ArticleService;
import codesquad.codestagram.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static codesquad.codestagram.config.AppConstants.ALERT_MESSAGE;
import static codesquad.codestagram.config.AppConstants.LOGIN_USER;

@Controller
public class ReplyController {
    private final ArticleService articleService;
    private final ReplyService replyService;

    public ReplyController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @PostMapping("/article/{articleId}/reply")
    public String createReply(@PathVariable("articleId") Long articleId, @ModelAttribute Reply reply, HttpSession session) {
        User loginUser = (User) session.getAttribute(LOGIN_USER);

        reply.setUser(loginUser);
        reply.setArticle(articleService.findArticle(articleId));
        replyService.saveReply(reply);

        return "redirect:/article/" + articleId;
    }

    @DeleteMapping("/article/{articleId}/reply/{replyId}/delete")
    public String deleteReply(@PathVariable("articleId") Long articleId, @PathVariable("replyId") Long replyId
            , HttpSession session, RedirectAttributes redirectAttributes) {

        User loginUser = (User) session.getAttribute(LOGIN_USER);

        try {
            boolean result = replyService.removeReply(replyId, loginUser);

            if (!result) {
                redirectAttributes.addFlashAttribute(ALERT_MESSAGE, "작성자 ID와 사용자 ID가 일치하지 않습니다.");
                return "redirect:/article/" + articleId;
            }

            redirectAttributes.addFlashAttribute(ALERT_MESSAGE, "댓글이 삭제되었습니다.");
            return "redirect:/article/" + articleId;

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE, e.getMessage());
            return "redirect:/";
        }
    }
}
