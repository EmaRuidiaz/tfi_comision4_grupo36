/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import config.TransactionManager;
import dao.ConfiguracionRedDAOImpl;
import dao.DispositivoIoTDAOImpl;
import entities.DispositivoIoT;
import java.sql.Connection;
import java.util.List;

public class DispositivoIoTServiceImpl implements DispositivoIoTService {
    
    private final DispositivoIoTDAOImpl dispDAO;
    private final ConfiguracionRedDAOImpl confDAO;
    
    public DispositivoIoTServiceImpl(DispositivoIoTDAOImpl dispDAO, ConfiguracionRedDAOImpl confDAO) {
        this.dispDAO = dispDAO;
        this.confDAO = confDAO;
    }

    @Override
    public void crearDispositivoConConfiguracion(DispositivoIoT dispositivo) throws Exception {
        // 1. Validaciones (Lógica de Negocio)
        if (dispositivo.getSerial() == null || dispositivo.getSerial().trim().isEmpty()) {
            throw new IllegalArgumentException("El serial del dispositivo es obligatorio.");
        }
        if (dispositivo.getConfiguracionRed() == null) {
            throw new IllegalArgumentException("Se requiere una Configuración de Red.");
        }
        
        TransactionManager tx = new TransactionManager();
        Connection conn = null;

        try {
            conn = tx.beginTransaction(); // setAutoCommit(false)
            
            // 2. Ejecutar Operaciones Transaccionales
            // 2.1 Crear A para obtener su ID (PK)
            dispDAO.crearTx(dispositivo, conn); 
            
            // 2.2 Establecer la FK en la entidad B con el ID recién generado
            dispositivo.getConfiguracionRed().setDispositivoIotId(dispositivo.getId());
            
            // 2.3 Crear B (ConfiguracionRed)
            confDAO.crearTx(dispositivo.getConfiguracionRed(), conn);

            tx.commit(); // Éxito: ambas operaciones se confirman
        } catch (Exception e) {
            tx.rollback(); // Fallo: ambas operaciones se deshacen
            throw e;
        }
    }
    
    // Métodos restantes (delegación simple)
    @Override public void save(DispositivoIoT entidad) throws Exception { throw new UnsupportedOperationException("Usar crearDispositivoConConfiguracion para operaciones completas."); }
    @Override public DispositivoIoT findById(long id) throws Exception { return dispDAO.leer(id); }
    @Override public List<DispositivoIoT> findAll() throws Exception { return dispDAO.leerTodos(); }
    @Override public void update(DispositivoIoT entidad) throws Exception { dispDAO.actualizar(entidad); }
    @Override public void delete(long id) throws Exception { dispDAO.eliminar(id); }
    
    @Override
    public void eliminarLogico(long id) throws Exception {
        // En un escenario real, esto debería ser transaccional si B también tuviera una columna 'eliminado'.
        // Aquí se llama directamente al método del DAO ya que es una operación simple UPDATE.
        dispDAO.updateEstadoEliminado(id, true);
    }

    @Override
    public void eliminarDispositivoTx(long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}