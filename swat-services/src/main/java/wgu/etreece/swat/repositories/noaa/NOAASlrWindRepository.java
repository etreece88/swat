package wgu.etreece.swat.repositories.noaa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import wgu.etreece.swat.models.noaa.NOAASlrWind;

import java.util.Optional;

public interface NOAASlrWindRepository extends MongoRepository<NOAASlrWind, String> {
    @Query("{'slrWinds.0': ?0}")
    Optional<NOAASlrWind> findByDate(String date);

    NOAASlrWind findFirstByOrderByDateDesc();
}
