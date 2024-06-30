package wgu.etreece.swat.services.noaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.noaa.NOAASolarProb;
import wgu.etreece.swat.repositories.noaa.NOAASolarProbRepository;
import wgu.etreece.swat.util.Constants;

@Service
public class NOAASolarProbService {
    @Value("${noaa.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NOAASolarProbRepository solarProbRepository;

    public void fetchAndSave() {
        fetchSolarProbData();
    }

    public NOAASolarProb getCurrentSolarProb() {
        return solarProbRepository.findFirstByOrderByDateDesc();
    }

    private void fetchSolarProbData() {
        NOAASolarProb[] solarProbs = restTemplate.getForObject(baseURL + Constants.SLR_PRB_ENDPOINT, NOAASolarProb[].class);

        if(solarProbs != null) {
            for(NOAASolarProb solarProb : solarProbs) {
                if(solarProbRepository.findByDate(solarProb.getDate()).isEmpty()) {
                    saveSolarProb(solarProb);
                }
            }
        }
    }

    private void saveSolarProb(NOAASolarProb solarProb) {
        solarProbRepository.save(solarProb);
    }
}
