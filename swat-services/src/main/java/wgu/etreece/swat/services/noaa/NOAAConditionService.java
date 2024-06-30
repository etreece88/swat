package wgu.etreece.swat.services.noaa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wgu.etreece.swat.models.noaa.*;
import wgu.etreece.swat.repositories.noaa.NOAAConditionRepository;
import wgu.etreece.swat.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class NOAAConditionService {

    @Value("${noaa.api.base.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NOAAConditionRepository currConRepository;

    public void fetchAndSave() throws JsonProcessingException {
        fetchCMEData();
    }

    private void fetchCMEData() throws JsonProcessingException {
        String json = restTemplate.getForObject(baseURL + Constants.CURR_CON_ENDPOINT, String.class);
        JsonNode rootNode = objectMapper.readTree(json);

        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

        while(fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();

            JsonNode conditionNode = field.getValue();

            NOAACondition con = new NOAACondition();

            con.setDateStamp(conditionNode.get("DateStamp").asText());
            con.setTimeStamp(conditionNode.get("TimeStamp").asText());
            con.setG(objectMapper.convertValue(conditionNode.get("G"), NOAAGeomagneticStorm.class));
            con.setR(objectMapper.convertValue(conditionNode.get("R"), NOAARadioBlackout.class));
            con.setS(objectMapper.convertValue(conditionNode.get("S"), NOAASolarRadiationStorm.class));

            if(currConRepository.findByDateStampAndTimeStamp(con.getDateStamp(), con.getTimeStamp()).isEmpty()) {
                saveCurrCon(con);
            }
        }
    }

    private void saveCurrCon(NOAACondition con) {
        currConRepository.save(con);
    }

    public NOAACondition getCurrentCondition() {
        return currConRepository.findTopByDateStampOrderByTimeStampDesc(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

}
