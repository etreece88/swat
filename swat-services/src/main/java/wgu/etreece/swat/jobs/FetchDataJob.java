package wgu.etreece.swat.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wgu.etreece.swat.services.DataService;

import java.io.IOException;

@Service
@Slf4j
public class FetchDataJob {

    @Autowired
    private DataService dataService;

    // Fetches API Data Every 3 Hours
    @Scheduled(fixedDelayString = "${api.delay}")
    public void fetchApiData() throws IOException {
        log.info("Fetching API data...");
        dataService.fetchAndSaveData();
    }
}
