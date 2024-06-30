package wgu.etreece.swat.services.donki;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;
import wgu.etreece.swat.models.donki.*;
import wgu.etreece.swat.repositories.donki.DONKISolarEventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DONKISolarEventService {

    @Autowired
    private DONKISolarEventRepository slrEventRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveSolarEvent(DONKISolarEvent event, String eventType) {

        if(event instanceof DONKICoronalMassEjection || event instanceof DONKISolarFlare || event instanceof DONKIGeomagneticStorm) {
            event.setEventType(eventType);
            slrEventRepository.save(event);
        }
        else {
            log.warn("Event not recognized: " + event.toString());
        }
    }

    public List<DONKISolarEvent> getEvents() {
        return slrEventRepository.findAll();
    }

    public List<String> getEventTypes() {
        TypedAggregation<DONKISolarEvent> aggregation = Aggregation.newAggregation(
                DONKISolarEvent.class,
                Aggregation.group("eventType").first("eventType").as("eventType")
        );

        AggregationResults<Document> eventTypes = mongoTemplate.aggregate(aggregation, "slr-events", Document.class);

        List<Document> events = eventTypes.getMappedResults();


        return events.stream()
                .map(doc -> doc.getString("eventType"))
                .collect(Collectors.toList());
    }

}
