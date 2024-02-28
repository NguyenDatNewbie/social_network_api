package fit.api.social_network.model.mapper;

import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.response.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-28T02:19:37+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setName( user.getName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setBio( user.getBio() );
        userResponse.setAvatar( user.getAvatar() );

        return userResponse;
    }
}
