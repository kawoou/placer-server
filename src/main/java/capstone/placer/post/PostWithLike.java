package capstone.placer.post;

import lombok.Data;
import lombok.NonNull;

@Data
public class PostWithLike extends Post {
    public PostWithLike(Post post, boolean myLike) {
        super(post.getId(), post.getWriterNickName(), post.getAddress(), post.getLike(), post.getCreatedAt(), post.getComment(), post.getTimestamp());
        this.myLike = myLike;
    }

    @NonNull
    private boolean myLike;
}
