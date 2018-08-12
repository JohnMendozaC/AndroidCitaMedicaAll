-- -----------------------------------------------------
-- Table medico
-- -----------------------------------------------------
CREATE TABLE medico(
  idmedico INT NOT NULL,
  cod_tarjetapro VARCHAR(45) NULL,
  especialidad VARCHAR(45) NULL,
  año_experiencia FLOAT NULL,
  consultorio VARCHAR(45) NULL,
  aten_domicilio boolean NULL,
  PRIMARY KEY (idmedico));

-- select * from medico

-- -----------------------------------------------------
-- Table paciente
-- ----------------------------------------------------
CREATE TABLE paciente (
  idpaciente INT NOT NULL,
  nombre VARCHAR(45) NULL,
  apellidos VARCHAR(45) NULL,
  fecha_nacimiento DATE NULL,
  identificacion VARCHAR(45) NULL,
  PRIMARY KEY (idpaciente));


-- select * from paciente

-- -----------------------------------------------------
-- Table historial_paciente
-- -----------------------------------------------------
CREATE TABLE historial_paciente (
  idmedicofk INT NOT NULL,
  idpacientefk INT NOT NULL,
  is_tratamiento boolean NULL,
  val_cuotamod FLOAT NULL,
  fyhnuevacita DATE NULL,
  aten_cita boolean NULL,
  firma bytea,
  PRIMARY KEY (idmedicofk, idpacientefk),
    FOREIGN KEY (idmedicofk)
    REFERENCES medico (idmedico),
    FOREIGN KEY (idpacientefk)
    REFERENCES paciente (idpaciente)); 

-- select * from historial_paciente
