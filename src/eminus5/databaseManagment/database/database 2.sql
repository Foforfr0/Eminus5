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
  `Descripcion` TEXT NOT NULL,
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
  `Descripcion` TEXT NULL,
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
-- Table `Eminus5`.`EstadoSolicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`EstadoSolicitud` (
  `IdSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`IdSolicitud`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`SolicitudCambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`SolicitudCambio` (
  `IdSolicitud` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` TEXT NULL,
  `Razon` TEXT NULL,
  `Impacto` TEXT NULL,
  `AccionPropuesta` TEXT NULL,
  `FechaCreacion` DATE NULL,
  `FechaAceptada` DATE NULL,
  `IdEstadoAceptacion` INT NULL,
  `IdDefecto` INT NULL,
  `IdProyecto` INT NULL,
  PRIMARY KEY (`IdSolicitud`),
  INDEX `IDProyecto_Solicitud_idx` (`IdProyecto` ASC) VISIBLE,
  INDEX `IDSolicitud_Estado_idx` (`IdEstadoAceptacion` ASC) VISIBLE,
  CONSTRAINT `IDDefecto_Solicitud`
    FOREIGN KEY (`IdDefecto`)
    REFERENCES `Eminus5`.`Defecto` (`IdDefecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDProyecto_Solicitud`
    FOREIGN KEY (`IdProyecto`)
    REFERENCES `Eminus5`.`Proyecto` (`IdProyecto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `IDSolicitud_Estado`
    FOREIGN KEY (`IdEstadoAceptacion`)
    REFERENCES `Eminus5`.`EstadoSolicitud` (`IdSolicitud`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Eminus5`.`Cambio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Eminus5`.`Cambio` (
  `IdCambio` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` TEXT NOT NULL,
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
INSERT INTO EstadoSolicitud (Nombre) VALUES ('Creado');
INSERT INTO EstadoSolicitud (Nombre) VALUES ('Aceptado');
INSERT INTO EstadoSolicitud (Nombre) VALUES ('Rechazado');
/*ESTADO SOLICITUD CAMBIO--------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Estado (Nombre) VALUES ('Iniciado');
INSERT INTO Estado (Nombre) VALUES ('Entregado');
/*TIPO ACTIVIDAD----------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO TipoActividad (Nombre) VALUES ('Backend');
INSERT INTO TipoActividad (Nombre) VALUES ('Base de datos');
INSERT INTO TipoActividad (Nombre) VALUES ('Controlador');
INSERT INTO TipoActividad (Nombre) VALUES ('Frontend');
INSERT INTO TipoActividad (Nombre) VALUES ('JavaScript');
/*PERIODO------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Periodo (Inicio, Fin) VALUES ('2024-01-01', '2024-06-01');
INSERT INTO Periodo (Inicio, Fin) VALUES ('2024-07-01', '2024-12-31');
/*EXPERIENCIA EDUCATIVA------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO ExperienciaEducativa (Nombre, Descripcion, idPeriodo) 
    VALUES ('Proyecto guiado', 'Descripción de ejemplo de proyecto guiado', 1);
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
    ('Ejemplo Actividad 1', 'Descripción de ejemplo para la actividad 1', 
    1, 3, '2024-01-15', '2024-01-25', 1, 2);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 2', 'Descripción de ejemplo para la actividad 2', 
    1, 1, '2024-01-14', '2024-01-14', 1, 3);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 3', 'Descripción de ejemplo para la actividad 3', 
    1, 4, '2024-02-01', '2024-02-03', 1, NULL);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 4', 'Descripción de ejemplo para la actividad 4', 
    1, 4, '2024-02-02', '2024-02-07', 1, 4);

INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 5', 'Descripción de ejemplo para la actividad 5', 
    1, 2, '2024-03-01', '2024-03-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 6', 'Descripción de ejemplo para la actividad 6', 
    2, 2, '2024-03-24', '2024-03-25', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 7', 'Descripción de ejemplo para la actividad 7', 
    1, 2, '2024-03-24', '2024-03-26', 1, 4);

INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 8', 'Descripción de ejemplo para la actividad 8', 
    1, 2, '2024-04-02', '2024-12-09', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 9', 'Descripción de ejemplo para la actividad 9', 
    2, 2, '2024-04-10', '2024-12-15', 1, 4);
INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES 
    ('Ejemplo Actividad 10', 'Descripción de ejemplo para la actividad 10', 
    1, 2, '2024-04-28', '2024-12-30', 1, 4);
/*BITACORA-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO BitacoraActividad (NumBitacora, Nombre, Descripción, IdActividad, IdDesarrollador)
    VALUES (1, 'Ejemplo bitácora 1', 'Descripción de ejemplo de bitácora 1', 4, 4);
/*CAMBIO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, IdDesarrollador, IdEstado, IdTipo)
    VALUES ('Ejemplo de cambio 1', 'Descripción de ejemplo de cambio 1', '2024-02-21', 4, 1, 2);
INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, IdDesarrollador, IdEstado, IdTipo)
    VALUES ('Ejemplo de cambio 2', 'Descripción de ejemplo de cambio 2', '2024-02-23', 4,  2, 2);
/*DEFECTO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO Defecto (Nombre, Descripcion, Esfuerzo, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Ejemplo defecto 1', 'Descripción de ejemplo defecto 1', 50, '2024-02-16', 1, 1, 3);
INSERT INTO Defecto (Nombre, Descripcion, Esfuerzo, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Ejemplo defecto 2', 'Descripción de ejemplo defecto 2', 45, '2024-03-06', 1, 1, 3);
INSERT INTO Defecto (Nombre, Descripcion, Esfuerzo, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Ejemplo defecto 3', 'Descripción de ejemplo defecto 3', 60, '2024-03-10', 1, 1, 1);
INSERT INTO Defecto (Nombre, Descripcion, Esfuerzo, FechaEncontrado, IdProyecto, IdEstado, IdTipo)
    VALUES ('Ejemplo defecto 4', 'Descripción de ejemplo defecto 3', 120, '2024-03-19', 1, 1, 3);
/*SOLICITUD DE CAMBIO-------------------------------------------------------------------------------------------------------------------------*/
INSERT INTO SolicitudCambio (Nombre, Descripcion, Razon, Impacto, AccionPropuesta, 
    FechaCreacion, FechaAceptada, IdEstadoAceptacion, IdDefecto, IdProyecto) 
    VALUES ('Ejemplo de solicitud de cambio 1', 'Descripción de ejemplo de solicitud de cambio 1',
    'Razón: Razón de ejemplo de solicitud de cambio 1', 'Impacto: Impacto de ejemplo de solicitud de cambio 1', 
    'Acción propuesta: Acción propuesta de ejemplo de solicitud de cambio 1', '2024-02-18', NULL, 1, 1, NULL);
INSERT INTO SolicitudCambio (Nombre, Descripcion, Razon, Impacto, AccionPropuesta, 
    FechaCreacion, FechaAceptada, IdEstadoAceptacion, IdDefecto, IdProyecto) 
    VALUES ('Ejemplo de solicitud de cambio 2', 'Descripción de ejemplo de solicitud de cambio 2',
    'Razón: Razón de ejemplo de solicitud de cambio 2', 'Impacto: Impacto de ejemplo de solicitud de cambio 2', 
    'Acción propuesta: Acción propuesta de ejemplo de solicitud de cambio 3', '2024-02-25', '2024-02-29', 2, NULL, 1);
INSERT INTO SolicitudCambio (Nombre, Descripcion, Razon, Impacto, AccionPropuesta, 
    FechaCreacion, FechaAceptada, IdEstadoAceptacion, IdDefecto, IdProyecto) 
    VALUES ('Ejemplo de solicitud de cambio 3', 'Descripción de ejemplo de solicitud de cambio 3',
    'Razón: Razón de ejemplo de solicitud de cambio 3', 'Impacto: Impacto de ejemplo de solicitud de cambio 3', 
    'Acción propuesta: Acción propuesta de ejemplo de solicitud de cambio 3', '2024-03-10', '2024-03-12', 3, 2, NULL);