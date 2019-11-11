package capstone.placer.post;

import java.util.List;

public interface SpatialIndexMapper {
    List<Long> getPostIds(long x, long y);

    SpatialIndex insert(SpatialIndex spatialIndex);

    void delete(long postId);
}
