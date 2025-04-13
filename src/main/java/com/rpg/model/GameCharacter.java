package com.rpg.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "game_character")
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do personagem é obrigatório")
    private String name;

    @NotBlank(message = "O nome de aventureiro é obrigatório")
    private String adventurerName;

    @NotNull(message = "A classe é obrigatória")
    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;

    @Min(value = 1, message = "O nível mínimo é 1")
    private int level;

    @Min(value = 0, message = "A força base mínima é 0")
    @Max(value = 10, message = "A força base máxima é 10")
    private int baseStrength;

    @Min(value = 0, message = "A defesa base mínima é 0")
    @Max(value = 10, message = "A defesa base máxima é 10")
    private int baseDefense;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MagicalItem> magicalItems = new ArrayList<>();

    public GameCharacter(String name, String adventurerName, CharacterClass characterClass, int level, int baseStrength, int baseDefense) {
        this.name = name;
        this.adventurerName = adventurerName;
        this.characterClass = characterClass;
        this.level = level;
        this.baseStrength = baseStrength;
        this.baseDefense = baseDefense;
        validateAttributes();
    }

    private void validateAttributes() {
        if (baseStrength + baseDefense != 10) {
            throw new IllegalArgumentException("Total of strength and defense must be 10");
        }
        if (baseStrength < 0 || baseStrength > 10 || baseDefense < 0 || baseDefense > 10) {
            throw new IllegalArgumentException("Strength and defense must be between 0 and 10");
        }
    }

    public int getTotalStrength() {
        return baseStrength + magicalItems.stream()
                .mapToInt(MagicalItem::getStrength)
                .sum();
    }

    public int getTotalDefense() {
        return baseDefense + magicalItems.stream()
                .mapToInt(MagicalItem::getDefense)
                .sum();
    }

    public void addMagicalItem(MagicalItem item) {
        if (item.getType() == ItemType.AMULETO) {
            boolean hasAmulet = magicalItems.stream()
                    .anyMatch(i -> i.getType() == ItemType.AMULETO);
            if (hasAmulet) {
                throw new IllegalStateException("Character already has an amulet");
            }
        }
        item.setCharacter(this);
        magicalItems.add(item);
    }

    public void removeMagicalItem(MagicalItem item) {
        magicalItems.remove(item);
        item.setCharacter(null);
    }
} 