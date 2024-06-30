package wgu.etreece.swat.repositories.donki;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.donki.DONKISolarFlare;

import java.util.Optional;

public interface DONKISolarFlareRepository extends MongoRepository<DONKISolarFlare, String> {
    Optional<DONKISolarFlare> findByFlrID(String flrID);
}
