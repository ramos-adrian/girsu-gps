package ar.ramos.girsugps.internal.positionrecord;

import ar.ramos.girsugps.internal.truck.TruckLocationUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PositionRecordServiceTest {

    @Mock
    private IPositionRecordRepository positionRecordRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PositionRecordService positionRecordService;

    @Captor
    private ArgumentCaptor<TruckLocationUpdateDTO> truckLocationUpdateCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldPersistPositionRecordAndPublishEvent() {
        long timestamp = System.currentTimeMillis();

        PositionRecord positionRecord = new PositionRecord();
        positionRecord.setTruckId(1L);
        positionRecord.setLatitude(40.7128);
        positionRecord.setLongitude(-74.0060);
        positionRecord.setTimestamp(timestamp);

        PositionRecord savedRecord = new PositionRecord();
        savedRecord.setId(1L);
        savedRecord.setTruckId(1L);
        savedRecord.setLatitude(40.7128);
        savedRecord.setLongitude(-74.0060);
        savedRecord.setTimestamp(timestamp);

        when(positionRecordRepository.save(positionRecord)).thenReturn(savedRecord);

        positionRecordService.save(positionRecord);

        verify(eventPublisher).publishEvent(truckLocationUpdateCaptor.capture());
        TruckLocationUpdateDTO capturedUpdate = truckLocationUpdateCaptor.getValue();

        assertThat(capturedUpdate.getId()).isEqualTo(savedRecord.getId());
        assertThat(capturedUpdate.getTruckId()).isEqualTo(savedRecord.getTruckId());
        assertThat(capturedUpdate.getLatitude()).isEqualTo(savedRecord.getLatitude());
        assertThat(capturedUpdate.getLongitude()).isEqualTo(savedRecord.getLongitude());
        assertThat(capturedUpdate.getTimestamp()).isEqualTo(savedRecord.getTimestamp());
    }
}