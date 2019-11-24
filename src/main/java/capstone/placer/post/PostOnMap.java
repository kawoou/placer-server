package capstone.placer.post;

import lombok.NonNull;

public class PostOnMap extends Post {
    public PostOnMap(String address, double longitude, double latitude) {
        super("", address, "");
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public PostOnMap(Post post, double longitude, double latitude) {
        super("", post.getAddress(), "");
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public PostOnMap(Post post, PostDetail postDetail) {
        super("", post.getAddress(), "");
        this.longitude = postDetail.getLongitude();
        this.latitude = postDetail.getLatitude();
    }

    @NonNull
    private double longitude;

    @NonNull
    private double latitude;
}
