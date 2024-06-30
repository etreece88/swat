package wgu.etreece.swat.models.donki;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notifications")
public class DONKINotification {

    private String id;

    private String messageType;

    private String messageID;

    private String messageURL;

    private String messageIssueTime;

    private String messageBody;

}
