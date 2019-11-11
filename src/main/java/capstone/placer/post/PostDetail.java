package capstone.placer.post;

import capstone.placer.exif.Exif;
import capstone.placer.exif.Gps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class PostDetail {

    public PostDetail(long postId, Exif e, Gps g) {
        this(postId, e.getAperture(), e.getFocalLength(), e.getExposureTime(), e.getIso(), e.isFlash(), e.getManufacturer(), e.getLensModel(), g.getLongitude(), g.getLatitude(), g.getAltitude(), e.getTimestamp());
    }

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

    // 고도
    @Nullable
    private double altitude;

    // 시간
    @NonNull
    private String timestamp;
}
