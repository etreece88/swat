package wgu.etreece.swat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wgu.etreece.swat.models.noaa.*;
import wgu.etreece.swat.services.noaa.*;
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/noaa")
public class NOAAController {

    @Autowired
    NOAAConditionService conditionService;

    @Autowired
    NOAAAlertService alertService;

    @Autowired
    NOAAKpiService kpiService;

    @Autowired
    NOAASolarProbService solarProbService;

    @Autowired
    NOAASlrWindService slrWindService;

    @GetMapping(value = "/curr-con")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<NOAACondition> getCurrentConditions() {
        try {
            return new ResponseEntity<>(conditionService.getCurrentCondition(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/curr-kpi")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<NOAAKpi> getCurrentKpi() {
        try {
            return new ResponseEntity<>(kpiService.getCurrentKpi(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/curr-slr-prb")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<NOAASolarProb> getCurrentSolarProb() {
        try {
            return new ResponseEntity<>(solarProbService.getCurrentSolarProb(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/curr-slr-wind")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<NOAASlrWind> getCurrentSolarWind() {
        try {
            return new ResponseEntity<>(slrWindService.getCurrentSolarWind(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/recent-alert")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<NOAAAlert> getMostRecentAlert() {
        try {
            return new ResponseEntity<>(alertService.getMostRecentAlert(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
