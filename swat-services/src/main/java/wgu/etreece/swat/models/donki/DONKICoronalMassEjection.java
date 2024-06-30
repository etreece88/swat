package wgu.etreece.swat.models.donki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "slr-events")
@EqualsAndHashCode(callSuper = true)
public class DONKICoronalMassEjection extends DONKISolarEvent {

    private String id;

    private String sourceLocation;

    private int activeRegionNum;

    private List<DONKIInstrument> DONKIInstruments;

    private List<DONKICMEAnalysis> cmeAnalyses;
}
