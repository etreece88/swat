package wgu.etreece.swat.models.donki;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "slr-events")
public abstract class DONKISolarEvent {

    @MongoId
    private String id;

    private String activityID;

    private String eventType;

    private String startTime;

    private List<DONKILinkedEvents> DONKILinkedEvents;

    private String note;

    private String catalog;

}
