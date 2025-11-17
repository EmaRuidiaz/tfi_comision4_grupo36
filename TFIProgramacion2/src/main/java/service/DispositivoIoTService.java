/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.DispositivoIoT;

public interface DispositivoIoTService extends GenericService<DispositivoIoT> {
    // Operación clave que requiere transacción
    void crearDispositivoConConfiguracion(DispositivoIoT dispositivo) throws Exception;
    // Operación clave para eliminar (elimina A y B)
    void eliminarDispositivoTx(long id) throws Exception;
    void eliminarLogico(long id) throws Exception;
}