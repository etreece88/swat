package wgu.etreece.swat.models.donki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "slr-events")
@EqualsAndHashCode(callSuper = true)
public class DONKISolarFlare extends DONKISolarEvent {

    private String id;

    private String flrID;

    private List<DONKIInstrument> DONKIInstruments;

    private String beginTime;

    private String peakTime;

    private String endTime;

    private String classType;

    private String sourceLocation;

    private int activeRegionNum;

}
