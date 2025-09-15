# Shopping List API 🛒

REST API w Spring Boot do zarządzania listą zakupów z automatycznym pozycjonowaniem produktów.

## 📋 Opis

API umożliwia dodawanie, przeglądanie i usuwanie produktów z listy zakupów przez REST endpoints. Każdy produkt ma automatycznie przypisywaną pozycję na liście, która jest zarządzana przy dodawaniu i usuwaniu elementów.

## 🚀 Technologie

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok**
- **Maven**
- **JUnit5**

## 🏃‍♂️ Uruchamianie

1. Sklonuj repozytorium:
```bash
git clone https://github.com/Dawid101/shopping-list-app.git
cd shopping-list-app
```

2. Uruchom API:
```bash
mvn spring-boot:run
```

3. API będzie dostępne pod: `http://localhost:8080`

4. Konsola H2: `http://localhost:8080/h2-console`
    - URL: `jdbc:h2:mem:shoppinglist`
    - User: `sa`
    - Password: (puste)

## 📚 API Endpoints

### 📌 Dodaj produkt
```http
POST /add
Content-Type: application/json

{
  "name": "Mleko",
  "quantity": 2
}
```

**Response:**
```json
{
  "name": "Mleko",
  "quantity": 2,
  "placeOnTheList": 1
}
```

### 📌 Pobierz wszystkie produkty
```http
GET /list
```

**Response:**
```json
[
  {
    "name": "Mleko",
    "quantity": 2,
    "placeOnTheList": 1
  },
  {
    "name": "Chleb",
    "quantity": 1,
    "placeOnTheList": 2
  }
]
```

### 📌 Pobierz pojedynczy produkt
```http
GET /list/{id}
```

**Response:**
```json
{
  "name": "Mleko",
  "quantity": 2,
  "placeOnTheList": 1
}
```

### 📌 Usuń produkt
```http
DELETE /delete/{id}
```

**Response:**
```json
"Product deleted"
```

## ⚙️ Jak działa pozycjonowanie?

- **Dodawanie:** Nowy produkt automatycznie otrzymuje najwyższą pozycję + 1
- **Usuwanie:** Po usunięciu produktu, wszystkie produkty z wyższych pozycji są przesuwane w dół
- **Lista:** Produkty są zawsze zwracane posortowane według pozycji (od 1 do n)

### Przykład:
```
Początkowa lista:
1. Mleko
2. Chleb  
3. Masło

Po usunięciu "Chleb":
1. Mleko
2. Masło  ← automatycznie przesunięte z pozycji 3 na 2
```

## 🗃️ Model danych

### Product
```java
{
  "id": "uuid-string",        // Generowane automatycznie
  "name": "string",           // Nazwa produktu
  "quantity": "integer",      // Ilość
  "placeOnTheList": "integer" // Pozycja na liście (1, 2, 3...)
}
```

## 🧪 Testowanie

Użyj narzędzi takich jak:
- **Postman**
- **cURL**
- **HTTPie**

### Przykład cURL:
```bash
# Dodaj produkt
curl -X POST http://localhost:8080/add \
  -H "Content-Type: application/json" \
  -d '{"name":"Mleko","quantity":2}'

# Pobierz listę
curl http://localhost:8080/list
```

## 🔮 Planowane funkcjonalności

- [ ] Walidacja danych wejściowych
- [X] Testy jednostkowe
- [x] Zmiana pozycji produktów na liście
- [ ] Edytowanie produktów
- [ ] Kategorie produktów
