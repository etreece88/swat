package wgu.etreece.swat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wgu.etreece.swat.services.donki.DONKIApiService;
import wgu.etreece.swat.services.noaa.NOAAApiService;

@Service
public class DataService {

    @Autowired
    private DONKIApiService donkiApiService;

    @Autowired
    private NOAAApiService noaaApiService;

    @Async
    public void fetchAndSaveData() throws JsonProcessingException {
        donkiApiService.fetchAndSaveData();
        noaaApiService.fetchAndSaveData();
    }
}
