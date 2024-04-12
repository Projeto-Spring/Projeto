package com.projeto.model;

import java.util.List;

public class Turma {
    private Long idTurma;
    private String serie;
    private List<Long> alunosIds; // IDs dos alunos
    private Long professorId;

    public Turma(Long idTurma, String serie, List<Long> alunosIds, Long professorId) {
        this.idTurma = idTurma;
        this.serie = serie;
        this.alunosIds = alunosIds;
        this.professorId = professorId;
    }

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public List<Long> getAlunosIds() {
        return alunosIds;
    }

    public void setAlunosIds(List<Long> alunosIds) {
        this.alunosIds = alunosIds;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}
