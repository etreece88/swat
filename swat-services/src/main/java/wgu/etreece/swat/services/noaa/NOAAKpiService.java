package wgu.etreece.swat.services.noaa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.noaa.NOAAKpi;
import wgu.etreece.swat.repositories.noaa.NOAAKpiRepository;
import wgu.etreece.swat.util.Constants;

import java.util.List;

@Service
public class NOAAKpiService {

    @Value("${noaa.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private NOAAKpiRepository kpiRepository;

    public void fetchAndSave() throws JsonProcessingException {
        fetchKpiData();
    }

    private void fetchKpiData() throws JsonProcessingException {
        String json = restTemplate.getForObject(baseURL + Constants.KPI_ENDPOINT, String.class);
        JsonNode rootNode = objectMapper.readTree(json);


        for(int i = 1; i < rootNode.size(); i++) {
            JsonNode kpiNode = rootNode.get(i);

            NOAAKpi kpi = new NOAAKpi();

            kpi.setKpis(objectMapper.convertValue(kpiNode, List.class));

            String date = kpi.getDate();

            if(date != null && kpiRepository.findByDate(date).isEmpty()) {
                saveKpi(kpi);
            }
        }

    }

    private void saveKpi(NOAAKpi kpi) {
        kpiRepository.save(kpi);
    }

    public NOAAKpi getCurrentKpi() {
        return kpiRepository.findFirstByOrderByDateDesc();
    }

}
