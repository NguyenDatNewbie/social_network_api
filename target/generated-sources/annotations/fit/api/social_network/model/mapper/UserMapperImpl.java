package fit.api.social_network.model.mapper;

import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.response.user.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-29T08:14:22+0700",
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

        userResponse.setId( user.getId() );
        userResponse.setName( user.getName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setBio( user.getBio() );
        userResponse.setAvatar( user.getAvatar() );
        userResponse.setPassword( user.getPassword() );
        userResponse.setKind( user.getKind() );
        userResponse.setOtp( user.getOtp() );
        userResponse.setOtpExpiredDate( user.getOtpExpiredDate() );
        userResponse.setBanedExpiredDate( user.getBanedExpiredDate() );
        userResponse.setRole( user.getRole() );
        userResponse.setCreatedDate( user.getCreatedDate() );
        userResponse.setModifiedDate( user.getModifiedDate() );
        userResponse.setStatus( user.getStatus() );

        return userResponse;
    }

    @Override
    public List<UserResponse> toResponseList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserResponse> list1 = new ArrayList<UserResponse>( list.size() );
        for ( User user : list ) {
            list1.add( toResponse( user ) );
        }

        return list1;
    }
}
