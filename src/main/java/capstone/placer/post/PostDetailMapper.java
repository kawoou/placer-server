package capstone.placer.post;

public interface PostDetailMapper {
    PostDetail getDetail(long postId);

    PostDetail insert(PostDetail postDetail);
}
