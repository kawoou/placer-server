package capstone.placer.post;

import lombok.Data;

import java.util.Objects;

@Data
class PostRequestParam {

    private Double aperture;

    private Double focalLength;

    private Integer exposureTime;

    private Integer iso;

    private boolean flash;

    private String manufacturer;

    private String lensModel;

    private double longitude;

    private double latitude;

    private String timestamp;

    private double altitude;

    private String nickName;

    private String comment;

    public PostRequestParam(Double aperture, Double focalLength, Integer exposureTime, Integer iso, Boolean flash, String manufacturer, String lensModel, double longitude, double latitude, String timestamp, Double altitude, String nickName, String comment) {
        this.aperture = aperture;
        this.focalLength = focalLength;
        this.exposureTime = exposureTime;
        this.iso = iso;
        if (Objects.isNull(flash)) this.flash = false;
        else this.flash = flash;
        this.manufacturer = manufacturer;
        this.lensModel = lensModel;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        if (Objects.isNull(altitude)) this.altitude = 0.0;
        else this.altitude = altitude;
        this.nickName = nickName;
        this.comment = comment;
    }

}
