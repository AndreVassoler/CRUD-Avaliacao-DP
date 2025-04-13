package com.rpg.service;

import com.rpg.model.MagicalItem;
import com.rpg.repository.MagicalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MagicalItemService {
    @Autowired
    private MagicalItemRepository magicalItemRepository;

    public MagicalItem createMagicalItem(MagicalItem item) {
        return magicalItemRepository.save(item);
    }

    public List<MagicalItem> getAllMagicalItems() {
        return magicalItemRepository.findAll();
    }

    public Optional<MagicalItem> getMagicalItemById(Long id) {
        return magicalItemRepository.findById(id);
    }
} 