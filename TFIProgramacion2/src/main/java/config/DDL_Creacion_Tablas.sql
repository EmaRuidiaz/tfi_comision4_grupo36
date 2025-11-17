/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  ema_r
 * Created: 16 nov 2025
 */

-- SCRIPT 1: DEFINICIÓN DE TABLAS (DDL)
-- Modelo: DispositivoIoT (A) -> ConfiguracionRed (B) con FK ÚNICA

-- 1. Creación de la Base de Datos
CREATE DATABASE IF NOT EXISTS tpi_bd;
USE tpi_bd;

-- Desactivar la verificación de claves foráneas para limpieza segura
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar tablas si existen (orden inverso por dependencia FK)
DROP TABLE IF EXISTS ConfiguracionRed;
DROP TABLE IF EXISTS DispositivoIoT;

-- Reestablecer la verificación
SET FOREIGN_KEY_CHECKS = 1;

-- 2. Creación de la tabla principal: DispositivoIoT (A)
CREATE TABLE DispositivoIoT (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- PK
    eliminado BOOLEAN NOT NULL DEFAULT 0, -- Baja lógica [cite: 55]
    serial VARCHAR(50) NOT NULL UNIQUE,  -- Requerimiento NOT NULL, UNIQUE [cite: 195]
    modelo VARCHAR(50) NOT NULL,        -- Requerimiento NOT NULL [cite: 195]
    ubicacion VARCHAR(120) NULL,        -- [cite: 195]
    firmwareVersion VARCHAR(30) NULL,   -- [cite: 195]
    
    CHECK (eliminado IN (0, 1))
);

-- 3. Creación de la tabla dependiente: ConfiguracionRed (B)
CREATE TABLE ConfiguracionRed (
    id INT PRIMARY KEY AUTO_INCREMENT,  -- PK
    eliminado BOOLEAN NOT NULL DEFAULT 0, -- Baja lógica [cite: 56]
    dhcpHabilitado BOOLEAN NOT NULL,    -- [cite: 198]

    ip VARCHAR(45) NULL,
    mascara VARCHAR(45) NULL,
    gateway VARCHAR(45) NULL,
    dnsPrimario VARCHAR(45) NULL,
    
    -- Columna para la Clave Foránea (FK)
    dispositivo_iot_id INT NOT NULL,

    -- **RESTRICCIÓN 1:1:** La FK debe ser ÚNICA para garantizar que solo una fila de B apunte a una fila de A.
    CONSTRAINT uq_dispositivo_fk UNIQUE (dispositivo_iot_id),
    
    -- Clave Foránea a la tabla A
    CONSTRAINT fk_dispositivo_iot_config
        FOREIGN KEY (dispositivo_iot_id)
        REFERENCES DispositivoIoT(id)
        ON DELETE CASCADE, -- Si se elimina A, se elimina B automáticamente
        
    CHECK (eliminado IN (0, 1))
);
