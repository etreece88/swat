package wgu.etreece.swat.repositories.donki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.donki.DONKINotification;

import java.util.List;
import java.util.Optional;

public interface DONKINotificationRepository extends MongoRepository<DONKINotification, String> {
    Optional<DONKINotification> findByMessageID(String messageID);

    List<DONKINotification> findByMessageTypeOrderByMessageIssueTimeDesc(String type);

    List<DONKINotification> findAllByOrderByMessageIssueTimeDesc();
}
