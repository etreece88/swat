package wgu.etreece.swat.models.donki;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("slr-events")
public class EventType {
    @JsonProperty("eventType")
    private String eventType;
}
