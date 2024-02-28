package fit.api.social_network.repository;

import fit.api.social_network.model.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember,Long>, JpaSpecificationExecutor<RoomMember> {
}
