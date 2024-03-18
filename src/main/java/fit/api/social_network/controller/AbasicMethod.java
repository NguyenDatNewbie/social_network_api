package fit.api.social_network.controller;

import fit.api.social_network.model.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Log4j2
public class AbasicMethod {
    public Long getCurrentUserId(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            if(user == null){
                log.error("user null");
            }
            return user.getId();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
