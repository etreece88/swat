package wgu.etreece.swat.models.noaa;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "kpis")
public class NOAAKpi {

    @MongoId
    private String id;

    private List<String> kpis;

    public String getDate() {
        return kpis != null && !kpis.isEmpty() ? kpis.get(0) : null;
    }
}
