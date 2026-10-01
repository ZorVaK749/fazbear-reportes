package com.fazbear.reportes.service;

import com.fazbear.reportes.model.PedidoEvent;
import com.fazbear.reportes.model.VentaLog;
import com.fazbear.reportes.repository.VentaLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportesService {

    private static final Logger log = LoggerFactory.getLogger(ReportesService.class);

    private final VentaLogRepository ventaLogRepository;

    public ReportesService(VentaLogRepository ventaLogRepository) {
        this.ventaLogRepository = ventaLogRepository;
    }

    /** Registra en H2 el evento de pedido recibido de RabbitMQ. */
    public void registrarVenta(PedidoEvent evento) {
        VentaLog venta = new VentaLog(
            evento.getPedidoId(),
            evento.getUsuarioId(),
            evento.getTotal(),
            evento.getFechaCreacion()
        );
        ventaLogRepository.save(venta);
        log.info("Venta registrada en reportes: pedidoId={}, total={}", evento.getPedidoId(), evento.getTotal());
    }

    /** Retorna el resumen de ingresos totales y cantidad de pedidos. */
    public Map<String, Object> getIngresosTotales() {
        BigDecimal total       = ventaLogRepository.sumTotalIngresos();
        long       totalPedidos = ventaLogRepository.count();
        Map<String, Object> res = new HashMap<>();
        res.put("totalPedidos", totalPedidos);
        res.put("ingresosTotales", total);
        return res;
    }

    /** Retorna todos los registros de ventas (útil para debug y auditoría). */
    public Map<String, Object> getResumenVentas() {
        long       totalPedidos = ventaLogRepository.count();
        BigDecimal ingresos     = ventaLogRepository.sumTotalIngresos();
        BigDecimal promedio     = totalPedidos > 0
            ? ingresos.divide(BigDecimal.valueOf(totalPedidos), 2, BigDecimal.ROUND_HALF_UP)
            : BigDecimal.ZERO;

        Map<String, Object> res = new HashMap<>();
        res.put("totalPedidos",       totalPedidos);
        res.put("ingresosTotales",    ingresos);
        res.put("promedioPerPedido",  promedio);
        res.put("ventas",             ventaLogRepository.findAll());
        return res;
    }
}
