package wgu.etreece.swat.models.noaa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NOAASolarRadiationStorm {

    @JsonProperty("Scale")
    private String Scale;

    @JsonProperty("Text")
    private String Text;

    @JsonProperty("Prob")
    private String Prob;

}
