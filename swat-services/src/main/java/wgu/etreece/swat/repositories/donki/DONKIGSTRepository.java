package wgu.etreece.swat.repositories.donki;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.donki.DONKIGeomagneticStorm;

import java.util.Optional;

public interface DONKIGSTRepository extends MongoRepository<DONKIGeomagneticStorm, String> {
    Optional<DONKIGeomagneticStorm> findFirstByGstID(String gstID);
}
