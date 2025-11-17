package dao;

import entities.ConfiguracionRed;
import java.sql.Connection;
import java.sql.SQLException;


public interface ConfiguracionRedDAO extends GenericDAO<ConfiguracionRed> {
    ConfiguracionRed leerPorDispositivoId(long dispositivoId, Connection conn) throws SQLException;
}