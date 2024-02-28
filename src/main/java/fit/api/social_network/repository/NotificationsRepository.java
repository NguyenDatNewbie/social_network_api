package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications,Long>, JpaSpecificationExecutor<Notifications> {
}
