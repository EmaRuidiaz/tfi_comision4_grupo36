/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author ema_r
 */
public class TransactionManager {
    private Connection connection;

    // Inicia la transacción: Obtiene la conexión y deshabilita el autocommit
    public Connection beginTransaction() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.connection.setAutoCommit(false);
        return this.connection;
    }

    // Confirma la transacción
    public void commit() {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                System.err.println("Error al realizar commit: " + e.getMessage());
            } finally {
                close();
            }
        }
    }

    // Deshace la transacción
    public void rollback() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("Error al realizar rollback: " + e.getMessage());
            } finally {
                close();
            }
        }
    }

    // Cierra la conexión y restaura el autocommit
    private void close() {
        if (connection != null) {
            try {
                // Restaurar el autocommit a true al finalizar
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
}
