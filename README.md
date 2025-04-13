# API de Gerenciamento de RPG

API para gerenciamento de personagens e itens mágicos de RPG.

## Endpoints

### Personagens

#### Criar Personagem
```http
POST /api/characters
```
```json
{
    "name": "Merlin",
    "adventurerName": "O Sábio",
    "level": 1,
    "baseStrength": 5,
    "baseDefense": 5
}
```
**Regras:**
- Nome e nome de aventureiro são obrigatórios
- Nível mínimo é 1
- A soma de força e defesa base deve ser 10
- Força e defesa base devem estar entre 0 e 10

#### Listar Personagens
```http
GET /api/characters
```

#### Buscar Personagem por ID
```http
GET /api/characters/{id}
```

#### Atualizar Nome de Aventureiro
```http
PUT /api/characters/{id}/adventurer-name
```
```json
{
    "adventurerName": "Novo Nome"
}
```

#### Deletar Personagem
```http
DELETE /api/characters/{id}
```

### Itens Mágicos

#### Adicionar Item Mágico ao Personagem
```http
POST /api/characters/{characterId}/items
```

**Exemplos de JSON por tipo de item:**

Arma:
```json
{
    "name": "Espada Flamejante",
    "type": "ARMA",
    "strength": 7,
    "defense": 0
}
```

Armadura:
```json
{
    "name": "Armadura de Diamante",
    "type": "ARMADURA",
    "strength": 0,
    "defense": 8
}
```

Amuleto:
```json
{
    "name": "Amuleto da Sabedoria",
    "type": "AMULETO",
    "strength": 5,
    "defense": 5
}
```

#### Listar Itens do Personagem
```http
GET /api/characters/{characterId}/items
```

#### Remover Item do Personagem
```http
DELETE /api/characters/{characterId}/items/{itemId}
```

## Regras de Negócio

### Personagens
- Nome e nome de aventureiro são obrigatórios
- Nível mínimo é 1
- A soma de força e defesa base deve ser exatamente 10
- Força e defesa base devem estar entre 0 e 10

### Itens Mágicos

#### Tipos de Item
Os itens mágicos podem ser apenas dos seguintes tipos:
- ARMA
- ARMADURA
- AMULETO

#### Regras por Tipo de Item

**ARMA:**
- Defesa OBRIGATORIAMENTE zero
- Força deve ser maior que zero
- Força máxima 10

**ARMADURA:**
- Força OBRIGATORIAMENTE zero
- Defesa deve ser maior que zero
- Defesa máxima 10

**AMULETO:**
- Pode ter tanto força quanto defesa
- Não pode ter força E defesa igual a zero
- Força e defesa máximas 10
- Um personagem só pode ter UM amuleto equipado por vez

#### Regras Gerais de Itens
- Não podem existir itens com zero de defesa E zero de força
- Força e defesa devem estar entre 0 e 10
- Um item não pode estar equipado em mais de um personagem ao mesmo tempo
- O mesmo item não pode ser equipado duas vezes no mesmo personagem

## Códigos de Resposta

- 200: Sucesso
- 201: Criado com sucesso
- 400: Erro de validação
- 404: Recurso não encontrado
- 500: Erro interno do servidor

## Exemplos de Erros

### Tentativa de adicionar segundo amuleto
```json
{
    "error": "O personagem já possui um amuleto equipado"
}
```

### Tentativa de criar arma com defesa
```json
{
    "error": "Itens do tipo ARMA devem ter defesa igual a zero"
}
```

### Tentativa de equipar item já equipado
```json
{
    "error": "Este item já está equipado em outro personagem"
}
``` 