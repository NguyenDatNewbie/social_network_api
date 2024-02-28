package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long>, JpaSpecificationExecutor<Comments> {
}
