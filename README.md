# RPG Management API

API para gerenciamento de personagens e itens mágicos em um sistema de RPG.

## Endpoints

### Personagens

#### Criar Personagem
```http
POST /api/characters
Content-Type: application/json

{
    "name": "Gandalf",
    "adventurerName": "O Cinzento",
    "characterClass": "GUERREIRO",
    "level": 1,
    "baseStrength": 5,
    "baseDefense": 5,
    "magicalItems": []
}
```

**Respostas:**
- 201 Created: Personagem criado com sucesso
- 400 Bad Request: Dados inválidos

#### Buscar Personagem
```http
GET /api/characters/{id}
```

**Respostas:**
- 200 OK: Personagem encontrado
- 404 Not Found: Personagem não encontrado
- 400 Bad Request: ID inválido

#### Remover Personagem
```http
DELETE /api/characters/{id}
```

**Respostas:**
- 204 No Content: Personagem removido com sucesso
- 404 Not Found: Personagem não encontrado
- 400 Bad Request: ID inválido

### Itens Mágicos

#### Adicionar Item Mágico
```http
POST /api/characters/{characterId}/items
Content-Type: application/json

# Exemplo de Arma
{
    "name": "Espada Mágica",
    "type": "ARMA",
    "strength": 5,
    "defense": 0
}

# Exemplo de Armadura
{
    "name": "Armadura Encantada",
    "type": "ARMADURA",
    "strength": 0,
    "defense": 5
}

# Exemplo de Amuleto
{
    "name": "Amuleto do Poder",
    "type": "AMULETO",
    "strength": 3,
    "defense": 2
}
```

**Respostas:**
- 201 Created: Item adicionado com sucesso
- 404 Not Found: Personagem não encontrado
- 400 Bad Request: Dados do item inválidos

#### Remover Item Mágico
```http
DELETE /api/characters/{characterId}/items/{itemId}
```

**Respostas:**
- 204 No Content: Item removido com sucesso
- 404 Not Found: Personagem ou item não encontrado
- 400 Bad Request: ID inválido

#### Listar Itens Mágicos
```http
GET /api/characters/{characterId}/items
```

**Respostas:**
- 200 OK: Lista de itens retornada com sucesso
- 404 Not Found: Personagem não encontrado
- 400 Bad Request: ID inválido

#### Buscar Amuleto
```http
GET /api/characters/{characterId}/amulet
```

**Respostas:**
- 200 OK: Amuleto encontrado
- 404 Not Found: Personagem não encontrado ou não possui amuleto

## Regras de Negócio

### Personagens
- O nome e nome de aventureiro são obrigatórios
- A classe deve ser uma das seguintes: GUERREIRO, MAGO, ARQUEIRO, LADINO, BARDO
- O nível mínimo é 1
- A força e defesa base devem estar entre 0 e 10
- A soma da força e defesa base deve ser 10

### Itens Mágicos
- O nome é obrigatório
- O tipo deve ser: ARMA, ARMADURA ou AMULETO
- A força e defesa devem estar entre 0 e 10
- Armas devem ter defesa = 0
- Armaduras devem ter força = 0
- Amuletos podem ter tanto força quanto defesa
- Um personagem só pode ter um amuleto por vez 