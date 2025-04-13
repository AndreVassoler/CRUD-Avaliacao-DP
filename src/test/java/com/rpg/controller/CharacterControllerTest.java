package com.rpg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.model.CharacterClass;
import com.rpg.model.GameCharacter;
import com.rpg.service.CharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterService characterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCharacter_ValidInput_ReturnsCreated() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setName("Gandalf");
        character.setAdventurerName("O Cinzento");
        character.setCharacterClass(CharacterClass.MAGO);
        character.setLevel(1);
        character.setBaseStrength(5);
        character.setBaseDefense(5);

        when(characterService.createCharacter(any(GameCharacter.class))).thenReturn(character);

        mockMvc.perform(post("/api/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(character)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gandalf"))
                .andExpect(jsonPath("$.adventurerName").value("O Cinzento"));
    }

    @Test
    public void getCharacter_ExistingId_ReturnsCharacter() throws Exception {
        GameCharacter character = new GameCharacter();
        character.setId(1L);
        character.setName("Gandalf");
        character.setAdventurerName("O Cinzento");
        character.setCharacterClass(CharacterClass.MAGO);
        character.setLevel(1);
        character.setBaseStrength(5);
        character.setBaseDefense(5);

        when(characterService.getCharacterById(1L)).thenReturn(Optional.of(character));

        mockMvc.perform(get("/api/characters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gandalf"));
    }

    @Test
    public void getAllCharacters_ReturnsCharacterList() throws Exception {
        GameCharacter character1 = new GameCharacter();
        character1.setId(1L);
        character1.setName("Gandalf");
        
        GameCharacter character2 = new GameCharacter();
        character2.setId(2L);
        character2.setName("Aragorn");

        when(characterService.getAllCharacters()).thenReturn(Arrays.asList(character1, character2));

        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gandalf"))
                .andExpect(jsonPath("$[1].name").value("Aragorn"));
    }

    @Test
    public void deleteCharacter_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/characters/1"))
                .andExpect(status().isNoContent());
    }
} 