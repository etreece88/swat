package wgu.etreece.swat.repositories.donki;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.donki.DONKICoronalMassEjection;

import java.util.Optional;

public interface DONKICMERepository extends MongoRepository<DONKICoronalMassEjection, String> {
    Optional<DONKICoronalMassEjection> findByActivityID(String activityID);
}
