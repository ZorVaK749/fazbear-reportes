package com.fazbear.reportes.controller;

import com.fazbear.reportes.service.ReportesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ReportesController — expone estadísticas de ventas.
 *
 * GET /api/reportes/resumen        → total pedidos, ingresos, promedio y listado de ventas
 * GET /api/reportes/ingresos       → solo total de ingresos y cantidad de pedidos
 * GET /api/reportes/health         → health check sin auth
 */
@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "https://w8xu8o4pd7.execute-api.us-east-1.amazonaws.com")
public class ReportesController {

    private final ReportesService reportesService;

    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    /** Resumen completo: pedidos, ingresos totales, promedio por pedido, y listado. */
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> getResumen() {
        return ResponseEntity.ok(reportesService.getResumenVentas());
    }

    /** Solo los totales de ingresos y cantidad de pedidos. */
    @GetMapping("/ingresos")
    public ResponseEntity<Map<String, Object>> getIngresos() {
        return ResponseEntity.ok(reportesService.getIngresosTotales());
    }

    /** Health check — no requiere auth en este endpoint. */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "ms-reportes"));
    }
}
