package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Followers;
import fit.api.social_network.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersRepository extends JpaRepository<Followers,Long> {
}
