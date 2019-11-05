package capstone.placer.post;

import capstone.placer.util.Paging;
import capstone.placer.util.S3Util;
import capstone.placer.util.UploadUtil;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final String UPLOAD_PATH = "photos";
    @GetMapping("/get/{page}")
    public List<Post> get(@PathVariable int page) {
        Paging paging = new Paging(page);
        return postService.get(paging);
    }

    @GetMapping("/getDetail/{postId}")
    public PostDetail getDetail(@PathVariable int postId) {
        return postService.getDetail(postId);
    }

    @PostMapping("/post")
    public long post(@RequestParam("file") MultipartFile file, @RequestParam("info") PostParams params) throws Exception {
        ResponseEntity<String> img_path = new ResponseEntity<>(UploadUtil.uploadFile(UPLOAD_PATH, file.getOriginalFilename(), file.getBytes())
                , HttpStatus.CREATED);
        String s3Path = img_path.getBody();

        Post post = postService.insert(new Post(params.getWriterNickName(), s3Path, params.comment));
        return 0L;
    }

    @Data
    class PostParams {

        @NonNull
        private String writerNickName;

        @Nullable
        private String comment;

        // 조리개 값
        @Nullable
        private Double aperture;

        // 초점 거리
        @Nullable
        private Double focalLength;

        // 노출 시간
        // 1/n 형식
        @Nullable
        private Integer exposureTime;

        // ISO
        @Nullable
        private Integer iso;

        // Flash
        @Nullable
        private Boolean flash;

        // 제조사
        @Nullable
        private String manufacturer;

        // 렌즈 모델
        @Nullable
        private String lensModel;

        // required data
        // 경도
        @NonNull
        private double longitude;

        // 위도
        @NonNull
        private double latitude;

        // 시간
        @NonNull
        private long timestamp;

    }
}


