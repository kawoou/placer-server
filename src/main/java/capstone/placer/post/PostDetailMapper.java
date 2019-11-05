package capstone.placer.post;

public interface PostDetailMapper {
    PostDetail getDetail(int postId);

    PostDetail insert(PostDetail postDetail);
}
