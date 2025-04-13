package com.rpg.controller;

import com.rpg.model.GameCharacter;
import com.rpg.model.MagicalItem;
import com.rpg.service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<GameCharacter>> getAllCharacters() {
        List<GameCharacter> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    @PostMapping
    public ResponseEntity<?> createCharacter(@Valid @RequestBody GameCharacter character) {
        try {
            GameCharacter savedCharacter = characterService.createCharacter(character);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacter(@PathVariable(name = "id") Long id) {
        try {
            if (id == null || id <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem inválido");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<GameCharacter> character = characterService.getCharacterById(id);
            if (character.isPresent()) {
                return ResponseEntity.ok(character.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Personagem não encontrado com ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem não encontrado com ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable(name = "id") Long id) {
        try {
            if (id == null || id <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem inválido");
                return ResponseEntity.badRequest().body(response);
            }

            characterService.deleteCharacter(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "ID inválido: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem não encontrado com ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/{characterId}/items")
    public ResponseEntity<?> addMagicalItem(
            @PathVariable(name = "characterId") Long characterId,
            @Valid @RequestBody MagicalItem item) {
        try {
            if (characterId == null || characterId <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem inválido");
                return ResponseEntity.badRequest().body(response);
            }

            characterService.addMagicalItemToCharacter(characterId, item);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Dados inválidos: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem não encontrado com ID: " + characterId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{characterId}/items/{itemId}")
    public ResponseEntity<?> removeMagicalItem(
            @PathVariable(name = "characterId") Long characterId,
            @PathVariable(name = "itemId") Long itemId) {
        try {
            if (characterId == null || characterId <= 0 || itemId == null || itemId <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem ou do item inválido");
                return ResponseEntity.badRequest().body(response);
            }

            characterService.removeMagicalItemFromCharacter(characterId, itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem ou item não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{characterId}/items")
    public ResponseEntity<?> getCharacterItems(
            @PathVariable(name = "characterId") Long characterId) {
        try {
            if (characterId == null || characterId <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem inválido");
                return ResponseEntity.badRequest().body(response);
            }

            List<MagicalItem> items = characterService.getCharacterItems(characterId);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem não encontrado com ID: " + characterId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{characterId}/amulet")
    public ResponseEntity<MagicalItem> getCharacterAmulet(
            @PathVariable(name = "characterId") Long characterId) {
        MagicalItem amulet = characterService.getCharacterAmulet(characterId);
        return amulet != null ? ResponseEntity.ok(amulet) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/adventurer-name")
    public ResponseEntity<?> updateAdventurerName(
            @PathVariable(name = "id") Long id,
            @RequestBody String newAdventurerName) {
        try {
            if (id == null || id <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "ID do personagem inválido");
                return ResponseEntity.badRequest().body(response);
            }

            GameCharacter updated = characterService.updateAdventurerName(id, newAdventurerName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Personagem não encontrado com ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
} 