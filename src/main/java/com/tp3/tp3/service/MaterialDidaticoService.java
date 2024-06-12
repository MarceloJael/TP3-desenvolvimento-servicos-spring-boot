package com.tp3.tp3.service;

import com.tp3.tp3.model.MaterialDidatico;
import com.tp3.tp3.repository.MaterialDidaticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialDidaticoService {

    @Autowired
    private MaterialDidaticoRepository materialDidaticoRepository;

    public MaterialDidatico save(MaterialDidatico materialDidatico) {
        return materialDidaticoRepository.save(materialDidatico);
    }

    public List<MaterialDidatico> findAll() {
        return materialDidaticoRepository.findAll();
    }

    public Optional<MaterialDidatico> findById(String id) {
        return materialDidaticoRepository.findById(id);
    }

    public void deleteById(String id) {
        materialDidaticoRepository.deleteById(id);
    }
}
