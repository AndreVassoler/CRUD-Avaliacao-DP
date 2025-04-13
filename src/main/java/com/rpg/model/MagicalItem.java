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

    @NotBlank(message = "O nome do item é obrigatório")
    private String name;

    @NotNull(message = "O tipo do item é obrigatório")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Min(value = 0, message = "A força deve ser no mínimo 0")
    @Max(value = 10, message = "A força deve ser no máximo 10")
    private int strength;

    @Min(value = 0, message = "A defesa deve ser no mínimo 0")
    @Max(value = 10, message = "A defesa deve ser no máximo 10")
    private int defense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    @JsonBackReference
    private GameCharacter character;

    public enum ItemType {
        ARMA, ARMADURA, AMULETO
    }

    public MagicalItem(String name, ItemType type, int strength, int defense) {
        this.name = name;
        this.type = type;
        this.strength = strength;
        this.defense = defense;
        validate();
    }

    public void validate() {
        if (strength == 0 && defense == 0) {
            throw new IllegalArgumentException("Um item não pode ter força e defesa igual a zero");
        }
        if (type == ItemType.ARMA && defense != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a zero");
        }
        if (type == ItemType.ARMADURA && strength != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a zero");
        }
        if (strength > 10 || defense > 10) {
            throw new IllegalArgumentException("Força e defesa não podem ser maiores que 10");
        }
        if (strength < 0 || defense < 0) {
            throw new IllegalArgumentException("Força e defesa não podem ser menores que 0");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagicalItem that = (MagicalItem) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
} 