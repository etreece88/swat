package wgu.etreece.swat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import wgu.etreece.swat.models.donki.DONKICoronalMassEjection;
import wgu.etreece.swat.models.donki.DONKISolarEvent;
import wgu.etreece.swat.repositories.donki.DONKISolarEventRepository;
import wgu.etreece.swat.services.donki.DONKISolarEventService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolarEventServiceTests {

    @InjectMocks
    private DONKISolarEventService solarEventService;

    @Mock
    private DONKISolarEventRepository slrEventRepository;

    private DONKISolarEvent solarEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        solarEvent = new DONKICoronalMassEjection();
        solarEvent.setEventType("CME");
    }

    @Test
    void testSaveSolarEvent() {
        solarEventService.saveSolarEvent(solarEvent, "CME");

        verify(slrEventRepository, times(1)).save(solarEvent);
        assertEquals("CME", solarEvent.getEventType());
    }

    @Test()
    void testGetEvents() {
        List<DONKISolarEvent> events = List.of(solarEvent);

        when(slrEventRepository.findAll()).thenReturn(events);

        List<DONKISolarEvent> result = solarEventService.getEvents();

        assertEquals(1, result.size());
        assertEquals("CME", result.get(0).getEventType());
    }
}