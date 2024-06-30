package wgu.etreece.swat.services.noaa;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NOAAApiService {

    @Autowired
    NOAAConditionService currConService;

    @Autowired
    NOAAAlertService alertService;

    @Autowired
    NOAAKpiService kpiService;

    @Autowired
    NOAASolarProbService solarProbService;

    @Autowired
    NOAASlrWindService slrWindService;

    @Async
    public void fetchAndSaveData() throws JsonProcessingException {
        currConService.fetchAndSave();
        alertService.fetchAndSave();
        kpiService.fetchAndSave();
        solarProbService.fetchAndSave();
        slrWindService.fetchAndSave();
    }

}
