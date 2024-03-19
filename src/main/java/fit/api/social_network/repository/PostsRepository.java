package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long>, JpaSpecificationExecutor<Posts> {
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.post.id = :postId")
    int getLikeAmount(@Param("postId") Long postId);
    Posts findFirstByIdAndUserId(Long id, Long userId);
}
