package wgu.etreece.swat.repositories.noaa;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.noaa.NOAASolarProb;

import java.util.Optional;

public interface NOAASolarProbRepository extends MongoRepository<NOAASolarProb, String> {
    Optional<NOAASolarProb> findByDate(String date);

    NOAASolarProb findFirstByOrderByDateDesc();
}
