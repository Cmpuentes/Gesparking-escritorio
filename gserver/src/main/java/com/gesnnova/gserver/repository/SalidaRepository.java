package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.ResumenTurnoDTO;
import com.gesnnova.gserver.dto.SalidaData;
import com.gesnnova.gserver.dto.SalidaResponseDTO;
import com.gesnnova.gserver.model.Salida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SalidaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Nuevo implemento de qwen
    // Obtener máximo numfactura
    public Integer findMaxNumFactura() {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(numfactura), 0) FROM salida", Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public Optional<SalidaData> consultarPorPlaca(String placa) {
        String sql = "SELECT idingreso, fechaingreso, cliente, zona, tipovehiculo, tiposervicio, numeroturno, empleado " +
                "FROM ingreso WHERE placa = ? AND estado = 'Activo' ORDER BY fechaingreso DESC LIMIT 1";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{placa}, (rs, rowNum) -> {
                SalidaData data = new SalidaData();
                data.setIdingreso(rs.getInt("idingreso"));
                data.setFechaingreso(rs.getString("fechaingreso"));
                data.setCliente(rs.getString("cliente"));
                data.setZona(rs.getString("zona"));
                data.setTipovehiculo(rs.getString("tipovehiculo"));
                data.setTiposervicio(rs.getString("tiposervicio"));
                data.setNumeroturno(rs.getInt("numeroturno"));
                data.setEmpleado(rs.getString("empleado"));
                return data;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //Nuevo implemento de qwen
    // Insertar nueva salida
    public void insertarSalida(Salida salida) {
        String sql = """
            INSERT INTO salida (
                idingreso, placa, tipovehiculo, tiposervicio, cliente, fechaentrada, fechasalida,
                zona, numfactura, dias, horas, minutos, valor, numero_recibo, descuento, subtotal,
                efectivo, tarjeta, transferencia, total, turno, turnoentrada, empleadoentrada,
                turnosalida, empleadosalida
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql,
                salida.getIdingreso(), salida.getPlaca(), salida.getTipovehiculo(), salida.getTiposervicio(),
                salida.getCliente(), salida.getFechaentrada(), salida.getFechasalida(), salida.getZona(),
                salida.getNumfactura(), salida.getDias(), salida.getHoras(), salida.getMinutos(),
                salida.getValor(), salida.getNumerorecibo(), salida.getDescuento(), salida.getSubtotal(),
                salida.getEfectivo(), salida.getTarjeta(), salida.getTransferencia(), salida.getTotal(),
                salida.getTurno(), salida.getTurnoentrada(), salida.getEmpleadoentrada(),
                salida.getTurnosalida(), salida.getEmpleadosalida()
        );
    }

    public List<Salida> findByTurnosalidaOrderByFechasalidaAsc(int turnosalida) {
        String sql = """
        SELECT * FROM salida 
        WHERE turnosalida = ? 
        ORDER BY fechasalida ASC
    """;

        return jdbcTemplate.query(sql, new Object[]{turnosalida}, (rs, rowNum) -> {
            Salida salida = new Salida();
            salida.setIdsalida(rs.getInt("idsalida")); // <- agregar aquí
            salida.setIdingreso(rs.getInt("idingreso"));
            salida.setPlaca(rs.getString("placa"));
            salida.setTipovehiculo(rs.getString("tipovehiculo"));
            salida.setTiposervicio(rs.getString("tiposervicio"));
            salida.setFechasalida(rs.getString("fechasalida")); // O Date si usas Date
            salida.setTotal(rs.getInt("total"));
            salida.setTurnosalida(rs.getInt("turnosalida"));
            // Agrega aquí más setters si los necesitas
            return salida;
        });
    }

    public Optional<ResumenTurnoDTO> obtenerResumenPorTurno(int turno) {

        // 1. Totales de SALIDA
        String sqlSalida = """
            SELECT
                COUNT(*) AS vehiculosSalida,
                COALESCE(SUM(efectivo), 0) AS efectivoSalida,
                COALESCE(SUM(tarjeta), 0) AS tarjetaSalida,
                COALESCE(SUM(transferencia), 0) AS transferenciaSalida
            FROM salida
            WHERE turnosalida = ?
        """;

        // 2. Totales de ABONOS
        String sqlAbonos = """
            SELECT
                COALESCE(SUM(efectivo), 0) AS abonoEfectivo,
                COALESCE(SUM(tarjeta), 0) AS abonoTarjeta,
                COALESCE(SUM(transferencia), 0) AS abonoTransferencia
            FROM abonos
            WHERE numero_turno = ?
        """;

        try {
            Map<String, Object> salida = jdbcTemplate.queryForMap(sqlSalida, turno);
            Map<String, Object> abonos = jdbcTemplate.queryForMap(sqlAbonos, turno);

            int vehiculosSalida = ((Number) salida.get("vehiculosSalida")).intValue();

            int efectivoSalida = ((Number) salida.get("efectivoSalida")).intValue();
            int tarjetaSalida = ((Number) salida.get("tarjetaSalida")).intValue();
            int transferenciaSalida = ((Number) salida.get("transferenciaSalida")).intValue();

            int abonoEfectivo = ((Number) abonos.get("abonoEfectivo")).intValue();
            int abonoTarjeta = ((Number) abonos.get("abonoTarjeta")).intValue();
            int abonoTransferencia = ((Number) abonos.get("abonoTransferencia")).intValue();

            int totalAbonos = abonoEfectivo + abonoTarjeta + abonoTransferencia;

            int totalEfectivo = efectivoSalida + abonoEfectivo;
            int totalTarjeta = tarjetaSalida + abonoTarjeta;
            int totalTransferencia = transferenciaSalida + abonoTransferencia;

            int totalRecaudado =
                    totalEfectivo +
                            totalTarjeta +
                            totalTransferencia;

            ResumenTurnoDTO dto = new ResumenTurnoDTO(
                    vehiculosSalida,
                    abonoEfectivo,
                    abonoTarjeta,
                    abonoTransferencia,
                    totalAbonos,
                    totalEfectivo,
                    totalTarjeta,
                    totalTransferencia,
                    totalRecaudado
            );

            return Optional.of(dto);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<SalidaResponseDTO> obtenerDetallePorId(int idSalida) {
        String sql = """
        SELECT 
            s.numfactura,
            s.placa,
            s.fechaentrada,
            s.fechasalida,
            s.tipovehiculo,
            s.tiposervicio,
            s.dias,
            s.horas,
            s.minutos,
            s.valor,
            s.numero_recibo,
            s.descuento,
            s.total,
            s.efectivo,
            s.tarjeta,
            s.transferencia
        FROM salida s
        WHERE s.idsalida = ?
    """;

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{idSalida}, (rs, rowNum) -> {
                SalidaResponseDTO dto = new SalidaResponseDTO();
                dto.setNumfactura(rs.getInt("numfactura"));
                dto.setPlaca(rs.getString("placa"));
                dto.setFechaentrada(rs.getString("fechaentrada"));
                dto.setFechasalida(rs.getString("fechasalida"));
                dto.setTipovehiculo(rs.getString("tipovehiculo"));
                dto.setTiposervicio(rs.getString("tiposervicio"));
                dto.setDias(rs.getInt("dias"));
                dto.setHoras(rs.getInt("horas"));
                dto.setMinutos(rs.getInt("minutos"));
                dto.setValor(rs.getInt("valor"));
                dto.setNumerorecibo(rs.getInt("numero_recibo"));
                dto.setDescuento(rs.getInt("descuento"));
                dto.setTotal(rs.getInt("total"));
                dto.setEfectivo(rs.getInt("efectivo"));
                dto.setTarjeta(rs.getInt("tarjeta"));
                dto.setTransferencia(rs.getInt("transferencia"));
                return dto;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
