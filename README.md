# Employee Vaccination Inventory

A full-stack application for managing employee vaccination status, featuring a modernized Spring Boot 3 backend and a React 19 frontend, fully integrated with **Auth0 JWT Authentication**.

---

## 🛠️ Tech Stack & Architecture

-   **Frontend**: React 19, Vite 6, PrimeReact, Auth0 SDK.
-   **Backend**: Spring Boot 3.4.1 (Java 24), Gradle 8.14.4 (with Wrapper).
-   **Database**: PostgreSQL 17.
-   **Security**: Stateless OAuth2 Resource Server (Auth0).
-   **Orchestration**: Docker Compose.

## 🚀 Quick Start with Docker

### 1. Environment Configuration
Create or update your `.env` file in the root directory (or update `docker-compose.yml`) with your Auth0 credentials:

```bash
# Auth0 Backend Config
AUTH0_ISSUER_URI=https://<your-tenant>.auth0.com/
AUTH0_AUDIENCE=https://api.vaccination-inventory.com

# Auth0 Frontend Config
VITE_AUTH0_DOMAIN=<your-tenant>.auth0.com
VITE_AUTH0_CLIENT_ID=<your-client-id>
VITE_AUTH0_AUDIENCE=https://api.vaccination-inventory.com

# App Config
ALLOWED_ORIGINS=http://localhost:5173
```

### 2. Start the Application
The entire ecosystem (Frontend, Backend, and Database) is orchestrated using Docker Compose.

```bash
docker-compose up --build
```

### 3. Access the Services
- **Frontend UI**: http://localhost:5173
- **API Endpoint**: http://localhost:8080/api
- **Swagger API**: http://localhost:8080/swagger-ui/index.html

---

## 💾 Database Management

The database automatically initializes with structural schema. Personal vaccination data is synchronized from Auth0 identities via the `/api/me` endpoint.

### Manual Data Initialization
To manually reload sample records into the running container:
```bash
docker exec -it dev-postgres psql -U user_test -d db_test -a -f sample.sql
```

---

## 📄 Documentation & Metadata
- **Metadata Endpoint**: `http://localhost:8080/v3/api-docs`
- **Identity Sync**: Upon login, the system automatically synchronizes Auth0 profiles to the local PostgreSQL database.

## TODO
- [ ] Implement RBAC (Role-Based Access Control) using Auth0 custom claims.
- [ ] Add unit and integration tests for the new JWT synchronization flow.
