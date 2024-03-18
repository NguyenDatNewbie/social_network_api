package fit.api.social_network.model.mapper;

import fit.api.social_network.model.entity.Posts;
import fit.api.social_network.model.response.post.PostResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class})
public interface PostMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user", target = "user", qualifiedByName = "toResponse")
    @Mapping(source = "image_url", target = "image_url")
    @Mapping(source = "caption", target = "caption")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "commentedAmount", target = "commentedAmount")
    @Mapping(source = "likedAmount", target = "likedAmount")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("toResponse")
    PostResponse toResponse(Posts entity);


    @IterableMapping(elementTargetType = PostResponse.class, qualifiedByName = "toResponse")
    @BeanMapping(ignoreByDefault = true)
    List<PostResponse> toResponseList(List<Posts> list);
}
