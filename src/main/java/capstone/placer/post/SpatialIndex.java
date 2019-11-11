package capstone.placer.post;

import lombok.Data;
import lombok.NonNull;

@Data
public class SpatialIndex {

    @NonNull
    private int x;

    @NonNull
    private int y;

    @NonNull
    private int level;

    @NonNull
    private long postId;
}
