package capstone.placer.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
class PostRequestParam {

    private double aperture;

    private double focalLength;

    private Integer exposureTime;

    private Integer iso;

    private Boolean flash;

    private String manufacturer;

    private String lensModel;

    private double longitude;

    private double latitude;

    private String timestamp;

    private double altitude;

    private String nickName;

    private String comment;

}
