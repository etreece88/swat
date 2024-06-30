package wgu.etreece.swat.models.noaa;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document("slr-winds")
public class NOAASlrWind {

    @MongoId
    private String id;

    private List<String> slrWinds;

    public String getDate() {
        return slrWinds != null && !slrWinds.isEmpty() ? slrWinds.get(0) : null;
    }
}
