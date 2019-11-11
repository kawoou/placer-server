package capstone.placer.post;

import capstone.placer.exception.PostNotExistException;
import capstone.placer.exception.UserNotExistException;
import capstone.placer.exif.Extractor;
import capstone.placer.user.UserService;
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
    private final UserService userService;

    private static final String UPLOAD_PATH = "photos";

    @GetMapping("/getByTime/{page}")
    public List<PostWithLike> getByTime(@PathVariable int page, @RequestParam long userId, @RequestParam double latitude, @RequestParam double longitude, @RequestParam double zoom) throws UserNotExistException {
        if (!userService.isExistUser(userId)) {
            throw new UserNotExistException("존재하지 않는 유저입니다.");
        }

        Paging paging = new Paging(page);
        return postService.getByTime(paging, userId, latitude, longitude, zoom);
    }

    @GetMapping("/getByPopularity/{page}")
    public List<PostWithLike> getByPopularity(@PathVariable int page, @RequestParam long userId, @RequestParam double latitude, @RequestParam double longitude, @RequestParam double zoom) throws UserNotExistException {
        if (!userService.isExistUser(userId)) {
            throw new UserNotExistException("존재하지 않는 유저입니다.");
        }

        Paging paging = new Paging(page);
        return postService.getByPopularity(paging, userId, latitude, longitude, zoom);
    }

    @GetMapping("/detail/{postId}")
    public PostDetail getDetail(@PathVariable long postId) throws PostNotExistException {
        if (!postService.isExistPost(postId)) {
            throw new PostNotExistException("존재하지 않는 포스트입니다.");
        }
        return postService.getDetail(postId);
    }


    @PostMapping("/like/{postId}/{userId}")
    public boolean like(@PathVariable long postId, @PathVariable long userId) throws PostNotExistException, UserNotExistException {
        if (!postService.isExistPost(postId)) {
            throw new PostNotExistException("존재하지 않는 포스트입니다.");
        }

        if (!userService.isExistUser(userId)) {
            throw new UserNotExistException("존재하지 않는 유저입니다.");
        }

        return postService.toggleLike(postId, userId);
    }

    @PostMapping("/post")
    public Post post(@RequestParam("file") MultipartFile file, @RequestParam("info") PostParams params) throws Exception {
        ResponseEntity<String> img_path = new ResponseEntity<>(UploadUtil.uploadFile(UPLOAD_PATH, file.getOriginalFilename(), file.getBytes())
                , HttpStatus.CREATED);
        String s3Path = img_path.getBody();

        //TODO post, postdetail, spatial index 만들고, s3에 업로드하기.
        Post post = postService.insert(new Post(params.getWriterNickName(), s3Path, params.getComment()));
        PostDetail postDetail = postService.insertDetail(new PostDetail(post.getId(), Extractor.extractExif(file.getBytes()), Extractor.extractGPS(file.getBytes())));
        postService.insertSpatialIndex(post.getId(), postDetail.getLatitude(), postDetail.getLongitude());
        return post;
    }

    @Data
    private class PostParams {
        @NonNull
        private String writerNickName;

        @Nullable
        private String comment;
    }
}


