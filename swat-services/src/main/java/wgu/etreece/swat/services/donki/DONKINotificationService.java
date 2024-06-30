package wgu.etreece.swat.services.donki;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.donki.DONKINotification;
import wgu.etreece.swat.models.donki.DONKISolarEvent;
import wgu.etreece.swat.models.donki.NotificationType;
import wgu.etreece.swat.repositories.donki.DONKINotificationRepository;
import wgu.etreece.swat.util.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DONKINotificationService {

    @Value("${donki.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DONKINotificationRepository notificationRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void fetchAndSave() {
        fetchNotifications();
    }

    public List<DONKINotification> getAllNotifications() {
        return notificationRepository.findAllByOrderByMessageIssueTimeDesc();
    }

    public List<DONKINotification> getNotificationsByType(String type) {
        return notificationRepository.findByMessageTypeOrderByMessageIssueTimeDesc(type);
    }

    public List<String> getNotificationTypes() {
        TypedAggregation<DONKINotification> aggregation = Aggregation.newAggregation(
                DONKINotification.class,
                Aggregation.group("messageType").first("messageType").as("messageType")
        );

        AggregationResults<Document> eventTypes = mongoTemplate.aggregate(aggregation, "notifications", Document.class);

        List<Document> events = eventTypes.getMappedResults();

        return events.stream()
                .map(doc -> doc.getString("messageType"))
                .collect(Collectors.toList());
    }

    private void fetchNotifications() {
        DONKINotification[] DONKINotifications = restTemplate.getForObject(baseURL + Constants.NOTIFICATION_ENDPOINT, DONKINotification[].class);

        if(DONKINotifications != null) {
            for(DONKINotification DONKINotification : DONKINotifications) {
                if(notificationRepository.findByMessageID(DONKINotification.getMessageID()).isEmpty()) {
                    saveNotification(DONKINotification);
                }
            }
        }
    }

    private void saveNotification(DONKINotification notification) {
        notificationRepository.save(notification);
    }

}
