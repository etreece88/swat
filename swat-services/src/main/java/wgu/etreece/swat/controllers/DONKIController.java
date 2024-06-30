package wgu.etreece.swat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wgu.etreece.swat.models.donki.DONKINotification;
import wgu.etreece.swat.models.donki.DONKISolarEvent;
import wgu.etreece.swat.services.donki.*;
import wgu.etreece.swat.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/donki")
public class DONKIController {

    @Autowired
    private DONKISolarEventService slrEventService;

    @Autowired
    private DONKINotificationService notificationService;

    @GetMapping("/events")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DONKISolarEvent>> getEvents() {
        try {

            List<DONKISolarEvent> events = new ArrayList<>(slrEventService.getEvents());

            return new ResponseEntity<>(events, HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error retrieving solar events page: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notifications")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DONKINotification>> getNotifications() {
        try {

            List<DONKINotification> notifications = new ArrayList<>(notificationService.getAllNotifications());

            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
        catch(Exception e) {
            log.error("DONKI - Error retrieving notifications page: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/event-types")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<String>> getEventTypes() {
        try {
            List<String> eventTypes = slrEventService.getEventTypes();
            return new ResponseEntity<>(eventTypes, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("DONKI - Failed retrieving event types: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notification-types")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<String>> getNotificationTypes() {
        try {
            List<String> notificationTypes = notificationService.getNotificationTypes();
            notificationTypes.add("ALL");
            return new ResponseEntity<>(notificationTypes, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("DONKI - Failed retrieving notification types: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
