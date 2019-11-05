package capstone.placer.post;


import java.util.List;

public interface PostMapper {
    List<Post> get(int pageNumber, int pageSize);

    Post insert(Post post);
}
