package wgu.etreece.swat.repositories.noaa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import wgu.etreece.swat.models.noaa.NOAAKpi;

import java.util.Optional;

public interface NOAAKpiRepository extends MongoRepository<NOAAKpi, String> {
    @Query("{'kpis.0': ?0}")
    Optional<NOAAKpi> findByDate(String date);

    NOAAKpi findFirstByOrderByDateDesc();
}
