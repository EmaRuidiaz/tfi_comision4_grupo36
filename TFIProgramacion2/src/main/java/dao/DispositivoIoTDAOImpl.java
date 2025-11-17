package dao;

import config.DatabaseConnection;
import entities.ConfiguracionRed;
import entities.DispositivoIoT;
import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DispositivoIoTDAOImpl implements DispositivoIoTDAO {
    
    // Inyección de dependencia (fuerte, podría ser por interfaz)
    private final ConfiguracionRedDAOImpl configuracionDAO = new ConfiguracionRedDAOImpl();
    
    private DispositivoIoT mapear(ResultSet rs) throws SQLException {
        DispositivoIoT disp = new DispositivoIoT();
        disp.setId(rs.getLong("id"));
        disp.setEliminado(rs.getBoolean("eliminado"));
        disp.setSerial(rs.getString("serial"));
        disp.setModelo(rs.getString("modelo"));
        disp.setUbicacion(rs.getString("ubicacion"));
        disp.setFirmwareVersion(rs.getString("firmwareVersion"));
        return disp;
    }

    @Override
    public void crearTx(DispositivoIoT entidad, Connection conn) throws SQLException {
        String sql = "INSERT INTO DispositivoIoT (eliminado, serial, modelo, ubicacion, firmwareVersion) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBoolean(1, entidad.getEliminado());
            stmt.setString(2, entidad.getSerial());
            stmt.setString(3, entidad.getModelo());
            stmt.setString(4, entidad.getUbicacion());
            stmt.setString(5, entidad.getFirmwareVersion());

            if (stmt.executeUpdate() > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidad.setId(rs.getLong(1)); // Asigna el ID generado (PK de A)
                    }
                }
            } else {
                throw new SQLException("Error: No se pudo crear el Dispositivo IoT.");
            }
        }
    }
    
    // Método para leer un dispositivo por ID (carga la relación 1:1)
    @Override
    public DispositivoIoT leer(long id) throws SQLException {
        String sql = "SELECT * FROM DispositivoIoT WHERE id = ? AND eliminado = 0";
        DispositivoIoT dispositivo = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dispositivo = mapear(rs);
                    // Cargar la relación 1:1 (ConfiguracionRed)
                    ConfiguracionRed conf = configuracionDAO.leerPorDispositivoId(dispositivo.getId(), conn);
                    dispositivo.setConfiguracionRed(conf);
                }
            }
        }
        return dispositivo;
    }
    
    // Método para listar todos (carga la relación 1:1)
    @Override
    public List<DispositivoIoT> leerTodos() throws SQLException {
        String sql = "SELECT * FROM DispositivoIoT WHERE eliminado = 0";
        List<DispositivoIoT> dispositivos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DispositivoIoT disp = mapear(rs);
                // Cargar la relación 1:1
                ConfiguracionRed conf = configuracionDAO.leerPorDispositivoId(disp.getId(), conn);
                disp.setConfiguracionRed(conf);
                dispositivos.add(disp);
            }
        }
        return dispositivos;
    }
    
    @Override
    public DispositivoIoT findBySerial(String serial) throws SQLException {
        String sql = "SELECT * FROM DispositivoIoT WHERE serial = ? AND eliminado = 0";
        DispositivoIoT dispositivo = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, serial);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dispositivo = mapear(rs);
                    // Cargar la relación 1:1
                    ConfiguracionRed conf = configuracionDAO.leerPorDispositivoId(dispositivo.getId(), conn);
                    dispositivo.setConfiguracionRed(conf);
                }
            }
        }
        return dispositivo;
    }

    @Override
    public List<DispositivoIoT> findAllActivos() throws SQLException {
        String sql = "SELECT * FROM DispositivoIoT WHERE eliminado = 0";
        List<DispositivoIoT> dispositivos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DispositivoIoT disp = mapear(rs);
                // Cargar la relación 1:1
                ConfiguracionRed conf = configuracionDAO.leerPorDispositivoId(disp.getId(), conn);
                disp.setConfiguracionRed(conf);
                dispositivos.add(disp);
            }
        }
        return dispositivos;
    }

    @Override
    public void updateEstadoEliminado(long id, boolean eliminado) throws SQLException {
        String sql = "UPDATE DispositivoIoT SET eliminado = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, eliminado);
            stmt.setLong(2, id);
            
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontro el Dispositivo IoT con ID " + id + " para cambiar el estado.");
            }
        }
    }

    // Métodos restantes (simplificados o sin implementar)
    @Override public void crear(DispositivoIoT entidad) throws SQLException { throw new UnsupportedOperationException("Usar crearTx."); }
    @Override public void actualizar(DispositivoIoT entidad) throws SQLException { throw new UnsupportedOperationException("Método pendiente."); }
    @Override public void eliminar(long id) throws SQLException { throw new UnsupportedOperationException("Método pendiente."); }
    @Override public void eliminarTx(long id, Connection conn) throws SQLException { throw new UnsupportedOperationException("Método pendiente."); }
}