package com.fazbear.reportes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que registra cada venta recibida vía RabbitMQ.
 * Se acumula en H2 para generar estadísticas por categoría y producto.
 */
@Entity
@Table(name = "ventas_log")
public class VentaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private String usuarioId;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;

    public VentaLog() {}

    public VentaLog(Long pedidoId, String usuarioId, BigDecimal total, LocalDateTime fechaCreacion) {
        this.pedidoId      = pedidoId;
        this.usuarioId     = usuarioId;
        this.total         = total;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId()                            { return id; }
    public Long getPedidoId()                      { return pedidoId; }
    public void setPedidoId(Long v)                { this.pedidoId = v; }
    public String getUsuarioId()                   { return usuarioId; }
    public void setUsuarioId(String v)             { this.usuarioId = v; }
    public BigDecimal getTotal()                   { return total; }
    public void setTotal(BigDecimal v)             { this.total = v; }
    public LocalDateTime getFechaCreacion()        { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime v)  { this.fechaCreacion = v; }
}
