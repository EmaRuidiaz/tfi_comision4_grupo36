package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    // CRUD Básico
    void crear(T entidad) throws SQLException;
    T leer(long id) throws SQLException;
    List<T> leerTodos() throws SQLException;
    void actualizar(T entidad) throws SQLException;
    void eliminar(long id) throws SQLException; 
    
    // Métodos transaccionales (Tx) que aceptan Connection externa
    void crearTx(T entidad, Connection conn) throws SQLException;
    void eliminarTx(long id, Connection conn) throws SQLException;
}