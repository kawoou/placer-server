package capstone.placer.post;

import lombok.NonNull;

public class PostWithLike extends Post {
    public PostWithLike(String writerNickName, String address, String comment, boolean myLike) {
        super(writerNickName, address, comment);
        this.myLike = myLike;
    }

    public PostWithLike(Post post, boolean myLike) {
        super(post.getId(), post.getWriterNickName(), post.getAddress(), post.getLike(), post.getCreatedAt(), post.getComment());
        this.myLike = myLike;
    }

    @NonNull
    private boolean myLike;
}
