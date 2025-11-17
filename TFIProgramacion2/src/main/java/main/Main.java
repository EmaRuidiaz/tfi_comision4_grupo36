/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.ConfiguracionRedDAOImpl;
import dao.DispositivoIoTDAOImpl;
import service.DispositivoIoTServiceImpl;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciación de las Dependencias
        DispositivoIoTDAOImpl dispositivoDAO = new DispositivoIoTDAOImpl();
        ConfiguracionRedDAOImpl configuracionDAO = new ConfiguracionRedDAOImpl();
        
        // 2. Inyección de dependencias en la Capa Service
        DispositivoIoTServiceImpl dispositivoService = 
            new DispositivoIoTServiceImpl(dispositivoDAO, configuracionDAO);

        // 3. Creación y ejecución del menú de la aplicación
        AppMenu app = new AppMenu(dispositivoService);
        app.iniciar(); // Método que muestra el menú en consola
    }
}
