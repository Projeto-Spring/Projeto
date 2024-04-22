# Projeto

SCRIPT DBPROJETO

-- Criar o banco de dados dbprojeto
CREATE DATABASE IF NOT EXISTS dbprojeto;
-- Usar o banco de dados dbprojeto
USE dbprojeto;
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
    Nome VARCHAR(255) NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL
);
-- Tabela de professores
CREATE TABLE IF NOT EXISTS Professor (
    idProfessor INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL
);
-- Tabela de administradores
CREATE TABLE IF NOT EXISTS Admin (
    idAdmin INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    CPF VARCHAR(14) UNIQUE NOT NULL
);
-- Tabela de turmas
CREATE TABLE IF NOT EXISTS Turma (
    idTurma INT AUTO_INCREMENT PRIMARY KEY,
    Serie VARCHAR(50),
    idProfessor INT,
    Disciplina VARCHAR(100),
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

 
-- Tabela de presenca
CREATE TABLE IF NOT EXISTS Presenca (
    idPresenca INT AUTO_INCREMENT PRIMARY KEY,
    idAluno INT,
    IdTurma INT,
    data_presenca DATE,
    situacao VARCHAR(50),
    FOREIGN KEY (idAluno) REFERENCES Aluno(idAluno),
    FOREIGN KEY (idTurma) REFERENCES Turma(idTurma)
);

 
INSERT INTO Pessoa (Nome, data_nascimento, CPF, TipoUsuario, Senha) VALUES ('Admin Teste', '1980-01-01', '111.111.111-11', 'Admin', 1);