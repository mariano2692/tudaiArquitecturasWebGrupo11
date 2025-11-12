package com.viajes.repository;
import com.viajes.dto.ReporteMonopatinesPorViajesYAnio;
import com.viajes.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.viajes.dto.ReporteUsoPorTiempoDto;
import com.viajes.entity.Viaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    @Query("SELECT 1 FROM Viaje v WHERE v.idMonopatin = :idMonopatin")
    Boolean monopatinTieneViajes(Long idMonopatin);

    @Query("SELECT new com.viajes.dto.ReporteUsoPorTiempoDto(v.idMonopatin, p.pausa) " +
            "FROM Viaje v " +
            "JOIN v.inicioPausasFinal p " +
            "ORDER BY v.idMonopatin, p.pausa")
    List<ReporteUsoPorTiempoDto> reporteUsoPorTiempo();

    @Query("SELECT new com.viajes.dto.ReporteMonopatinesPorViajesYAnio(v.idMonopatin, COUNT(v), :anio) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaHoraFin) = :anio " +
            "GROUP BY v.idMonopatin " +
            "HAVING COUNT(v) > :cantViajes")
    List<ReporteMonopatinesPorViajesYAnio>getReportePorViajeYAnio(Long cantViajes, Long anio);


    @Query("SELECT new com.viajes.dto.ReporteTotalFacturadoEntreMesesDeAnio(SUM(v.valorTotal), :anio, :mesInicio, :mesFin) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaHoraInicio) = :year " +
            "AND MONTH(v.fechaHoraInicio) BETWEEN :mesInicio AND :mesFin")
    ReporteTotalFacturadoEntreMesesDeAnio getReporteTotalFacturadoEntreMesesDeAnio(Long mesInicio, Long mesFin, Long anio);

    @Query("SELECT v FROM Viaje v WHERE v.idCuenta = :idCuenta " +
            "AND v.fechaHoraInicio >= :fechaInicio " +
            "AND v.fechaHoraFin <= :fechaFin " +
            "AND v.fechaHoraFin IS NOT NULL")
    List<Viaje> findViajesPorCuentaYPeriodo(@Param("idCuenta") Long idCuenta,
                                            @Param("fechaInicio") java.time.LocalDateTime fechaInicio,
                                            @Param("fechaFin") java.time.LocalDateTime fechaFin);

    @Query("SELECT v FROM Viaje v WHERE v.idCuenta IN :idsCuentas " +
            "AND v.fechaHoraInicio >= :fechaInicio " +
            "AND v.fechaHoraFin <= :fechaFin " +
            "AND v.fechaHoraFin IS NOT NULL")
    List<Viaje> findViajesPorCuentasYPeriodo(@Param("idsCuentas") List<Long> idsCuentas,
                                             @Param("fechaInicio") java.time.LocalDateTime fechaInicio,
                                             @Param("fechaFin") java.time.LocalDateTime fechaFin);
}
