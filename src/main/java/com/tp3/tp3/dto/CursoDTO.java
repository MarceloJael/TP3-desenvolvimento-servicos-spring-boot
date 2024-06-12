package com.tp3.tp3.dto;

import com.tp3.tp3.model.Curso;

import java.util.Set;
import java.util.stream.Collectors;

public class CursoDTO {
    private Long id;
    private String nome;
    private Set<Long> alunoIds;

    public CursoDTO() {}

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.alunoIds = curso.getAlunos().stream().map(aluno -> aluno.getId()).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Long> getAlunoIds() {
        return alunoIds;
    }

    public void setAlunoIds(Set<Long> alunoIds) {
        this.alunoIds = alunoIds;
    }

    public Curso toEntity() {
        Curso curso = new Curso();
        curso.setId(this.id);
        curso.setNome(this.nome);
        // Note que os alunos não são configurados diretamente no DTO
        return curso;
    }
}