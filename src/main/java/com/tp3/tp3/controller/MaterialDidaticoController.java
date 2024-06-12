package com.tp3.tp3.controller;

import com.tp3.tp3.model.MaterialDidatico;
import com.tp3.tp3.service.MaterialDidaticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materialDidatico")
public class MaterialDidaticoController {

    @Autowired
    private MaterialDidaticoService materialDidaticoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialDidatico> createMaterialDidatico(@RequestBody MaterialDidatico materialDidatico) {
        MaterialDidatico savedMaterial = materialDidaticoService.save(materialDidatico);
        return new ResponseEntity<>(savedMaterial, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MaterialDidatico>> getAllMaterialDidatico() {
        List<MaterialDidatico> materialDidaticos = materialDidaticoService.findAll();
        return ResponseEntity.ok(materialDidaticos);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialDidatico> getMaterialDidaticoById(@PathVariable String id) {
        Optional<MaterialDidatico> materialDidatico = materialDidaticoService.findById(id);
        return materialDidatico.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialDidatico> updateMaterialDidatico(@PathVariable String id, @RequestBody MaterialDidatico materialDidatico) {
        if (!materialDidaticoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        materialDidatico.setId(id);
        MaterialDidatico updatedMaterial = materialDidaticoService.save(materialDidatico);
        return ResponseEntity.ok(updatedMaterial);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteMaterialDidatico(@PathVariable String id) {
        if (!materialDidaticoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        materialDidaticoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}