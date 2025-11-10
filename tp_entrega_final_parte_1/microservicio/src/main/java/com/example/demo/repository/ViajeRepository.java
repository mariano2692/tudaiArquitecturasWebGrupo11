package com.example.demo.repository;

import com.example.demo.Entity.Viaje;
import com.example.demo.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.demo.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.demo.dto.ReporteUsoPorTiempoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    @Query("SELECT COUNT(v) > 0 FROM Viaje v WHERE v.idMonopatin = :idMonopatin")
    Boolean monopatinTieneViajes(Long idMonopatin);

    @Query("SELECT new com.example.demo.dto.ReporteUsoPorTiempoDto(v.idMonopatin, p.pausa) " +
            "FROM Viaje v " +
            "JOIN v.inicioPausas p " +
            "ORDER BY v.idMonopatin, p.pausa")
    List<ReporteUsoPorTiempoDto> reporteUsoPorTiempo();

    @Query("SELECT new com.example.demo.dto.ReporteMonopatinesPorViajesYAnio(v.idMonopatin, COUNT(v), :anio) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaHoraFin) = :anio " +
            "GROUP BY v.idMonopatin " +
            "HAVING COUNT(v) > :cantViajes")
    List<ReporteMonopatinesPorViajesYAnio> getReportePorViajeYAnio(Long cantViajes, Long anio);

    @Query("SELECT new com.example.demo.dto.ReporteTotalFacturadoEntreMesesDeAnio(SUM(v.valorTotal), :anio, :mesInicio, :mesFin) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fechaHoraInicio) = :anio " +
            "AND MONTH(v.fechaHoraInicio) BETWEEN :mesInicio AND :mesFin")
    ReporteTotalFacturadoEntreMesesDeAnio getReporteTotalFacturadoEntreMesesDeAnio(Long mesInicio, Long mesFin, Long anio);
}
