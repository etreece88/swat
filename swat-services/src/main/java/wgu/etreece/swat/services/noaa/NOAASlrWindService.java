package wgu.etreece.swat.services.noaa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.noaa.NOAASlrWind;
import wgu.etreece.swat.repositories.noaa.NOAASlrWindRepository;
import wgu.etreece.swat.util.Constants;

import java.util.List;

@Service
public class NOAASlrWindService {

    @Value("${noaa.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NOAASlrWindRepository slrWindRepository;

    public void fetchAndSave() throws JsonProcessingException {
        fetchSlrWindData();
    }

    public NOAASlrWind getCurrentSolarWind() {
        return slrWindRepository.findFirstByOrderByDateDesc();
    }

    private void fetchSlrWindData() throws JsonProcessingException {
        String json = restTemplate.getForObject(baseURL + Constants.SLR_WIND_ENDPOINT, String.class);
        JsonNode rootNode = objectMapper.readTree(json);


        for(int i = 1; i < rootNode.size(); i++) {
            JsonNode slrWindNode = rootNode.get(i);

            NOAASlrWind slrWind = new NOAASlrWind();

            slrWind.setSlrWinds(objectMapper.convertValue(slrWindNode, List.class));

            String date = slrWind.getDate();

            if(date != null && slrWindRepository.findByDate(date).isEmpty()) {
                saveSlrWind(slrWind);
            }
        }
    }

    private void saveSlrWind(NOAASlrWind slrWind) {
        slrWindRepository.save(slrWind);
    }

}
