package fit.api.social_network.model.mapper;

import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.request.user.RegisterRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-09T13:57:20+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)"
)
@Component
public class RegisterMapperImpl implements RegisterMapper {

    @Override
    public User toEntity(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setName( request.getName() );
        user.setEmail( request.getEmail() );
        user.setPassword( request.getPassword() );

        return user;
    }
}
