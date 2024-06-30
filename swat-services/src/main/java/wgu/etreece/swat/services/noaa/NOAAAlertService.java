package wgu.etreece.swat.services.noaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.noaa.NOAAAlert;
import wgu.etreece.swat.repositories.noaa.NOAAAlertRepository;
import wgu.etreece.swat.util.Constants;

@Service
public class NOAAAlertService {

    @Value("${noaa.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NOAAAlertRepository alertRepository;

    public void fetchAndSave() {
        fetchAlertData();
    }

    public NOAAAlert getMostRecentAlert() {
        return alertRepository.findFirstByOrderByDateDesc();
    }

    private void fetchAlertData() {
        NOAAAlert[] alerts = restTemplate.getForObject(baseURL + Constants.ALERTS_ENDPOINT, NOAAAlert[].class);

        if(alerts != null) {
            for(NOAAAlert alert : alerts) {
                if(alertRepository.findByDate(alert.getDate()).isEmpty()) {
                    saveAlert(alert);
                }
            }
        }
    }

    private void saveAlert(NOAAAlert alert) {
        alertRepository.save(alert);
    }

}
