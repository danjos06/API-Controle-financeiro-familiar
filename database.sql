CREATE DATABASE IF NOT EXISTS controleFinanceiroDB;
USE controleFinanceiroDB;

CREATE TABLE familias (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          limite_gastos_mensal DECIMAL(10, 2) DEFAULT 0.00
);

CREATE TABLE contas (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        familia_id INT,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL,
                        salario DECIMAL(10, 2) DEFAULT 0.00,
                        limite_gastos DECIMAL(10, 2) DEFAULT 0.00,
                        FOREIGN KEY (familia_id) REFERENCES familias(id) ON DELETE SET NULL
);

CREATE TABLE tipos_gastos (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nome VARCHAR(50) NOT NULL
);

CREATE TABLE movimentacoes (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               conta_id INT NOT NULL,
                               tipo_gasto_id INT,
                               descricao VARCHAR(150) NOT NULL,
                               valor DECIMAL(10, 2) NOT NULL,
                               tipo ENUM('ENTRADA', 'SAIDA') NOT NULL,
                               data_movimentacao DATE NOT NULL,
                               FOREIGN KEY (conta_id) REFERENCES contas(id) ON DELETE CASCADE,
                               FOREIGN KEY (tipo_gasto_id) REFERENCES tipos_gastos(id) ON DELETE SET NULL
);