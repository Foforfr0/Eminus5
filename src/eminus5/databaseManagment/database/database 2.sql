-- MySQL Workbench Forward Engineering
DROP DATABASE Eminus5;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Eminus5
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Eminus5
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Eminus5` DEFAULT CHARACTER SET utf8 ;
USE `Eminus5` ;

-- -----------------------------------------------------
-- Table `Eminus5`.`Periodo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Periodo` (
  `IdPeriodo` INT NOT NULL AUTO_INCREMENT,
  `Inicio` DATE NOT NULL,
  `Fin` DATE NOT NULL,
  PRIMARY KEY (`IdPeriodo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`ExperienciaEducativa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`ExperienciaEducativa` (
  `IdExperienciaEducativa` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `idPeriodo` INT NOT NULL,
  PRIMARY KEY (`IdExperienciaEducativa`),
  INDEX `IDExperienciaEducativa_Periodo_idx` (`idPeriodo` ASC) VISIBLE,
  CONSTRAINT `IDExperienciaEducativa_Periodo`
    FOREIGN KEY (`idPeriodo`)
    REFERENCES `Eminus5`.`Periodo` (`IdPeriodo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Proyecto` (
  `IdProyecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `NumIntegrantes` INT NULL,
  `Descripcion` TEXT NOT NULL,
  `IdExperienciaEducativa` INT NOT NULL,
  PRIMARY KEY (`IdProyecto`),
  INDEX `IDExperienciaEducativa_Proyectos_idx` (`IdExperienciaEducativa` ASC) VISIBLE,
  CONSTRAINT `IDExperienciaEducativa_Proyectos`
    FOREIGN KEY (`IdExperienciaEducativa`)
    REFERENCES `Eminus5`.`ExperienciaEducativa` (`IdExperienciaEducativa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`RolSistema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`RolSistema` (
  `IdRolSistema` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdRolSistema`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Usuario` (
  `IDUsuario` INT NOT NULL AUTO_INCREMENT,
  `Usuario` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `ApellidoPaterno` VARCHAR(45) NOT NULL,
  `ApellidoMaterno` VARCHAR(45) NOT NULL,
  `CorreoPersonal` VARCHAR(45) NULL,
  `CorreoInstitucional` VARCHAR(45) NULL,
  `Semestre` INT NULL,
  `IdRolSistema` INT NOT NULL,
  `IdProyecto` INT NULL,
  `IdExperienciaEducativa` INT NOT NULL,
  UNIQUE INDEX `Matricula_UNIQUE` (`Usuario` ASC) VISIBLE,
  UNIQUE INDEX `CorreoPersonal_UNIQUE` (`CorreoPersonal` ASC) VISIBLE,
  UNIQUE INDEX `CorreoInstitucional_UNIQUE` (`CorreoInstitucional` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IdProyecto` ASC) VISIBLE,
  PRIMARY KEY (`IDUsuario`),
  INDEX `IDRolSistema_Usuario_idx` (`IdRolSistema` ASC) VISIBLE,
  INDEX `IDExperienciaEducativa_Usuarios_idx` (`IdExperienciaEducativa` ASC) VISIBLE,
  CONSTRAINT `IDProyecto_Usuarios`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDRolSistema_Usuario`
    FOREIGN KEY (`IdRolSistema`)
    REFERENCES `Eminus5`.`RolSistema` (`IdRolSistema`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDExperienciaEducativa_Usuarios`
    FOREIGN KEY (`IdExperienciaEducativa`)
    REFERENCES `Eminus5`.`ExperienciaEducativa` (`IdExperienciaEducativa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`TipoActividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`TipoActividad` (
  `IdTipoActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdTipoActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Estado` (
  `IdEstado` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdEstado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Actividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Actividad` (
  `IdActividad` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` TEXT NOT NULL,
  `IdEstado` INT NULL,
  `IdTipo` INT NOT NULL,
  `FechaInicio` DATE NULL,
  `FechaTermino` DATE NULL,
  `IdProyecto` INT NOT NULL,
  `IdDesarrollador` INT NULL,
  PRIMARY KEY (`IdActividad`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC) VISIBLE,
  INDEX `IDProyecto_idx` (`IdProyecto` ASC) VISIBLE,
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  INDEX `IDTipoActividad_idx` (`IdTipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`IdEstado` ASC) VISIBLE,
  CONSTRAINT `IDProyecto_Actividades`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDDesarrollador_Actividades`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDTipoActividad_Actividad`
    FOREIGN KEY (`IdTipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IdTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Actividad`
    FOREIGN KEY (`IdEstado`)
    REFERENCES `Eminus5`.`Estado` (`IdEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`BitacoraActividad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`BitacoraActividad` (
  `IdBitacora` INT NOT NULL AUTO_INCREMENT,
  `NumBitacora` INT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripción` TEXT NOT NULL,
  `IdDesarrollador` INT NOT NULL,
  `IdActividad` INT NOT NULL,
  PRIMARY KEY (`IdBitacora`),
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  INDEX `IDActividad_Bitacoras_idx` (`IdActividad` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Bitacoras`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDActividad_Bitacoras`
    FOREIGN KEY (`IdActividad`)
    REFERENCES `Eminus5`.`Actividad` (`IdActividad`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Defecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Defecto` (
  `IdDefecto` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `IdEstado` INT NULL,
  `Esfuerzo` INT NULL,
  `FechaEncontrado` DATE NULL,
  `FechaSolucionado` DATE NULL,
  `IdTipo` INT NULL,
  `IdProyecto` INT NOT NULL,
  PRIMARY KEY (`IdDefecto`),
  INDEX `IDTIpo_idx` (`IdTipo` ASC) VISIBLE,
  INDEX `IDEstado_idx` (`IdEstado` ASC) VISIBLE,
  INDEX `IDProyecto_Defectos_idx` (`IdProyecto` ASC) VISIBLE,
  CONSTRAINT `IDTIpo_Defecto`
    FOREIGN KEY (`IdTipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IdTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDEstado_Defecto`
    FOREIGN KEY (`IdEstado`)
    REFERENCES `Eminus5`.`Estado` (`IdEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDProyecto_Defectos`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`SolicitudCambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`SolicitudCambio` (
  `IdSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(45) NULL,
  `Razon` VARCHAR(45) NULL,
  `Impacto` VARCHAR(45) NULL,
  `AccionPropuesta` VARCHAR(45) NULL,
  `FechaCreacion` DATE NULL,
  `FechaAceptada` DATE NULL,
  `estadoAceptacion` VARCHAR(2) NULL,
  `IdDefecto` INT NOT NULL,
  PRIMARY KEY (`IdSolicitud`),
  UNIQUE INDEX `IDDefecto_UNIQUE` (`IdDefecto` ASC) VISIBLE,
  CONSTRAINT `IDDefecto_Solicitud`
    FOREIGN KEY (`IdDefecto`)
    REFERENCES `Eminus5`.`Defecto` (`IdDefecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Cambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Cambio` (
  `IdCambio` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NOT NULL,
  `Esfuerzo` INT NULL,
  `FechaInicio` DATE NULL,
  `FechaFin` DATE NULL,
  `IdDesarrollador` INT NULL,
  `IdSolicitud` INT NULL,
  `IdEstado` INT NULL,
  `IdTipo` INT NULL,
  PRIMARY KEY (`IdCambio`),
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  UNIQUE INDEX `IDSolicitud_UNIQUE` (`IdSolicitud` ASC) VISIBLE,
  INDEX `IDEstado_Cambio_idx` (`IdEstado` ASC) VISIBLE,
  INDEX `IDTipoActividad_Cambio_idx` (`IdTipo` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Cambio`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDSolicitud_Cambio`
    FOREIGN KEY (`IdSolicitud`)
    REFERENCES `Eminus5`.`SolicitudCambio` (`IdSolicitud`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDEstado_Cambio`
    FOREIGN KEY (`IdEstado`)
    REFERENCES `Eminus5`.`Estado` (`IdEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IDTipoActividad_Cambio`
    FOREIGN KEY (`IdTipo`)
    REFERENCES `Eminus5`.`TipoActividad` (`IdTipoActividad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`BitacoraCambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`BitacoraCambio` (
  `IdBitacora` INT NOT NULL AUTO_INCREMENT,
  `NumBitacora` INT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripción` TEXT NOT NULL,
  `IdDesarrollador` INT NOT NULL,
  `IdCambio` INT NOT NULL,
  PRIMARY KEY (`IdBitacora`),
  INDEX `IDDesarrollador_idx` (`IdDesarrollador` ASC) VISIBLE,
  CONSTRAINT `IDDesarrollador_Bitacoras0`
    FOREIGN KEY (`IdDesarrollador`)
    REFERENCES `Eminus5`.`Usuario` (`IDUsuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDCambio_Bitacoras`
    FOREIGN KEY (`IdCambio`)
    REFERENCES `Eminus5`.`Cambio` (`IdCambio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


/*ROLES SISTEMA--------------------------------------------------------------------------------------------------------------------*/
INSERT INTO RolSistema (Nombre) VALUES ('Responsable');
INSERT INTO RolSistema (Nombre) VALUES ('Desarrollador');
/*ESTADO ACTIVIDAD--------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Estado (Nombre) VALUES ('Iniciado');
INSERT INTO Estado (Nombre) VALUES ('Entregado');
/*TIPO ACTIVIDAD----------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO TipoActividad (Nombre) VALUES ('Frontend');
INSERT INTO TipoActividad (Nombre) VALUES ('Backend');
INSERT INTO TipoActividad (Nombre) VALUES ('Base de datos');
INSERT INTO TipoActividad (Nombre) VALUES ('Controlador');
INSERT INTO TipoActividad (Nombre) VALUES ('JavaScript');
/*PERIODO------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Periodo (Inicio, Fin) VALUES ('2023-01-01', '2023-06-08');
INSERT INTO Periodo (Inicio, Fin) VALUES ('2023-07-01', '2024-01-01');
/*EXPERIENCIA EDUCATIVA------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO ExperienciaEducativa (Nombre, Descripcion, idPeriodo) 
    VALUES ('Proyecto guiado', 'Descripción de ejemplo de proyecto guiado', 2);
/*PROYECTO------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Proyecto (Nombre, NumIntegrantes, Descripcion, IdExperienciaEducativa) 
    VALUES ('SPGER', 10, 'Descripción de ejemplo del proyecto SPGER', 1);
/*USUARIO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema, IDproyecto, IdExperienciaEducativa) 
    VALUES('P21013908', '1234', 'Abraham David', 'Fernández', 'Rodríguez', 
    'abrakadabra007@gmail.com', 'ps21013908@profesor.uv.mx', NULL, 1, 1, 1);
INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema, IDproyecto, IdExperienciaEducativa) 
    VALUES('S21013909', '1234', 'Rodolfo', 'Fernández', 'Rodríguez', 
    'foforfr007@gmail.com', 'zs21013909@estudiantes.uv.mx', 6, 2, 1, 1);
INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema, IDproyecto, IdExperienciaEducativa) 
    VALUES('S21013910', '1234', 'Andrés', 'Arellano', 'García', 
    'andargar@gmail.com', 'zs21013910@estudiantes.uv.mx', 6, 2, 1, 1);
INSERT INTO Usuario (Usuario, Password, Nombre, ApellidoPaterno, ApellidoMaterno, CorreoPersonal, CorreoInstitucional, Semestre, IdRolSistema, IDProyecto, IdExperienciaEducativa)
    VALUES('S21013885', 'saijiki', 'Papu', 'Vazquez', 'Quinto', 'lol2021@gmail.com', 'zs21013885@estudiantes.uv.mx', 6, 2, 1, 1);
/*ACTIVIDAD-------------------------------------------------------------------------------------------------------------------------*/
USE Eminus5;
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación de base de datos', 'Descripción de ejemplo para la actividad Creación de base de datos', 
    1, 3, '2023-06-01', '2023-06-04', 1, 2);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación de vistas', 'Descripción de ejemplo para la actividad Creación de vistas', 
    1, 1, '2023-06-01', '2023-06-04', 1, 3);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación de controladores', 'Descripción de ejemplo para la actividad Creación de controladores', 
    NULL, 4, '2023-06-01', '2023-06-04', 1, NULL);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación de bitacora', 'Descripción de ejemplo para la actividad Creación de bitacora, relacionada con una actividad', 
    1, 4, '2023-06-01', '2023-06-04', 1, 4);

INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación query', 'Query para getActividadesDesarrollador', 
    1, 2, '2023-12-24', '2023-12-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Comprobacion de query', 'Query para getActividadesDesarrollador sin terminar', 
    2, 2, '2023-12-24', '2023-12-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creacion de', 'Query para modificar actividades', 
    1, 2, '2023-12-24', '2023-12-26', 1, 4);

INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Creación alert', 'alert para confirmar modificacion', 
    1, 2, '2023-12-24', '2023-12-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Comprobacion de alert', 'comprobar alerts para confirmar', 
    2, 2, '2023-12-24', '2023-12-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('XD', 'lol nomas por si no me sale', 
    1, 2, '2023-12-24', '2023-12-26', 1, 4);
/*BITACORA-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO BitacoraActividad (NumBitacora, Nombre, Descripción, IdActividad, IdDesarrollador)
    VALUES (1, 'Bitacora de ejemplo', 'Prueba para hacer mi CU13, creo jajaj no recuerdo cual era. Ni pex', 4, 4);
/*CAMBIO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, IdDesarrollador, IdEstado, IdTipo)
    VALUES ('Cambio de ejemplo', 'Ejemplo para ver si mi query funciona', '2023-12-21', 4, 1, 2);
INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, IdDesarrollador, IdEstado, IdTipo)
    VALUES ('Cambio de ejemplo 2', 'Ejemplo para modificar', '2023-12-21', 4,  2, 2);
/*DEFECTO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Defecto (Nombre, Descripcion, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Primer defecto', 'Query regresa null', '2023-12-26', 1, 1, 3);
INSERT INTO Defecto (Nombre, Descripcion, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Defecto de ejemplo', 'Para comprobar query', '2023-12-26', 1, 1, 3);
INSERT INTO Defecto (Nombre, Descripcion, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Fallo al no haber defectos', 'Programa falla cuando no halla defectos', '2023-12-28', 1, 1, 1);
INSERT INTO Defecto (Nombre, Descripcion, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Comprobacion de arreglo', 'Arreglo de controlador', '2023-12-28', 1, 1, 3);
/*-------------------------------------------------------------------------------------------------------------------------*/

SELECT BA.Nombre, BA.Descripción FROM bitacoraactividad BA
JOIN Usuario U ON BA.IdDesarrollador = U.IDUsuario 
WHERE U.IDUsuario = 4
UNION SELECT BC.Nombre, BC.Descripción FROM bitacoracambio BC
JOIN  Usuario U ON BC.IdDesarrollador = U.IDUsuario
WHERE U.IDUsuario = 4;

SELECT C.IdCambio, C.Nombre, C.Descripcion, C.Esfuerzo, C.FechaInicio, C.FechaFin, E.Nombre AS 'Estado', TA.Nombre AS 'TipoActividad'
FROM Cambio C
JOIN Estado E ON E.IdEstado = C.IdEstado
JOIN TipoActividad TA ON TA.IdTipoActividad = C.IdTipo
JOIN Usuario U ON C.IdDesarrollador = U.IDUsuario
WHERE U.IDUsuario = 4 AND C.IdEstado = 1;

SELECT A.IdActividad, A.Nombre, A.Descripcion, E.Nombre AS 'Estado'
FROM Actividad A
JOIN Estado E ON E.IdEstado = A.IdEstado
LEFT JOIN Usuario U ON A.IdDesarrollador = U.IDUsuario
WHERE IDUsuario = 4 AND A.IdEstado = 1;

SELECT DE.IdDefecto, DE.Nombre, DE.Descripcion, E.Nombre AS 'Estado', DE.Esfuerzo, DE.FechaEncontrado, DE.FechaSolucionado, TA.Nombre AS 'Tipo'
FROM Defecto DE 
JOIN Estado E ON E.IdEstado = DE.IdEstado
JOIN TipoActividad TA ON TA.IdTipoActividad = DE.IdTipo
JOIN Proyecto P ON DE.IdProyecto = P.IdProyecto
WHERE P.IdProyecto = 1 AND DE.IdEstado = 1;

UPDATE Defecto DE
SET DE.Esfuerzo = 22, DE.IdEstado = 2, DE.FechaSolucionado = '2023-12-27'
WHERE DE.IdDefecto = 1;