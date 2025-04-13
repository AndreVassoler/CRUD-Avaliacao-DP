package com.rpg.controller;

import com.rpg.model.MagicalItem;
import com.rpg.service.MagicalItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class MagicalItemController {

    @Autowired
    private MagicalItemService magicalItemService;

    @GetMapping
    public ResponseEntity<List<MagicalItem>> getAllMagicalItems() {
        List<MagicalItem> items = magicalItemService.getAllMagicalItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMagicalItemById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do item inválido");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<MagicalItem> item = magicalItemService.getMagicalItemById(id);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Item não encontrado com ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Erro ao buscar item: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createMagicalItem(@Valid @RequestBody MagicalItem item) {
        try {
            MagicalItem savedItem = magicalItemService.createMagicalItem(item);
            return ResponseEntity.ok(savedItem);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Dados inválidos: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}