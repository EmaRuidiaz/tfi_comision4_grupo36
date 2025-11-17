/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author ema_r
 */
public class DatabaseConnection {
    private static final Properties PROPERTIES = new Properties();
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;
    
    static {
        // 1. Cargar las propiedades desde el archivo
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                // Esto ocurrirá si el archivo no se encuentra en el classpath
                throw new IOException("FATAL: No se encontro el archivo 'database.properties'.");
            }
            PROPERTIES.load(input);

            // 2. Asignar los valores a las variables estáticas
            DRIVER = PROPERTIES.getProperty("db.driver");
            URL = PROPERTIES.getProperty("db.url");
            USER = PROPERTIES.getProperty("db.user");
            PASSWORD = PROPERTIES.getProperty("db.password");
            
            // 3. Cargar el Driver JDBC
            Class.forName(DRIVER);

        } catch (IOException e) {
            System.err.println("FATAL: Error al leer el archivo de propiedades: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.err.println("FATAL: No se encontró el driver JDBC: " + DRIVER);
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene una nueva conexión a la base de datos usando las propiedades cargadas.
     * @return Objeto Connection activo.
     * @throws SQLException Si falla la conexión.
     */
    public static Connection getConnection() throws SQLException {
        // Usamos las variables estáticas cargadas desde el archivo.
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
