package wgu.etreece.swat.models.donki;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DONKICMEAnalysis {
    private boolean isMostAccurate;

    private LocalDateTime time21_5;

    private int latitude;

    private int longitude;

    private int halfAngle;

    private int speed;

    private String type;

    private String featureCode;

    private String imageType;

    private String measurementTechnique;

    private String note;

    private int levelOfData;

    private int speedMeasuredAtHeight;

    private LocalDateTime submissionTime;

    private String link;
}