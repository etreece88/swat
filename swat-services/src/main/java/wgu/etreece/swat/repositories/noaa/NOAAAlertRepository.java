package wgu.etreece.swat.repositories.noaa;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.noaa.NOAAAlert;

import java.util.Optional;

public interface NOAAAlertRepository extends MongoRepository<NOAAAlert, String> {
    Optional<NOAAAlert> findByDate(String date);

    NOAAAlert findFirstByOrderByDateDesc();
}
