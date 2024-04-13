CREATE DATABASE IF NOT EXISTS Escola;
 
-- Usar o banco de dados Escola
USE Escola;
 
-- Tabela de pessoas
CREATE TABLE IF NOT EXISTS Pessoa (
    idPessoa INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    data_nascimento DATE,
    CPF VARCHAR(14) UNIQUE NOT NULL,
    TipoUsuario ENUM('Aluno', 'Professor', 'Admin'),
    Senha VARCHAR(255)
);
 
-- Tabela de alunos
CREATE TABLE IF NOT EXISTS Aluno (
    idAluno INT AUTO_INCREMENT PRIMARY KEY,
    idPessoa INT,
    FOREIGN KEY (idPessoa) REFERENCES Pessoa(idPessoa)
);
 
-- Tabela de professores
CREATE TABLE IF NOT EXISTS Professor (
    idProfessor INT AUTO_INCREMENT PRIMARY KEY,
    idPessoa INT,
    Disciplina VARCHAR(100),
    FOREIGN KEY (idPessoa) REFERENCES Pessoa(idPessoa)
);
 
-- Tabela de administradores
CREATE TABLE IF NOT EXISTS Admin (
    idAdmin INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL,
    Senha VARCHAR(255) NOT NULL
);
 
-- Tabela de turmas
CREATE TABLE IF NOT EXISTS Turma (
    idTurma INT AUTO_INCREMENT PRIMARY KEY,
    Serie VARCHAR(50),
    idProfessor INT,
    FOREIGN KEY (idProfessor) REFERENCES Professor(idProfessor)
);
 
-- Tabela de turma_alunos (relacionamento entre alunos e turmas)
CREATE TABLE IF NOT EXISTS Turma_Alunos (
    idTurma INT,
    idAluno INT,
    FOREIGN KEY (idTurma) REFERENCES Turma(idTurma),
    FOREIGN KEY (idAluno) REFERENCES Aluno(idAluno),
    PRIMARY KEY (idTurma, idAluno)
);
 
-- Tabela de presença
CREATE TABLE IF NOT EXISTS Presenca (
    idPresenca INT AUTO_INCREMENT PRIMARY KEY,
    idAluno INT,
    data_presenca DATE,
    situacao VARCHAR(50),
    FOREIGN KEY (idAluno) REFERENCES Aluno(idAluno)
);
 
-- Inserir um administrador
INSERT INTO Admin (Nome, CPF, Senha) VALUES ('Admin', '123.456.789-00', 'senha_admin');
 
-- Exemplo de inserção de um aluno
INSERT INTO Pessoa (Nome, data_nascimento, CPF, TipoUsuario, Senha) VALUES ('Aluno Teste', '2000-01-01', '111.222.333-44', 'Aluno', 'senha_aluno');
 
-- Exemplo de inserção de um professor
INSERT INTO Pessoa (Nome, data_nascimento, CPF, TipoUsuario, Senha) VALUES ('Professor Teste', '1980-01-01', '555.666.777-88', 'Professor', 'senha_professor');
 
-- Certifique-se de que as senhas sejam criptografadas antes de inseri-las no banco de dados.

SELECT * FROM PESSOA;