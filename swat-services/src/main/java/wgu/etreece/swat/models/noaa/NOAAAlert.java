package wgu.etreece.swat.models.noaa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "noaa-alerts")
public class NOAAAlert {

    @MongoId
    private String id;

    private String product_id;

    @JsonProperty("issue_datetime")
    private String date;

    private String message;
}
