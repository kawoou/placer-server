package capstone.placer.post;


import java.util.List;

public interface PostMapper {
    Post get(long postId);

    List<Post> getByTime(int pageNumber, int pageSize, int x, int y, int level);

    List<Post> getByPopularity(int pageNumber, int pageSize, int x, int y, int level);

    Boolean getCurrentLikeStatus(long postId, long userId);

    void like(long postId, long userId);

    void dislike(long postId, long userId);

    void increaseLike(long postId);

    void decreaseLike(long postId);

    void insert(Post post);
}
