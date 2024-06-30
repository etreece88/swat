package wgu.etreece.swat.repositories.noaa;

import org.springframework.data.mongodb.repository.MongoRepository;
import wgu.etreece.swat.models.noaa.NOAACondition;

import java.util.Optional;

public interface NOAAConditionRepository extends MongoRepository<NOAACondition, String> {
    Optional<NOAACondition> findByDateStampAndTimeStamp(String dateStamp, String timeStamp);

    NOAACondition findTopByDateStampOrderByTimeStampDesc(String date);
}
