package wgu.etreece.swat.services.donki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.donki.DONKIGeomagneticStorm;
import wgu.etreece.swat.repositories.donki.DONKIGSTRepository;
import wgu.etreece.swat.util.Constants;

@Service
public class DONKIGSTService {

    @Value("${donki.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DONKIGSTRepository DONKIGSTRepository;

    @Autowired
    private DONKISolarEventService DONKISolarEventService;

    public void fetchAndSave() {
        fetchGSTData();
    }

    private void fetchGSTData() {
        DONKIGeomagneticStorm[] gsts = restTemplate.getForObject(baseURL + Constants.GST_ENDPOINT, DONKIGeomagneticStorm[].class);

        if(gsts != null) {
            for(DONKIGeomagneticStorm gst : gsts) {
                if(DONKIGSTRepository.findFirstByGstID(gst.getActivityID()).isEmpty()) {
                    DONKISolarEventService.saveSolarEvent(gst, "GST");
                }
            }
        }
    }

}
