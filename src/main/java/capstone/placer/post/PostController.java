package capstone.placer.post;

import capstone.placer.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/get/{page}")
    public List<Post> get(@PathVariable int page) {
        Paging paging = new Paging(page);
        return postService.get(paging);
    }

    @GetMapping("/getDetail/{postId}")
    public PostDetail getDetail(@PathVariable int postId) {
        return postService.getDetail(postId);
    }


}
