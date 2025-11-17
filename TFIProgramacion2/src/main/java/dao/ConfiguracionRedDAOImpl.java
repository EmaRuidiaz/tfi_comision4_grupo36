package dao;

import entities.ConfiguracionRed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionRedDAOImpl implements ConfiguracionRedDAO {
    
    private ConfiguracionRed mapear(ResultSet rs) throws SQLException {
        ConfiguracionRed conf = new ConfiguracionRed();
        conf.setDispositivoIotId(rs.getLong("id"));
        conf.setEliminado(rs.getBoolean("eliminado"));
        conf.setDhcpHabilitado(rs.getBoolean("dhcpHabilitado"));
        conf.setIp(rs.getString("ip"));
        conf.setMascara(rs.getString("mascara"));
        conf.setGateway(rs.getString("gateway"));
        conf.setDnsPrimario(rs.getString("dnsPrimario"));
        return conf;
    }

    @Override
    public void crearTx(ConfiguracionRed entidad, Connection conn) throws SQLException {
        String sql = "INSERT INTO ConfiguracionRed (eliminado, dhcpHabilitado, ip, mascara, gateway, dnsPrimario, dispositivo_iot_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBoolean(1, entidad.getEliminado());
            stmt.setBoolean(2, entidad.getDhcpHabilitado());
            stmt.setString(3, entidad.getIp());
            stmt.setString(4, entidad.getMascara());
            stmt.setString(5, entidad.getGateway());
            stmt.setString(6, entidad.getDnsPrimario());
            stmt.setLong(7, entidad.getDispositivoIotId()); // ID de A como FK

            if (stmt.executeUpdate() > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidad.setDispositivoIotId(rs.getLong(1)); 
                    }
                }
            } else {
                throw new SQLException("Error: No se pudo crear la Configuracion de Red.");
            }
        }
    }
    
    @Override
    public ConfiguracionRed leerPorDispositivoId(long dispositivoId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM ConfiguracionRed WHERE dispositivo_iot_id = ? AND eliminado = 0";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, dispositivoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    // Implementaciones de métodos restantes (simples o no Tx)
    @Override public void crear(ConfiguracionRed entidad) throws SQLException { throw new UnsupportedOperationException("Usar crearTx."); }
    @Override public ConfiguracionRed leer(long id) throws SQLException { return null; }
    @Override public List<ConfiguracionRed> leerTodos() throws SQLException { return new ArrayList<>(); }
    @Override public void actualizar(ConfiguracionRed entidad) throws SQLException { throw new UnsupportedOperationException("Metodo pendiente."); }
    @Override public void eliminar(long id) throws SQLException { throw new UnsupportedOperationException("Metodo pendiente."); }
    @Override public void eliminarTx(long id, Connection conn) throws SQLException { throw new UnsupportedOperationException("Metodo pendiente."); }
}