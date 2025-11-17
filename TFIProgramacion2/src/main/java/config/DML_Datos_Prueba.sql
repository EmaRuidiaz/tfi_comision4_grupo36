/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  ema_r
 * Created: 16 nov 2025
 */

-- SCRIPT DML: 100 INSERTS (CREACIÓN MASIVA DE DATOS DE PRUEBA)

USE tpi_bd;

-- ==================================================
-- 1. LIMPIEZA Y REINICIO SEGURO DE TABLAS
-- Es necesario desactivar las FKs para TRUNCATE
-- ==================================================
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiar la tabla principal (A) y la dependiente (B)
TRUNCATE TABLE ConfiguracionRed; 
TRUNCATE TABLE DispositivoIoT;  

-- Reiniciar el contador AUTO_INCREMENT
ALTER TABLE ConfiguracionRed AUTO_INCREMENT = 1;
ALTER TABLE DispositivoIoT AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- ==================================================
-- 2. GENERAR 100 DISPOSITIVOS (A)
-- ==================================================
INSERT INTO DispositivoIoT (serial, modelo, ubicacion, firmwareVersion, eliminado)
SELECT
    -- Generar SERIAL único: 'DEV-001' hasta 'DEV-100'
    CONCAT('SERIAL-IOT-', LPAD(seq.id, 3, '0')), 
    -- Alternar modelos para variar los datos
    CASE WHEN seq.id % 2 = 0 THEN 'Sensor-V3' ELSE 'Actuador-V2' END,
    CONCAT('Ubicacion Sala ', seq.id),
    '1.0.0',
    -- Eliminar lógicamente 5 de 100 (IDs 96-100) para probar la baja lógica
    CASE WHEN seq.id > 95 THEN 1 ELSE 0 END
FROM 
    -- Técnica para generar una tabla temporal de números del 1 al 100
    (SELECT @i := @i + 1 as id FROM 
    (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 
     UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) as t1,
    (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 
     UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) as t2,
    (SELECT @i := 0) as init) as seq
WHERE seq.id <= 100
ORDER BY seq.id;


-- ==================================================
-- 3. GENERAR 100 CONFIGURACIONES (B)
-- Se inserta una fila en B por cada fila en A, usando el ID de A como FK.
-- ==================================================
INSERT INTO ConfiguracionRed (dispositivo_iot_id, dhcpHabilitado, ip, mascara, gateway, dnsPrimario, eliminado)
SELECT
    -- El ID de la FK coincide con la PK de A (1 a 100)
    id, 
    -- Alternar DHCP (ID par es DHCP)
    id % 2, 
    
    -- IP estática si DHCP es 0 (ej: 192.168.1.X) o NULL si es DHCP
    CASE WHEN id % 2 = 0 THEN CONCAT('192.168.1.', id) ELSE NULL END, 
    CASE WHEN id % 2 = 0 THEN '255.255.255.0' ELSE NULL END,
    CASE WHEN id % 2 = 0 THEN '192.168.1.1' ELSE NULL END,
    CASE WHEN id % 2 = 0 THEN '8.8.8.8' ELSE NULL END,
    
    -- Heredar el estado 'eliminado' para consistencia
    eliminado
FROM 
    DispositivoIoT
ORDER BY id;

-- Verificación de los totales
SELECT 'DispositivoIoT Count:', COUNT(*) FROM DispositivoIoT;
SELECT 'ConfiguracionRed Count:', COUNT(*) FROM ConfiguracionRed;