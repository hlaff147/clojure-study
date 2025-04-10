# Clojure CRUD API (In-Memory)

A simple RESTful CRUD API built with Clojure, featuring an in-memory store (using Clojure’s `atom`). This project is designed for educational purposes to explore both traditional and modern web development approaches in Clojure.

## Features

- **No database needed** – state is managed in-memory with Clojure’s `atom`.
- **Two implementations**:
  - Traditional: Ring + Compojure
  - Modern: Reitit + Integrant (with Swagger UI)
- **Full CRUD operations** – supports Create, Read, Update, and Delete.
- **JSON support** for requests and responses.

## Prerequisites

- [Clojure](https://clojure.org/guides/getting_started)
- [Leiningen](https://leiningen.org/) for project management
- Java JDK 11+

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/clojure-crud.git
   cd clojure-crud
   ```

2. **Run the server (choose one):**
   - For the Compojure version:
     ```bash
     lein run
     ```
   - For the Reitit version with Swagger UI:
     ```bash
     lein run
     ```
     
   Access the interactive API documentation at [http://localhost:3000/](http://localhost:3000/).

## API Endpoints

### Create an item (POST)
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"Macbook Pro","price":1999.99}' http://localhost:3000/items
```

### List all items (GET)
```bash
curl http://localhost:3000/items
```

### Get a specific item (GET)
```bash
curl http://localhost:3000/items/<item-id>
```

### Update an item (PUT)
```bash
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Macbook Air","price":1299.99}' http://localhost:3000/items/<item-id>
```

### Delete an item (DELETE)
```bash
curl -X DELETE http://localhost:3000/items/<item-id>
```

## Project Structure

```
clojure-crud/
├── src/
│   └── clojure_crud/
│       ├── core.clj           # Main application code
│       ├── controller/
│       │   └── item_controller.clj  # API controllers
│       ├── repository/
│       │   └── item_repository.clj  # Data access layer
│       ├── service/
│       │   └── item_service.clj     # Business logic
│       └── specs/
│           └── item_spec.clj        # Specifications for validating items
├── project.clj                # Project configuration and dependencies
└── README.md                  # This file
```

## Next Steps

### To Enhance the Project
- **Add Automated Tests:** Implement unit and integration tests to ensure correct behavior.
- **Improve Error Handling:** Enhance your error management to capture and return meaningful errors.
- **Integrate a Persistent Database:** Replace or complement the in-memory store with a real database.
- **Implement Authentication & Authorization:** Secure API endpoints for production readiness.
- **Performance Monitoring:** Add logging and performance analysis to monitor and optimize API performance.

### To Deepen Your Clojure Knowledge
- **Explore Clojure Fundamentals:** Study immutable data structures and functional programming concepts.
- **Learn Clojure Spec:** Gain proficiency with [`clojure.spec`](https://clojure.org/guides/spec) for data validation and testing.
- **Delve into Reitit & Integrant:** Understand modern routing and application system management using [Reitit](https://github.com/metosin/reitit) and [Integrant](https://github.com/weavejester/integrant).
- **Concurrency in Clojure:** Learn about atoms, refs, agents, and their roles in handling state and concurrency.
- **Follow Community Resources:** Engage with [Clojure documentation](https://clojure.org), online tutorials, blogs, and video content.

## Git Setup

1. **Initialize the repository:**
   ```bash
   git init
   ```

2. **Add and commit files:**
   ```bash
   git add .
   git commit -m "Initial commit: Simple Clojure CRUD API"
   ```

3. **Push to GitHub:**
   ```bash
   git remote add origin https://github.com/your-username/clojure-crud.git
   git branch -M main
   git push -u origin main
   ```

## License

MIT