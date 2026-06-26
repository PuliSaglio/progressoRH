-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema rh_sistema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rh_sistema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rh_sistema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `rh_sistema` ;

-- -----------------------------------------------------
-- Table `rh_sistema`.`setores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`setores` (
  `id_setor` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_setor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`cargos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`cargos` (
  `id_cargo` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `limite_horas_extras` INT NOT NULL,
  `setor_id` INT NOT NULL,
  PRIMARY KEY (`id_cargo`),
  INDEX `fk_cargos_setores` (`setor_id` ASC) VISIBLE,
  CONSTRAINT `fk_cargos_setores`
    FOREIGN KEY (`setor_id`)
    REFERENCES `rh_sistema`.`setores` (`id_setor`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`funcionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`funcionarios` (
  `cpf` VARCHAR(11) NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `sobrenome` VARCHAR(100) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `logradouro` VARCHAR(100) NOT NULL,
  `numero` VARCHAR(20) NOT NULL,
  `cep` VARCHAR(10) NOT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`cpf`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`contratos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`contratos` (
  `id_contrato` INT NOT NULL AUTO_INCREMENT,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NULL DEFAULT NULL,
  `salario_contratual` DECIMAL(10,2) NOT NULL,
  `carga_horaria_semanal` INT NOT NULL,
  `funcionario_cpf` VARCHAR(11) NOT NULL,
  `cargo_id` INT NOT NULL,
  PRIMARY KEY (`id_contrato`),
  INDEX `fk_contratos_funcionarios` (`funcionario_cpf` ASC) VISIBLE,
  INDEX `fk_contratos_cargos` (`cargo_id` ASC) VISIBLE,
  CONSTRAINT `fk_contratos_cargos`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `rh_sistema`.`cargos` (`id_cargo`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_contratos_funcionarios`
    FOREIGN KEY (`funcionario_cpf`)
    REFERENCES `rh_sistema`.`funcionarios` (`cpf`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`banco_horas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`banco_horas` (
  `id_banco_horas` INT NOT NULL AUTO_INCREMENT,
  `data_registro` DATE NOT NULL,
  `saldo_anterior` DECIMAL(8,2) NOT NULL,
  `saldo_atual` DECIMAL(8,2) NOT NULL,
  `horas_extras_mes` DECIMAL(8,2) NULL DEFAULT NULL,
  `contrato_id` INT NOT NULL,
  PRIMARY KEY (`id_banco_horas`),
  INDEX `fk_banco_contratos` (`contrato_id` ASC) VISIBLE,
  CONSTRAINT `fk_banco_contratos`
    FOREIGN KEY (`contrato_id`)
    REFERENCES `rh_sistema`.`contratos` (`id_contrato`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`beneficios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`beneficios` (
  `id_beneficio` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `tipo` VARCHAR(50) NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_beneficio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`skills` (
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`cargos_skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`cargos_skills` (
  `cargo_id` INT NOT NULL,
  `skill_nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`cargo_id`, `skill_nome`),
  INDEX `fk_cs_skills` (`skill_nome` ASC) VISIBLE,
  CONSTRAINT `fk_cs_cargos`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `rh_sistema`.`cargos` (`id_cargo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cs_skills`
    FOREIGN KEY (`skill_nome`)
    REFERENCES `rh_sistema`.`skills` (`nome`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`descontos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`descontos` (
  `id_desconto` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `tipo` VARCHAR(50) NOT NULL,
  `valor` DECIMAL(10,2) NOT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_desconto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`folhas_pagamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`folhas_pagamento` (
  `id_folha` INT NOT NULL AUTO_INCREMENT,
  `data_pagamento` DATE NOT NULL,
  `valor_salario` DECIMAL(10,2) NOT NULL,
  `valor_horas_extras` DECIMAL(10,2) NULL DEFAULT NULL,
  `valor_beneficios` DECIMAL(10,2) NULL DEFAULT NULL,
  `valor_descontos` DECIMAL(10,2) NULL DEFAULT NULL,
  `valor_total` DECIMAL(10,2) NOT NULL,
  `contrato_id` INT NOT NULL,
  PRIMARY KEY (`id_folha`),
  INDEX `fk_folhas_contratos` (`contrato_id` ASC) VISIBLE,
  CONSTRAINT `fk_folhas_contratos`
    FOREIGN KEY (`contrato_id`)
    REFERENCES `rh_sistema`.`contratos` (`id_contrato`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`folha_tem_beneficios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`folha_tem_beneficios` (
  `folha_id` INT NOT NULL,
  `beneficio_id` INT NOT NULL,
  PRIMARY KEY (`folha_id`, `beneficio_id`),
  INDEX `fk_ftb_beneficio` (`beneficio_id` ASC) VISIBLE,
  CONSTRAINT `fk_ftb_beneficio`
    FOREIGN KEY (`beneficio_id`)
    REFERENCES `rh_sistema`.`beneficios` (`id_beneficio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ftb_folha`
    FOREIGN KEY (`folha_id`)
    REFERENCES `rh_sistema`.`folhas_pagamento` (`id_folha`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`folha_tem_descontos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`folha_tem_descontos` (
  `folha_id` INT NOT NULL,
  `desconto_id` INT NOT NULL,
  PRIMARY KEY (`folha_id`, `desconto_id`),
  INDEX `fk_ftd_desconto` (`desconto_id` ASC) VISIBLE,
  CONSTRAINT `fk_ftd_desconto`
    FOREIGN KEY (`desconto_id`)
    REFERENCES `rh_sistema`.`descontos` (`id_desconto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ftd_folha`
    FOREIGN KEY (`folha_id`)
    REFERENCES `rh_sistema`.`folhas_pagamento` (`id_folha`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`funcionarios_skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`funcionarios_skills` (
  `funcionario_cpf` VARCHAR(11) NOT NULL,
  `skill_nome` VARCHAR(50) NOT NULL,
  `certificacao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`funcionario_cpf`, `skill_nome`),
  INDEX `fk_fs_skills` (`skill_nome` ASC) VISIBLE,
  CONSTRAINT `fk_fs_funcionarios`
    FOREIGN KEY (`funcionario_cpf`)
    REFERENCES `rh_sistema`.`funcionarios` (`cpf`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_fs_skills`
    FOREIGN KEY (`skill_nome`)
    REFERENCES `rh_sistema`.`skills` (`nome`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`pontos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`pontos` (
  `id_ponto` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(20) NOT NULL,
  `data_hora` DATETIME NOT NULL,
  `contrato_id` INT NOT NULL,
  PRIMARY KEY (`id_ponto`),
  INDEX `fk_pontos_contratos` (`contrato_id` ASC) VISIBLE,
  CONSTRAINT `fk_pontos_contratos`
    FOREIGN KEY (`contrato_id`)
    REFERENCES `rh_sistema`.`contratos` (`id_contrato`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`telefones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`telefones` (
  `numero` VARCHAR(20) NOT NULL,
  `funcionario_cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`numero`, `funcionario_cpf`),
  INDEX `fk_telefones_funcionarios` (`funcionario_cpf` ASC) VISIBLE,
  CONSTRAINT `fk_telefones_funcionarios`
    FOREIGN KEY (`funcionario_cpf`)
    REFERENCES `rh_sistema`.`funcionarios` (`cpf`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `rh_sistema`.`transacoes_horas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rh_sistema`.`transacoes_horas` (
  `id_transacao` INT NOT NULL AUTO_INCREMENT,
  `data_referencia` DATE NOT NULL,
  `quantidade_horas` DECIMAL(8,2) NOT NULL,
  `justificativa` VARCHAR(255) NOT NULL,
  `status` VARCHAR(30) NOT NULL,
  `comprovante` VARCHAR(255) NULL DEFAULT NULL,
  `tipo` VARCHAR(30) NOT NULL,
  `contrato_id` INT NOT NULL,
  PRIMARY KEY (`id_transacao`),
  INDEX `fk_transacoes_contratos` (`contrato_id` ASC) VISIBLE,
  CONSTRAINT `fk_transacoes_contratos`
    FOREIGN KEY (`contrato_id`)
    REFERENCES `rh_sistema`.`contratos` (`id_contrato`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
