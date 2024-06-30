package wgu.etreece.swat.services.donki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DONKIApiService {

    @Autowired
    private DONKICMEService DONKICMEService;

    @Autowired
    private DONKIGSTService DONKIGSTService;

    @Autowired
    private DONKISolarFlareService DONKISolarFlareService;

    @Autowired
    private DONKINotificationService DONKINotificationService;

    public void fetchAndSaveData() {
        DONKICMEService.fetchAndSave();
        DONKIGSTService.fetchAndSave();
        DONKISolarFlareService.fetchAndSave();
        DONKINotificationService.fetchAndSave();
    }

}
