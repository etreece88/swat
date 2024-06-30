package wgu.etreece.swat.models.donki;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class NotificationType {
    @Field("messageType")
    private String messageType;
}
