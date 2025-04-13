package com.rpg.service;

import com.rpg.model.GameCharacter;
import com.rpg.model.MagicalItem;
import com.rpg.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MagicalItemService magicalItemService;

    public List<GameCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<GameCharacter> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Transactional
    public GameCharacter createCharacter(GameCharacter character) {
        validateCharacter(character);
        return characterRepository.save(character);
    }

    @Transactional
    public void deleteCharacter(Long id) {
        if (!characterRepository.existsById(id)) {
            throw new RuntimeException("Personagem não encontrado com ID: " + id);
        }
        characterRepository.deleteById(id);
    }

    @Transactional
    public void addMagicalItemToCharacter(Long characterId, MagicalItem item) {
        GameCharacter character = characterRepository.findById(characterId)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + characterId));

        if (item.getId() != null) {
            boolean itemExists = character.getMagicalItems().stream()
                .anyMatch(existingItem -> existingItem.getId().equals(item.getId()));
            
            if (itemExists) {
                throw new IllegalArgumentException("Este item já está equipado no personagem");
            }

            MagicalItem existingItem = magicalItemService.getMagicalItemById(item.getId())
                .orElseThrow(() -> new IllegalArgumentException("Item mágico não encontrado com ID: " + item.getId()));
            
            if (existingItem.getCharacter() != null) {
                throw new IllegalArgumentException("Este item já está equipado em outro personagem");
            }
        }

        item.validate();

        if (item.getType() == MagicalItem.ItemType.AMULETO) {
            boolean hasAmulet = character.getMagicalItems().stream()
                .anyMatch(existingItem -> existingItem.getType() == MagicalItem.ItemType.AMULETO);
            
            if (hasAmulet) {
                throw new IllegalArgumentException("O personagem já possui um amuleto equipado");
            }
        }

        item.setCharacter(character);
        MagicalItem savedItem = magicalItemService.createMagicalItem(item);
        character.getMagicalItems().add(savedItem);
        characterRepository.save(character);
    }

    @Transactional
    public void removeMagicalItemFromCharacter(Long characterId, Long itemId) {
        GameCharacter character = characterRepository.findById(characterId)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + characterId));

        MagicalItem item = character.getMagicalItems().stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado no personagem"));

        item.setCharacter(null);
        character.getMagicalItems().remove(item);
        characterRepository.save(character);
    }

    public List<MagicalItem> getCharacterItems(Long characterId) {
        GameCharacter character = characterRepository.findById(characterId)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + characterId));
        
        return character.getMagicalItems();
    }

    public MagicalItem getCharacterAmulet(Long characterId) {
        GameCharacter character = characterRepository.findById(characterId)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + characterId));

        return character.getMagicalItems().stream()
            .filter(item -> item.getType() == MagicalItem.ItemType.AMULETO)
            .findFirst()
            .orElse(null);
    }

    @Transactional
    public GameCharacter updateAdventurerName(Long id, String newAdventurerName) {
        GameCharacter character = characterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado com ID: " + id));
        
        character.setAdventurerName(newAdventurerName);
        return characterRepository.save(character);
    }

    private void validateCharacter(GameCharacter character) {
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem é obrigatório");
        }
        if (character.getAdventurerName() == null || character.getAdventurerName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome de aventureiro é obrigatório");
        }
        if (character.getLevel() < 1) {
            throw new IllegalArgumentException("O nível mínimo é 1");
        }
        if (character.getBaseStrength() < 0 || character.getBaseStrength() > 10 ||
            character.getBaseDefense() < 0 || character.getBaseDefense() > 10) {
            throw new IllegalArgumentException("A força e defesa base devem estar entre 0 e 10");
        }
        if (character.getBaseStrength() + character.getBaseDefense() != 10) {
            throw new IllegalArgumentException("A soma da força e defesa base deve ser 10");
        }
    }
} 