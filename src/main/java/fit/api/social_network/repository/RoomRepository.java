package fit.api.social_network.repository;

import fit.api.social_network.model.entity.Room;
import fit.api.social_network.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
