package capstone.placer.post;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
public class Post {

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
