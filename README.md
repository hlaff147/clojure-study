# Clojure CRUD API (In-Memory)

A simple RESTful CRUD API built with Clojure, using an in-memory store (no database). This project is for educational purposes to improve my Clojure skills.

## Features

- **No database needed** (uses Clojure's `atom` for in-memory storage)
- **Two implementations**:
  - Traditional: Ring + Compojure
  - Modern: Reitit + Integrant (with Swagger UI)
- **Full CRUD operations**:
  - Create, Read, Update, Delete items
- **JSON support** for requests/responses

## Prerequisites

- [Clojure](https://clojure.org/guides/getting_started)
- [Leiningen](https://leiningen.org/) (for project management)
- Java JDK 11+

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/your-username/clojure-crud.git
cd clojure-crud
```

### 2. Run the server (Compojure version)
```bash
lein run
```

### 3. Run the server (Reitit version - includes Swagger UI)
```bash
lein run
```
Then visit: http://localhost:3000/ for interactive API documentation

## API Endpoints

### Create an item (POST)
```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"Macbook Pro","price":1999.99}' http://localhost:3000/items
```

### List all items (GET)
```bash
curl http://localhost:3000/items
```

### Get specific item (GET)
```bash
curl http://localhost:3000/items/<item-id>
```

### Update item (PUT)
```bash
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Macbook Air","price":1299.99}' http://localhost:3000/items/<item-id>
```

### Delete item (DELETE)
```bash
curl -X DELETE http://localhost:3000/items/<item-id>
```

## Project Structure

```
clojure-crud/
├── src/
│   └── clojure_crud/
│       └── core.clj       # Main application code
├── project.clj            # Project configuration and dependencies
└── README.md              # This file
```

## Git Setup

To initialize and push this project to GitHub:

1. Initialize git repository:
```bash
git init
```

2. Add files and commit:
```bash
git add .
git commit -m "Initial commit: Simple Clojure CRUD API"
```

3. Create a new repository on GitHub and push:
```bash
git remote add origin https://github.com/your-username/clojure-crud.git
git branch -M main
git push -u origin main
```

## Learning Goals

- Understand basic Clojure web development
- Work with Ring and HTTP handlers
- Compare traditional (Compojure) vs modern (Reitit) routing
- Manage state with atoms
- Practice REST API design

## Next Steps

- [ ] Add tests
- [ ] Implement proper error handling
- [ ] Add persistence with a real database
- [ ] Implement authentication

## License

MIT