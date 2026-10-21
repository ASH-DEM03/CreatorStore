# CreatorStore

A REST API for a small online store, built with **Java and Spring Boot**. It manages products and customer orders, and handles stock checking, price calculation, and order saving in a single transaction.

This is my first backend project. Along the way I worked through entity relationships, JSON serialization loops, validation, and database mapping problems.

## Features

- **Products**: create, update, list, get by id, and delete
- **Orders**: place an order with several items in one request
- **Stock management**: the API checks that each product exists and has enough stock, then reduces the stock
- **Server-side pricing**: the total is calculated from the prices in the database, never from the client
- **Price history**: each order line stores the price at the time of purchase
- **Validation**: invalid requests are rejected with `@Valid` and Bean Validation
- **Transactions**: an order is saved completely or not at all
- **API documentation**: Swagger UI is generated automatically

## Tech stack

| Area | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4 |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL (hosted on Supabase) |
| Validation | Jakarta Bean Validation |
| API docs | springdoc-openapi (Swagger UI) |
| Utilities | Lombok, dotenv-java |
| Build tool | Maven |
| Testing the API | Postman |

## Architecture

The project uses a layered structure:

```
Controller  →  Service  →  Repository  →  Database
(HTTP)         (logic)     (queries)
```

```
src/main/java/com/ash/creatorstore
├── Controllers     ProductController, OrderController
├── Services        ProductService, OrderService
├── repositories    ProductRepository, OrderRepository, OrderItemRepository
├── entities        Product, Order, OrderItems
└── dto             OrderRequest, OrderItemRequest
```

**Data model**

```
Order 1 ──── * OrderItems * ──── 1 Product
```

An order has many order items, and each item points to one product, with its quantity and the price at purchase.

**Why DTOs?** The client sends only `productId` and `quantity` for each item. Fields such as price, status, and total are set by the server, so a client can't change them.

## Getting started

### Requirements

- Java 17 or newer
- A PostgreSQL database (for example, a free Supabase project)

### 1. Clone the repository

```bash
git clone https://github.com/ASH-DEM03/CreatorStore.git
cd CreatorStore
```

### 2. Create a `.env` file

Create a file named `.env` in the project root, next to `pom.xml`. Use `.env.example` as a template:

```
DATABASE_URL=jdbc:postgresql://HOST:5432/postgres
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
```

The `.env` file is listed in `.gitignore`, so your credentials are never committed.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`. Hibernate creates the tables automatically on the first run.

### 4. Open the API docs

Swagger UI: `http://localhost:8080/swagger-ui.html`

## API endpoints

### Products

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/products` | Create a product |
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{id}` | Get one product |
| `PUT` | `/api/products/{id}` | Update a product |
| `DELETE` | `/api/products/{id}` | Delete a product |

Example request, `POST /api/products`:

```json
{
  "name": "T-shirt",
  "description": "Cotton, black",
  "category": "Apparel",
  "price": 199.00,
  "stockQuantity": 50
}
```

### Orders

| Method | URL | Description |
|---|---|---|
| `POST` | `/api/orders` | Place an order |

Example request, `POST /api/orders`:

```json
{
  "customerName": "Ali Yilmaz",
  "customerEmail": "ali@example.com",
  "customerPhone": "5551234567",
  "customerAddress": "Istanbul, Turkey",
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

Example response (shortened):

```json
{
  "id": 1,
  "customerName": "Ali Yilmaz",
  "status": "CONFIRMED",
  "totalPrice": 398.00,
  "orderItems": [
    {
      "quantity": 2,
      "priceAtPurchase": 199.00,
      "product": { "id": 1, "name": "T-shirt" }
    }
  ]
}
```

**What happens when an order is placed**

1. The request is validated (name, email, phone, address, at least one item).
2. For each item, the product is loaded from the database.
3. The stock is checked. If there isn't enough, the whole order is rejected.
4. The line total (price × quantity) is added to the order total.
5. The product stock is reduced.
6. The order and its items are saved together in one transaction.

## Roadmap

- [ ] Global exception handling with proper HTTP status codes (404, 400)
- [ ] Response DTOs instead of returning entities
- [ ] Order status as an enum and an endpoint to update it
- [ ] `GET` endpoints for orders
- [ ] Authentication and roles with Spring Security
- [ ] Unit and integration tests
- [ ] Docker setup

## What I learned

- Mapping entity relationships (`@OneToMany`, `@ManyToOne`) and avoiding JSON infinite loops
- Why DTOs matter for security and clean APIs
- Using `@Transactional` so related database changes succeed or fail together
- Reading stack traces and fixing real errors
- Keeping secrets out of Git with environment variables

## Author

**ASH-DEM03**: [GitHub](https://github.com/ASH-DEM03)
