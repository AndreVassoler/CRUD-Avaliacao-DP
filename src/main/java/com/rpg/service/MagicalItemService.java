package com.rpg.service;

import com.rpg.model.MagicalItem;
import com.rpg.repository.MagicalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MagicalItemService {
    @Autowired
    private MagicalItemRepository magicalItemRepository;

    public List<MagicalItem> getAllMagicalItems() {
        return magicalItemRepository.findAll();
    }

    public Optional<MagicalItem> getMagicalItemById(Long id) {
        return magicalItemRepository.findById(id);
    }

    @Transactional
    public MagicalItem createMagicalItem(MagicalItem item) {
        validateMagicalItem(item);
        return magicalItemRepository.save(item);
    }

    private void validateMagicalItem(MagicalItem item) {
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do item é obrigatório");
        }
        if (item.getType() == null) {
            throw new IllegalArgumentException("O tipo do item é obrigatório");
        }

        switch (item.getType()) {
            case ARMA:
                if (item.getDefense() != 0) {
                    throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a zero");
                }
                if (item.getStrength() == 0) {
                    throw new IllegalArgumentException("Itens do tipo ARMA devem ter força maior que zero");
                }
                break;
            case ARMADURA:
                if (item.getStrength() != 0) {
                    throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a zero");
                }
                if (item.getDefense() == 0) {
                    throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter defesa maior que zero");
                }
                break;
            case AMULETO:
                if (item.getStrength() == 0 && item.getDefense() == 0) {
                    throw new IllegalArgumentException("Amuletos devem ter pelo menos um atributo maior que zero");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de item inválido");
        }

        if (item.getStrength() < 0 || item.getStrength() > 10) {
            throw new IllegalArgumentException("A força deve estar entre 0 e 10");
        }
        if (item.getDefense() < 0 || item.getDefense() > 10) {
            throw new IllegalArgumentException("A defesa deve estar entre 0 e 10");
        }
    }
} 