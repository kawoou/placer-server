package capstone.placer.post;

import capstone.placer.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostDetailMapper postDetailMapper;
    private final SpatialIndexMapper spatialIndexMapper;

    public List<PostWithLike> get(Paging paging, long userId) {
        return postMapper.get(paging.getPageNumber(), Paging.PAGE_SIZE).stream()
                .map(post -> new PostWithLike(post, postMapper.getCurrentLikeStatus(post.getId(), userId)))
                .collect(Collectors.toList());
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

    public PostDetail getDetail(int postId) {
        return postDetailMapper.getDetail(postId);
    }

    public Post insert(Post post) {
        return postMapper.insert(post);
    }

    public PostDetail insertDetail(PostDetail postDetail) {
        return postDetailMapper.insert(postDetail);
    }

    public SpatialIndex insertSpatialIndex(long latitude, long longitude) {
        SpatialIndex index = new SpatialIndex();
        return spatialIndexMapper.insert(index);
    }
}
