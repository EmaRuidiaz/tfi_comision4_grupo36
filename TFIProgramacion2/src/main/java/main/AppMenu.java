/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.ConfiguracionRed;
import entities.DispositivoIoT;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import service.DispositivoIoTServiceImpl;

public class AppMenu {
    private final DispositivoIoTServiceImpl dispositivoService;
    private final Scanner scanner;

    public AppMenu(DispositivoIoTServiceImpl dispositivoService) {
        this.dispositivoService = dispositivoService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenuPrincipal();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        crearNuevoDispositivo();
                        break;
                    case 2:
                        listarTodosLosDispositivos();
                        break;
                    case 3:
                        buscarDispositivoPorId();
                        break;
                    case 4:
                        realizarBajaLogica();
                        break;
                    case 0:
                        System.out.println("Saliendo de la aplicación. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Ingrese un número válido.");
                scanner.nextLine();
                opcion = -1;
            } catch (Exception e) {
                System.err.println("Error de la aplicación: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- Menu CRUD: Dispositivo IoT 1:1 ---");
        System.out.println("1. Crear nuevo Dispositivo (Transaccional)");
        System.out.println("2. Listar todos los Dispositivos (Carga 1:1)");
        System.out.println("3. Buscar Dispositivo por ID (Carga 1:1)");
        System.out.println("4. Realizar Baja Logica (Eliminar)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private void crearNuevoDispositivo() throws Exception {
        System.out.println("\n--- Creacion de Nuevo Dispositivo IoT ---");
        
        // --- 1. Recolección de datos de B (ConfiguracionRed) ---
        ConfiguracionRed conf = new ConfiguracionRed();
        System.out.println("--- Configuracion de Red ---");
        System.out.print("¿DHCP Habilitado (true/false)? ");
        conf.setDhcpHabilitado(scanner.nextBoolean());
        scanner.nextLine();
        
        if (!conf.getDhcpHabilitado()) {
             System.out.print("Ingrese IP: ");
             conf.setIp(scanner.nextLine());
             System.out.print("Ingrese Mascara: ");
             conf.setMascara(scanner.nextLine());
        } else {
             conf.setIp(null); // Asegurar NULL si es DHCP
        }

        // --- 2. Recolección de datos de A (DispositivoIoT) ---
        System.out.println("--- Datos del Dispositivo IoT ---");
        System.out.print("Ingrese Serial (UNIQUE): ");
        String serial = scanner.nextLine();
        System.out.print("Ingrese Modelo: ");
        String modelo = scanner.nextLine();
        
        DispositivoIoT dispositivo = new DispositivoIoT();
        dispositivo.setSerial(serial);
        dispositivo.setModelo(modelo);
        dispositivo.setConfiguracionRed(conf);

        // --- 3. Ejecutar el método transaccional del Service ---
        dispositivoService.crearDispositivoConConfiguracion(dispositivo);
        System.out.println("\n EXITO: Dispositivo IoT y Configuración de Red guardados transaccionalmente.");
    }

    private void listarTodosLosDispositivos() throws Exception {
        System.out.println("\n--- Listado de Dispositivos Activos ---");
        List<DispositivoIoT> lista = dispositivoService.findAll();
        
        if (lista.isEmpty()) {
            System.out.println("No hay dispositivos registrados.");
            return;
        }

        for (DispositivoIoT d : lista) {
            System.out.println(d);
        }
    }
    
    private void buscarDispositivoPorId() throws Exception {
        System.out.print("Ingrese el ID del Dispositivo a buscar: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        DispositivoIoT dispositivo = dispositivoService.findById(id);
        
        if (dispositivo != null) {
            System.out.println("\n Encontrado: " + dispositivo);
        } else {
            System.out.println("\n ERROR: Dispositivo con ID " + id + " no encontrado.");
        }
    }
    
    private void realizarBajaLogica() throws Exception {
        System.out.println("\n--- Baja Logica de Dispositivo IoT ---");
        System.out.print("Ingrese el ID del Dispositivo a ELIMINAR LOGICAMENTE: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        dispositivoService.eliminarLogico(id);
        System.out.println("\n EXITO: Dispositivo con ID " + id + " ha sido marcado como ELIMINADO.");
    }
}
