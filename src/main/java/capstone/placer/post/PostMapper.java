package capstone.placer.post;


import java.util.List;

public interface PostMapper {
    List<Post> get(int pageNumber, int pageSize);

    List<Post> getByTime(int pageNumber, int pageSize);

    List<Post> getByPopularity(int pageNumber, int pageSize);

    boolean getCurrentLikeStatus(long postId, long userId);

    void like(long postId, long userId);

    void dislike(long postId, long userId);

    Post insert(Post post);
}
