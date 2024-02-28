package fit.api.social_network.model.criteria;

import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.enums.ROLES;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCriteria {
    private Long id;
    private Integer status;
    private String name;
    private String email;
    private String bio;
    private Integer kind;
    private ROLES role;

    public Specification<User> getSpecification() {
        return new Specification<User>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if(!StringUtils.isBlank(getName())){
                    predicates.add(cb.equal(cb.lower(root.get("name")),"%"+ getName()+"%"));
                }
                if(!StringUtils.isBlank(getEmail())){
                    predicates.add(cb.equal(cb.lower(root.get("email")),"%"+ getEmail()+"%"));
                }
                if(!StringUtils.isBlank(getBio())){
                    predicates.add(cb.equal(cb.lower(root.get("bio")),"%"+ getBio()+"%"));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getRole() != null) {
                    predicates.add(cb.equal(root.get("role"), getRole()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
