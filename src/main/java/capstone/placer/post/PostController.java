package capstone.placer.post;

import capstone.placer.exception.MetadataMissingException;
import capstone.placer.exception.PostNotExistException;
import capstone.placer.exception.UserNotExistException;
import capstone.placer.exif.Exif;
import capstone.placer.exif.Extractor;
import capstone.placer.exif.Gps;
import capstone.placer.user.UserService;
import capstone.placer.util.Paging;
import capstone.placer.util.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Post post(@ModelAttribute("post") PostRequestParam param, @RequestParam("file") MultipartFile file) throws Exception {
        Gps gps;
        Exif exif;
        try {
            gps = new Gps(param.getLongitude(), param.getLatitude(), param.getAltitude());
            exif = new Exif(param.getAperture(), param.getFocalLength(), param.getExposureTime(), param.getIso(), param.isFlash(), param.getManufacturer(), param.getLensModel(), param.getTimestamp());
        } catch (IllegalArgumentException e) {
            throw new MetadataMissingException("업로드에 요구되는 정보가 누락된 사진입니다.");
        }

        // upload to S3 Storage
        ResponseEntity<String> img_path = new ResponseEntity<>(UploadUtil.uploadFile(UPLOAD_PATH, file.getOriginalFilename(), file.getBytes())
                , HttpStatus.CREATED);
        String s3Path = img_path.getBody();

        // Generate Post Instance
        Post post = new Post(param.getNickName(), s3Path, param.getComment());
        postService.insert(post);

        // Generate Post Detail Instance using Post Instance's information
        PostDetail postDetail = new PostDetail(post.getId(), exif, gps);
        postService.insertDetail(postDetail);

        // Generate Spatial Index
        postService.insertSpatialIndex(post.getId(), postDetail.getLatitude(), postDetail.getLongitude());
        return post;
    }

}


