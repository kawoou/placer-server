package capstone.placer.post;

public interface PostDetailMapper {
    PostDetail getDetail(long postId);

    void insert(PostDetail postDetail);
}
