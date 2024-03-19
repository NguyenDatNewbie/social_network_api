package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long>, JpaSpecificationExecutor<Likes> {
    Likes findFirstByPostIdAndUserId(Long postId, Long userId);
    @Modifying
    @Transactional
    void deleteAllByPostIdAndUserId(Long postId, Long userId);
}
