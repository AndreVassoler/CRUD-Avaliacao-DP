package com.rpg.service;

import com.rpg.model.GameCharacter;
import com.rpg.model.MagicalItem;
import com.rpg.repository.CharacterRepository;
import com.rpg.repository.MagicalItemRepository;
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
    private MagicalItemRepository magicalItemRepository;

    public GameCharacter createCharacter(GameCharacter character) {
        return characterRepository.save(character);
    }

    public List<GameCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<GameCharacter> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Transactional
    public GameCharacter updateAdventurerName(Long id, String newAdventurerName) {
        GameCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        character.setAdventurerName(newAdventurerName);
        return characterRepository.save(character);
    }

    @Transactional
    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }

    @Transactional
    public void addMagicalItemToCharacter(Long characterId, MagicalItem item) {
        GameCharacter character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        character.addMagicalItem(item);
        characterRepository.save(character);
    }

    @Transactional
    public void removeMagicalItemFromCharacter(Long characterId, Long itemId) {
        GameCharacter character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found"));
        MagicalItem item = magicalItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        character.removeMagicalItem(item);
        characterRepository.save(character);
    }

    public List<MagicalItem> getCharacterItems(Long characterId) {
        return magicalItemRepository.findByCharacterId(characterId);
    }

    public MagicalItem getCharacterAmulet(Long characterId) {
        return magicalItemRepository.findByCharacterIdAndType(characterId, com.rpg.model.ItemType.AMULETO);
    }
} 