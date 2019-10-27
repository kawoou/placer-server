package capstone.placer.post;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
public class PostDetail {

    @NonNull
    private long postId;

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
