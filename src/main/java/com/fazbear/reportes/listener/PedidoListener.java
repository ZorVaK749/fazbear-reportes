package com.fazbear.reportes.listener;

import com.fazbear.reportes.model.PedidoEvent;
import com.fazbear.reportes.service.ReportesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * PedidoListener — escucha la cola pedido.creado.queue.
 * Cuando ms-orden confirma una compra, este listener acumula
 * la estadística en la base de datos local H2 de ms-reportes.
 */
@Component
public class PedidoListener {

    private static final Logger log = LoggerFactory.getLogger(PedidoListener.class);

    private final ReportesService reportesService;

    public PedidoListener(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @RabbitListener(queues = "pedido.creado.queue")
    public void onPedidoCreado(PedidoEvent evento) {
        log.info("Evento recibido en reportes: pedidoId={}", evento.getPedidoId());
        reportesService.registrarVenta(evento);
    }
}
