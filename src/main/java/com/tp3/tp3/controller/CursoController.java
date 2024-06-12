package com.tp3.tp3.controller;

import com.tp3.tp3.dto.CursoDTO;
import com.tp3.tp3.model.Curso;
import com.tp3.tp3.service.CursoCacheService;
import com.tp3.tp3.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoCacheService cursoCacheService;

    @Autowired
    public CursoController(CursoService cursoService, CursoCacheService cursoCacheService) {
        this.cursoService = cursoService;
        this.cursoCacheService = cursoCacheService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoDTO cursoDTO) {
        Curso curso = cursoDTO.toEntity();
        Curso savedCurso = cursoService.save(curso);
        cursoCacheService.cacheCurso(savedCurso);
        return new ResponseEntity<>(new CursoDTO(savedCurso), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CursoDTO> getCurso(@PathVariable Long id) {
        Curso curso = cursoCacheService.getCursoFromCache(id);
        if (curso == null) {
            curso = cursoService.findById(id).orElse(null);
            if (curso != null) {
                cursoCacheService.cacheCurso(curso);
            }
        }
        return curso != null ? ResponseEntity.ok(new CursoDTO(curso)) : ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        List<Curso> cursos = cursoService.findAll();
        List<CursoDTO> cursoDTOs = cursos.stream().map(CursoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(cursoDTOs);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        Curso curso = cursoDTO.toEntity();
        curso.setId(id);
        Curso updatedCurso = cursoService.save(curso);
        cursoCacheService.cacheCurso(updatedCurso);
        return ResponseEntity.ok(new CursoDTO(updatedCurso));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteById(id);
        cursoCacheService.evictCursoFromCache(id);
        return ResponseEntity.noContent().build();
    }
}