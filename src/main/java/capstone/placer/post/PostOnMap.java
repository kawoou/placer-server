package capstone.placer.post;

import lombok.NonNull;

public class PostOnMap extends Post {
    public PostOnMap(String address, double longitude, double latitude) {
        super("", address, "");
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public PostOnMap(Post post, double longitude, double latitude) {
        super(post.getId(), post.getWriterNickName(), post.getAddress(), post.getLike(), post.getCreatedAt(), post.getComment());
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public PostOnMap(Post post, PostDetail postDetail) {
        super(post.getId(), post.getWriterNickName(), post.getAddress(), post.getLike(), post.getCreatedAt(), post.getComment());
        this.longitude = postDetail.getLongitude();
        this.latitude = postDetail.getLatitude();
    }

    @NonNull
    private double longitude;

    @NonNull
    private double latitude;
}
