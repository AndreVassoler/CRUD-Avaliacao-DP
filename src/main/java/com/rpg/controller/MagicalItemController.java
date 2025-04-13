package com.rpg.controller;

import com.rpg.model.MagicalItem;
import com.rpg.service.MagicalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class MagicalItemController {
    @Autowired
    private MagicalItemService magicalItemService;

    @PostMapping
    public ResponseEntity<MagicalItem> createMagicalItem(@RequestBody MagicalItem item) {
        return ResponseEntity.ok(magicalItemService.createMagicalItem(item));
    }

    @GetMapping
    public ResponseEntity<List<MagicalItem>> getAllMagicalItems() {
        return ResponseEntity.ok(magicalItemService.getAllMagicalItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MagicalItem> getMagicalItemById(@PathVariable Long id) {
        return magicalItemService.getMagicalItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}