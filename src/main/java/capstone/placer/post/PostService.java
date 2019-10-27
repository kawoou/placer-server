package capstone.placer.post;

import capstone.placer.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostDetailMapper postDetailMapper;

    public List<Post> get(Paging paging) {
       return postMapper.get(paging.getPageNumber(), Paging.PAGE_SIZE);
    }

    public PostDetail getDetail(int postId) {
        return postDetailMapper.getDetail(postId);
    }

}
