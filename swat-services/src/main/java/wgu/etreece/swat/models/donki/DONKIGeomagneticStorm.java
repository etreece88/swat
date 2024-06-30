package wgu.etreece.swat.models.donki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "slr-events")
@EqualsAndHashCode(callSuper = true)
public class DONKIGeomagneticStorm extends DONKISolarEvent {

    private String id;

    private String gstID;

    private List<DONKIKpIndex> allDONKIKpIndices;

}
