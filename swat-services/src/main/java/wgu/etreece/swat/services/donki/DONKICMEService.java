package wgu.etreece.swat.services.donki;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.donki.DONKICoronalMassEjection;
import wgu.etreece.swat.repositories.donki.DONKICMERepository;
import wgu.etreece.swat.util.Constants;

@Slf4j
@Service
public class DONKICMEService {

    @Value("${donki.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DONKICMERepository DONKICMERepository;

    @Autowired
    private DONKISolarEventService DONKISolarEventService;

    public void fetchAndSave() {
        fetchCMEData();
    }

    private void fetchCMEData() {
        DONKICoronalMassEjection[] cmes = restTemplate.getForObject(baseURL + Constants.CME_ENDPOINT, DONKICoronalMassEjection[].class);

        if (cmes != null) {
            for (DONKICoronalMassEjection cme : cmes) {
                if (DONKICMERepository.findByActivityID(cme.getActivityID()).isEmpty()) {
                    DONKISolarEventService.saveSolarEvent(cme, "CME");
                }
            }
        }
    }

}
