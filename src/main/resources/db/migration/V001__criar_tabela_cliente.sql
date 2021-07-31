CREATE TABLE cliente (
  codigo BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  cpf VARCHAR(20) NOT NULL,
  rg VARCHAR(15),
  data_nascimento DATETIME,
  ativo TINYINT NULL,
  logradouro VARCHAR(80) NULL,
  numero VARCHAR(4) NULL,
  complemento VARCHAR(50) NULL,
  bairro VARCHAR(45) NULL,
  cep VARCHAR(45) NULL,
  cidade VARCHAR(45) NULL,
  estado VARCHAR(45) NULL,
  
  PRIMARY KEY (codigo));
    
  