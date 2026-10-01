package com.fazbear.reportes.repository;

import com.fazbear.reportes.model.VentaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VentaLogRepository extends JpaRepository<VentaLog, Long> {

    /** Suma de todos los ingresos registrados. */
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM VentaLog v")
    BigDecimal sumTotalIngresos();

    /** Total de pedidos registrados. */
    long count();
}
