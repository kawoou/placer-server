package capstone.placer.post;

import java.util.List;

public interface SpatialIndexMapper {
    List<Long> getPostIds(long x, long y);

    void insert(SpatialIndex spatialIndex);

    void delete(long postId);
}
