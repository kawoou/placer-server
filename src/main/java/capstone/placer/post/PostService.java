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
        Converter c = new GPSConverter();
        Point center = new Point(longitude, latitude);

        int hex_level = c.zoomToLevel(zoom);
        Hex index = c.pointToHex(center, hex_level);

        return postMapper.getByTime(paging.getNextPageStartOffset(), Paging.PAGE_SIZE, index.q(), index.r(), hex_level).stream()
                .map(post -> new PostWithLike(post, postMapper.getCurrentLikeStatus(post.getId(), userId)))
                .collect(Collectors.toList());
    }

    public List<PostWithLike> getByPopularity(Paging paging, long userId, double latitude, double longitude, double zoom) {
        Converter c = new GPSConverter();
        Point center = new Point(longitude, latitude);

        int hex_level = c.zoomToLevel(zoom);
        Hex index = c.pointToHex(center, hex_level);

        return postMapper.getByPopularity(paging.getNextPageStartOffset(), Paging.PAGE_SIZE, index.q(), index.r(), hex_level).stream()
                .map(post -> new PostWithLike(post, postMapper.getCurrentLikeStatus(post.getId(), userId)))
                .collect(Collectors.toList());
    }

    public List<PostOnMap> getForMap(double latitude, double longitude, double zoom) {
        Converter c = new GPSConverter();
        Point center = new Point(longitude, latitude);

        int hex_level = c.zoomToLevel(zoom);
        int required_level;
        if (hex_level == 0) {
            required_level = hex_level;
        } else {
            required_level = c.zoomToLevel(zoom) - 1;
        }

        Hex index = c.pointToHex(center, required_level);
        List<Hex> target_grids = new ArrayList<Hex>();
        target_grids.add(index);
        for (int i = 0; i < 6; i++) {
            target_grids.add(index.neighbor(i));
        }

        List<PostOnMap> result = new ArrayList<PostOnMap>();

        for (Hex h : target_grids) {
            result.addAll(postMapper.getByPopularity(1, 3, h.q(), h.r(), required_level).stream()
                    .map(post -> new PostOnMap(post, postDetailMapper.getDetail(post.getId())))
                    .collect(Collectors.toList()));
        }

        return result;
    }

    public boolean toggleLike(long postId, long userId) {
        Boolean currentLikeStatus = postMapper.getCurrentLikeStatus(postId, userId);

        // the user already likes the post
        if (currentLikeStatus) {
            postMapper.dislike(postId, userId);
            postMapper.decreaseLike(postId);
            return false;
        }
        // the user doesn't like the post yet
        else {
            postMapper.like(postId, userId);
            postMapper.increaseLike(postId);
            return true;
        }
    }

    public PostDetail getDetail(long postId) {
        return postDetailMapper.getDetail(postId);
    }

    public void insert(Post post) {
        postMapper.insert(post);
    }

    public void insertDetail(PostDetail postDetail) {
        postDetailMapper.insert(postDetail);
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
