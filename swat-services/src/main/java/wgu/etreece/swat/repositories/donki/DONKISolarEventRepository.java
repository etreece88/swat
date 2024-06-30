package wgu.etreece.swat.repositories.donki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.donki.DONKISolarEvent;

import java.util.List;

public interface DONKISolarEventRepository extends MongoRepository<DONKISolarEvent, String> {

    List<DONKISolarEvent> findByEventType(String eventType);
}
