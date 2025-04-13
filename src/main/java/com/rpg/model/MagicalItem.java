package com.rpg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "magical_item")
public class MagicalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotNull(message = "O tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Min(value = 0, message = "A força mínima é 0")
    @Max(value = 10, message = "A força máxima é 10")
    private int strength;

    @Min(value = 0, message = "A defesa mínima é 0")
    @Max(value = 10, message = "A defesa máxima é 10")
    private int defense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    @JsonBackReference
    private GameCharacter character;

    public MagicalItem(String name, ItemType type, int strength, int defense) {
        this.name = name;
        this.type = type;
        this.strength = strength;
        this.defense = defense;
        validateAttributes();
    }

    private void validateAttributes() {
        if (type == ItemType.ARMA && defense != 0) {
            throw new IllegalArgumentException("Weapons must have 0 defense");
        }
        if (type == ItemType.ARMADURA && strength != 0) {
            throw new IllegalArgumentException("Armor must have 0 strength");
        }
        if (strength < 0 || strength > 10 || defense < 0 || defense > 10) {
            throw new IllegalArgumentException("Strength and defense must be between 0 and 10");
        }
        if (strength == 0 && defense == 0) {
            throw new IllegalArgumentException("Item must have at least one non-zero attribute");
        }
    }
} 