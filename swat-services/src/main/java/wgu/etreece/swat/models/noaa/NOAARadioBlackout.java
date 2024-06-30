package wgu.etreece.swat.models.noaa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NOAARadioBlackout {

    @JsonProperty("Scale")
    private String Scale;

    @JsonProperty("Text")
    private String Text;

    @JsonProperty("MinorProb")
    private String MinorProb;

    @JsonProperty("MajorProb")
    private String MajorProb;
}
