package wgu.etreece.swat.models.noaa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NOAAGeomagneticStorm {

    @JsonProperty("Scale")
    private String Scale;

    @JsonProperty("Text")
    private String Text;
}
