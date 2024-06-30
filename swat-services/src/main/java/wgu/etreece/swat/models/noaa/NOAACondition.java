package wgu.etreece.swat.models.noaa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "noaa-conditions")
public class NOAACondition {

    @MongoId
    private String id;

    @JsonProperty("DateStamp")
    private String dateStamp;

    @JsonProperty("TimeStamp")
    private String timeStamp;

    @JsonProperty("R")
    private NOAARadioBlackout R;

    @JsonProperty("S")
    private NOAASolarRadiationStorm S;

    @JsonProperty("G")
    private NOAAGeomagneticStorm G;
}
