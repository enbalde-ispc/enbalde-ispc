-- MySQL Script generated by MySQL Workbench
-- dom 07 may 2023 15:03:39
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema enbalde
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `enbalde` ;
CREATE SCHEMA IF NOT EXISTS `enbalde` DEFAULT CHARACTER SET utf8mb4 ;
USE `enbalde` ;

-- -----------------------------------------------------
-- Table `enbalde`.`Presentaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Presentaciones` (
  `id_presentaciones` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id_presentaciones`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Presentaciones` AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `enbalde`.`TipoArticulos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`TipoArticulos` (
  `id_articulos` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id_articulos`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`TipoArticulos` AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `enbalde`.`Articulos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Articulos` (
  `id_articulos` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(200) NOT NULL,
  `descripcion` VARCHAR(200) NOT NULL,
  `tipo` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `precio` DOUBLE NOT NULL,
  `costo` DOUBLE NOT NULL,
  `uni_medida` INT NOT NULL,
  `presentacion` INT NOT NULL,
  `imagen` VARCHAR(512) NOT NULL,
  `alicuota` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id_articulos`),
  CONSTRAINT `fk_presentacion`
    FOREIGN KEY (`presentacion`)
    REFERENCES `enbalde`.`Presentaciones` (`id_presentaciones`),
  CONSTRAINT `fk_tipo_articulo`
    FOREIGN KEY (`tipo`)
    REFERENCES `enbalde`.`TipoArticulos` (`id_articulos`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Articulos` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`TipoComprobantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`TipoComprobantes` (
  `id_tipo_comprobantes` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id_tipo_comprobantes`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`TipoComprobantes` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`Comprobantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Comprobantes` (
  `id_comprobantes` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `descripcion` VARCHAR(60) NOT NULL,
  `tipo` INT NOT NULL,
  `signo` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_comprobantes`),
  CONSTRAINT `fk_tipo_comprobante`
    FOREIGN KEY (`tipo`)
    REFERENCES `enbalde`.`TipoComprobantes` (`id_tipo_comprobantes`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Comprobantes` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`RegimenIvas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`RegimenIvas` (
  `id_regimen_iva` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id_regimen_iva`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`RegimenIvas` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Usuarios` (
  `id_usuarios` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `tipo_usuario` INT NOT NULL,
  `nombre` VARCHAR(40) NOT NULL,
  `apellido` VARCHAR(40) NOT NULL,
  `direccion` VARCHAR(40) NULL DEFAULT NULL,
  `regIva` INT NOT NULL,
  `observacion` VARCHAR(200) NULL DEFAULT NULL,
  `usuario` VARCHAR(40) NOT NULL,
  `pasword` VARCHAR(40) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_usuarios`),
  CONSTRAINT `fk_regiva`
    FOREIGN KEY (`regIva`)
    REFERENCES `enbalde`.`RegimenIvas` (`id_regimen_iva`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Usuarios` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`Envios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Envios` (
  `id_envios` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nombre` VARCHAR(40) NOT NULL,
  `monto` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id_envios`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Envios` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`MovComprobantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`MovComprobantes` (
  `id_mov_coprob` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `nrocomprobante` CHAR(14) NOT NULL,
  `comprobante` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `usuario` INT NOT NULL,
  `neto` DOUBLE NULL DEFAULT NULL,
  `monto_iva` DOUBLE NULL DEFAULT NULL,
  `alicuota` FLOAT NULL DEFAULT NULL,
  `nogravado` DOUBLE NULL DEFAULT NULL,
  `total` DOUBLE NULL DEFAULT NULL,
  `envio` INT NOT NULL,
  PRIMARY KEY (`id_mov_coprob`),
  CONSTRAINT `fk_comprobante`
    FOREIGN KEY (`comprobante`)
    REFERENCES `enbalde`.`Comprobantes` (`id_comprobantes`),
  CONSTRAINT `fk_entidad`
    FOREIGN KEY (`usuario`)
    REFERENCES `enbalde`.`Usuarios` (`id_usuarios`),
  CONSTRAINT `fk_envio`
    FOREIGN KEY (`envio`)
    REFERENCES `enbalde`.`Envios` (`id_envios`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`MovComprobantes` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`DetMovComprobantes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`DetMovComprobantes` (
  `id_det_mov_compro` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `id_mov` INT NOT NULL,
  `articulo` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `pre_unitario` DOUBLE NULL DEFAULT NULL,
  `alicuo` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id_det_mov_compro`),
  CONSTRAINT `fk_articulo`
    FOREIGN KEY (`articulo`)
    REFERENCES `enbalde`.`Articulos` (`id_articulos`),
  CONSTRAINT `fk_movComprobante`
    FOREIGN KEY (`id_mov`)
    REFERENCES `enbalde`.`MovComprobantes` (`id_mov_coprob`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`DetMovComprobantes` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`Ofertas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`Ofertas` (
  `id_ofertas` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `porcentaje` FLOAT NOT NULL,
  `fecha_vencimiento` DATE NOT NULL,
  PRIMARY KEY (`id_ofertas`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`Ofertas` AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table `enbalde`.`OfertaArticulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enbalde`.`OfertaArticulo` (
  `id_ofertaarticulos` INT NOT NULL UNIQUE AUTO_INCREMENT,
  `id_articulos` INT NOT NULL,
  `id_ofertas` INT NOT NULL,
  PRIMARY KEY (`id_ofertaarticulos`),
  CONSTRAINT `fk_ofertaarticulo_articulo`
    FOREIGN KEY (`id_articulos`)
    REFERENCES `enbalde`.`Articulos` (`id_articulos`),
  CONSTRAINT `fk_ofertaarticulo_oferta`
    FOREIGN KEY (`id_ofertas`)
    REFERENCES `enbalde`.`Ofertas` (`id_ofertas`))
ENGINE = InnoDB;

ALTER TABLE `enbalde`.`OfertaArticulo` AUTO_INCREMENT = 1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;