package capstone.placer.post;

import capstone.placer.util.Paging;
import capstone.placer.util.UploadUtil;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final String UPLOAD_PATH = "photos";

    @GetMapping("/get/{page}")
    public List<PostWithLike> get(@PathVariable int page, @RequestParam long userId) {
        Paging paging = new Paging(page);
        return postService.get(paging, userId);
    }

    @GetMapping("/getByTime/{page}")
    public List<PostWithLike> getByTime(@PathVariable int page, @RequestParam long userId, @RequestParam double latitude, @RequestParam double longitude, @RequestParam double zoom) {
        Paging paging = new Paging(page);
        return postService.getByTime(paging, userId, latitude, longitude, zoom);
    }

    @GetMapping("/getByPopularity/{page}")
    public List<PostWithLike> getByPopularity(@PathVariable int page, @RequestParam long userId, @RequestParam double latitude, @RequestParam double longitude, @RequestParam double zoom) {
        Paging paging = new Paging(page);
        return postService.getByPopularity(paging, userId, latitude, longitude, zoom);
    }

    @PostMapping("/like/{postId}/{userId}")
    public boolean like(@PathVariable long postId, @PathVariable long userId) {
        return postService.toggleLike(postId, userId);
    }

    @PostMapping("/post")
    public long post(@RequestParam("file") MultipartFile file, @RequestParam("info") PostParams params) throws Exception {
        ResponseEntity<String> img_path = new ResponseEntity<>(UploadUtil.uploadFile(UPLOAD_PATH, file.getOriginalFilename(), file.getBytes())
                , HttpStatus.CREATED);
        String s3Path = img_path.getBody();

        Post post = postService.insert(new Post(params.getWriterNickName(), s3Path, params.getComment()));
        //PostDetail postDetail = postService.insertDetail(new PostDetail(post.getId(), ))
        return 0L;
    }

    @Data
    private class PostParams {
        @NonNull
        private String writerNickName;

        @Nullable
        private String comment;
    }
}


