package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Likes;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long>, JpaSpecificationExecutor<Likes> {
    Likes findFirstByPostIdAndUserId(Long postId, Long userId);
    Likes findFirstByPostIdAndUserIdAndKind(Long postId, Long userId, Integer kind);
    Likes findFirstByCommentIdAndUserIdAndKind(Long commentId, Long userId, Integer kind);
    @Modifying
    @Transactional
    void deleteAllByPostIdAndUserId(Long postId, Long userId);
}
