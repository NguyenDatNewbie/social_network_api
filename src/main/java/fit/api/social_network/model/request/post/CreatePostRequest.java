package fit.api.social_network.model.request.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreatePostRequest implements Serializable {
    @NotEmpty
    private String image_url;
    private String caption;
    @NonNull
    private Integer kind; // Posts & Stories 1 & 2
}
