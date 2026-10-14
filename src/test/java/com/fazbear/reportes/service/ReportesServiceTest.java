package com.fazbear.reportes.service;

import com.fazbear.reportes.model.PedidoEvent;
import com.fazbear.reportes.model.VentaLog;
import com.fazbear.reportes.repository.VentaLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportesServiceTest {
    @Test
    void eventDateIsParsedBeforePersisting() {
        VentaLogRepository repository = mock(VentaLogRepository.class);
        PedidoEvent event = new PedidoEvent();
        event.setPedidoId(42L);
        event.setTotal(new BigDecimal("12.99"));
        event.setFechaCreacion("2026-10-14T10:30:00");
        new ReportesService(repository).registrarVenta(event);
        ArgumentCaptor<VentaLog> captor = ArgumentCaptor.forClass(VentaLog.class);
        verify(repository).save(captor.capture());
        assertEquals(LocalDateTime.of(2026, 10, 14, 10, 30), captor.getValue().getFechaCreacion());
        assertEquals(42L, captor.getValue().getPedidoId());
    }

    @Test
    void malformedDateDoesNotPersistIncorrectStatistics() {
        VentaLogRepository repository = mock(VentaLogRepository.class);
        PedidoEvent event = new PedidoEvent();
        event.setFechaCreacion("invalid");
        assertThrows(java.time.format.DateTimeParseException.class,
            () -> new ReportesService(repository).registrarVenta(event));
        verifyNoInteractions(repository);
    }
}
