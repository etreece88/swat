package wgu.etreece.swat.models.noaa;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "slr-prb")
public class NOAASolarProb {

    @MongoId
    private String id;

    private String date;

    private String c_class_1_day;

    private String c_class_2_day;

    private String c_class_3_day;

    private String m_class_1_day;

    private String m_class_2_day;

    private String m_class_3_day;

    private String x_class_1_day;

    private String x_class_2_day;

    private String x_class_3_day;

    private String polar_cap_absorption;

}
