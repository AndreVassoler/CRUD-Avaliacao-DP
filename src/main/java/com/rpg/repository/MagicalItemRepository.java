package com.rpg.repository;

import com.rpg.model.MagicalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagicalItemRepository extends JpaRepository<MagicalItem, Long> {
    List<MagicalItem> findByCharacterId(Long characterId);
    MagicalItem findByCharacterIdAndType(Long characterId, com.rpg.model.ItemType type);
} 