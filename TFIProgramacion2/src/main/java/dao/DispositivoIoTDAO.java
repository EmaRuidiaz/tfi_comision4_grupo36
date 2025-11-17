package dao;

import entities.DispositivoIoT;
import java.sql.SQLException;
import java.util.List;


public interface DispositivoIoTDAO extends GenericDAO<DispositivoIoT> {
    /**
     * Busca un DispositivoIoT por su número de serie (campo UNIQUE).
     * @param serial El número de serie único del dispositivo.
     * @return El DispositivoIoT encontrado o null si no existe.
     * @throws SQLException Si ocurre un error de acceso a la BD.
     */
    DispositivoIoT findBySerial(String serial) throws SQLException;
    
    /**
     * Obtiene una lista de todos los dispositivos que NO tienen baja lógica (eliminado = false).
     * @return Lista de dispositivos activos.
     * @throws SQLException Si ocurre un error de acceso a la BD.
     */
    List<DispositivoIoT> findAllActivos() throws SQLException;
    
    /**
     * Alterna o establece el estado de baja lógica (eliminado) de un dispositivo por su ID.
     * @param id El ID del dispositivo a modificar.
     * @param eliminado El nuevo estado de eliminación (true para eliminar lógicamente).
     * @throws SQLException Si ocurre un error de acceso a la BD.
     */
    void updateEstadoEliminado(long id, boolean eliminado) throws SQLException;
}