# ⚙️ TFI Programación II: Persistencia JDBC con Transacciones
## Comision 4 Grupo 36
## Relación 1:1 Unidireccional: Dispositivo IoT ↔︎ Configuración de Red

Este proyecto es el Trabajo Final Integrador (TFI) de la materia Programación II de la Tecnicatura Universitaria en Programación (UTN - A Distancia). Implementa una solución de persistencia con Java y MySQL, utilizando los patrones **DAO** y **Service**, con énfasis en el manejo transaccional.

---

## 👥 Integrantes del Equipo

| Nombre | Mail |
| :--- | :--- |
| Mauro Gonzalo Santini | [mgs.argentum@gmail.com] |
| Cristian Serna | [sernachristian700@gmail.com] |
| Juan Martín Roques Zeballos | [juanmartinroqueszeballos@gmail.com] |
| Emanuel Facundo Ruidiaz | [emafruidiaz@gmail.com] |

---

## 🎯 Dominio Elegido y Arquitectura

### Relación 1:1: DispositivoIoT → ConfiguracionRed
Hemos elegido modelar la relación donde un **`DispositivoIoT` (A)** tiene asociada **una y solo una `ConfiguracionRed` (B)**. La referencia es **unidireccional**: solo `DispositivoIoT` contiene una referencia al objeto `ConfiguracionRed`.

### Persistencia y Baja Lógica
* **Modelo BD:** Se implementó la relación 1:1 utilizando una **Clave Foránea Única** (`dispositivo_iot_id INT NOT NULL UNIQUE`) en la tabla `ConfiguracionRed`.
* **Baja Lógica:** Ambas entidades (`A` y `B`) incluyen el campo `eliminado` (`Boolean`) para realizar eliminaciones lógicas en lugar de borrado físico.

---

## 🛠️ Requisitos Técnicos y Pasos para la Ejecución

### 1. Requisitos Previos 
* **Lenguaje:** Java (Se recomienda Java 21 o superior).
* **Base de Datos:** MySQL (Servidor corriendo localmente).
* **Dependencias:** El conector JDBC de MySQL (`mysql-connector-j-x.x.x.jar`).

### 2. Configuración de la Base de Datos

Para inicializar la base de datos y sus tablas, siga estos pasos:

1.  Asegúrese de que su servidor MySQL esté en ejecución.
2.  Ejecute el script **`database/DDL_Creacion_Tablas.sql`**. Este archivo contiene las sentencias `CREATE DATABASE` y `CREATE TABLE` con todas las restricciones de unicidad y claves foráneas necesarias para la relación 1:1.
3.  Ejecute el script **`database/DML_Datos_Prueba.sql`**. Este archivo contiene sentencias `INSERT` para poblar las tablas con datos iniciales.

### 3. Compilación y Ejecución

1.  Compile el proyecto en su IDE (Eclipse, NetBeans, IntelliJ).
2.  Asegúrese de que las credenciales de conexión en **`config/DatabaseConnection.java`** coincidan con su configuración local de MySQL.
3.  Ejecute la clase principal: **`main.Main.java`**.
4.  Utilice el menú de consola (`main.AppMenu`) para realizar las operaciones CRUD.

---

## 🚀 Puntos Clave del Código y la Arquitectura

### 1. Gestión Transaccional (Capa Service) 
La capa **Service** (específicamente `DispositivoIoTServiceImpl`) es responsable de la orquestación transaccional.

* **Inicio:** Se llama a `conn.setAutoCommit(false)` (gestionado por `TransactionManager`).
* **Operación Compuesta:** La creación de un DispositivoIoT implica la creación de ConfiguracionRed y el DispositivoIoT en una secuencia lógica.
* **Confirmación/Reversión:** Se utiliza `conn.commit()` para guardar los cambios y `conn.rollback()` en el bloque `catch` para asegurar la **Atomicidad** ante cualquier fallo.

### 2. Patrón DAO 
* **Interfaces Genéricas:** Se usan interfaces genéricas (`GenericDAO`) para la reutilización del código.
* **Conexión Externa:** Todos los métodos transaccionales en los DAOs (`crearTx`, etc.) aceptan un objeto `Connection` como parámetro para participar en la transacción del Service.
* **Ejecución:** Se utiliza **`PreparedStatement`** en todas las operaciones para seguridad y rendimiento.

### 3. Entregables Adicionales

* **Diagrama UML de Clases:** Ubicado en el repositorio como `UML_Clases.png`.
* **Informe PDF:** Ubicado en el repositorio como `Informe_TFI.pdf`.

---

## 🎥 Enlace al Video de Presentación

[Insertar Enlace de YouTube o Drive aquí] 