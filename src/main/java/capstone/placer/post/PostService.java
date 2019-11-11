package capstone.placer.post;

import capstone.hexgrid.Hex;
import capstone.placer.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import capstone.hexgrid.Point;
import capstone.hexgrid.Converter;
import capstone.hexgrid.GPSConverter;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostDetailMapper postDetailMapper;
    private final SpatialIndexMapper spatialIndexMapper;

    public Post get(long postId) {
        return postMapper.get(postId);
    }

    public List<PostWithLike> getByTime(Paging paging, long userId, double latitude, double longitude, double zoom) {
        return postMapper.getByTime(paging.getPageNumber(), Paging.PAGE_SIZE).stream()
                .map(post -> new PostWithLike(post, postMapper.getCurrentLikeStatus(post.getId(), userId)))
                .collect(Collectors.toList());
    }

    public List<PostWithLike> getByPopularity(Paging paging, long userId, double latitude, double longitude, double zoom) {
        return postMapper.getByPopularity(paging.getPageNumber(), Paging.PAGE_SIZE).stream()
                .map(post -> new PostWithLike(post, postMapper.getCurrentLikeStatus(post.getId(), userId)))
                .collect(Collectors.toList());
    }

    public boolean toggleLike(long postId, long userId) {
        boolean currentLikeStatus = postMapper.getCurrentLikeStatus(postId, userId);

        // the user already likes the post
        if (currentLikeStatus)
            postMapper.dislike(postId, userId);

            // the user doesn't like the post yet
        else
            postMapper.like(postId, userId);
        return !currentLikeStatus;
    }

    public PostDetail getDetail(long postId) {
        return postDetailMapper.getDetail(postId);
    }

    public Post insert(Post post) {
        return postMapper.insert(post);
    }

    public PostDetail insertDetail(PostDetail postDetail) {
        return postDetailMapper.insert(postDetail);
    }

    public List<SpatialIndex> insertSpatialIndex(long postId, double latitude, double longitude) {
        List<SpatialIndex> result = new ArrayList<>();

        Converter c = new GPSConverter();
        List<Hex> hexs = c.pointToHex(new Point(longitude, latitude));

        for (int i = 0; i < GPSConverter.levels; i++) {
            Hex h = hexs.get(i);
            SpatialIndex index = new SpatialIndex(h.q(), h.r(), i, postId);
            result.add(index);
            spatialIndexMapper.insert(index);
        }
        return result;
    }

    public boolean isExistPost(long postId) {
        return Objects.nonNull(this.get(postId));
    }
}
