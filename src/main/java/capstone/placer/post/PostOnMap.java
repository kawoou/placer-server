package capstone.placer.post;

import lombok.Data;
import lombok.NonNull;

@Data
public class PostOnMap extends Post {
    public PostOnMap(Post post, PostDetail postDetail) {
        super(post.getId(), post.getWriterNickName(), post.getAddress(), post.getLike(), post.getCreatedAt(), post.getComment(), post.getTimestamp());
        this.longitude = postDetail.getLongitude();
        this.latitude = postDetail.getLatitude();
    }

    @NonNull
    private double longitude;

    @NonNull
    private double latitude;
}
