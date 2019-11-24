package capstone.placer.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class Post {

    public Post(String writerNickName, String address, String comment) {
        this.writerNickName = writerNickName;
        this.address = address;
        this.like = 0;
        this.createdAt = System.currentTimeMillis();
        this.comment = comment;
    }

    @NonNull
    private long id;

    @NonNull
    private String writerNickName;

    // S3 주소
    @NonNull
    private String address;

    @NonNull
    private int like;

    @NonNull
    private long createdAt;

    @Nullable
    private String comment;
}
