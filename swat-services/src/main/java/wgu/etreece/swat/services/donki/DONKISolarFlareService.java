package wgu.etreece.swat.services.donki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.donki.DONKISolarFlare;
import wgu.etreece.swat.repositories.donki.DONKISolarFlareRepository;
import wgu.etreece.swat.util.Constants;

@Service
public class DONKISolarFlareService {

    @Value("${donki.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DONKISolarFlareRepository DONKISolarFlareRepository;

    @Autowired
    private DONKISolarEventService DONKISolarEventService;

    public void fetchAndSave() {
        fetchSolarFlareData();
    }

    private void fetchSolarFlareData() {
        DONKISolarFlare[] flares = restTemplate.getForObject(baseURL + Constants.FLR_ENDPOINT, DONKISolarFlare[].class);

        if(flares != null) {
            for(DONKISolarFlare flare : flares) {
                if(DONKISolarFlareRepository.findByFlrID(flare.getFlrID()).isEmpty()) {
                    DONKISolarEventService.saveSolarEvent(flare, "FLR");
                }
            }
        }
    }
}
