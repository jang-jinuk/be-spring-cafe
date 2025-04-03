package codesquad.codestagram.service;

import codesquad.codestagram.domain.Reply;
import codesquad.codestagram.domain.User;
import codesquad.codestagram.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void saveReply(Reply reply) {
        replyRepository.save(reply);
    }

    public List<Reply> findReplyList(Long articleId) {
        return replyRepository.findAllByArticle_Id(articleId);
    }

    public boolean removeReply(Long replyId, User loginUser) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (reply.getUser().getId() != loginUser.getId()) {
            return false;
        }

        replyRepository.deleteById(replyId);
        return true;
    }
}
