# NeoMart 

A microservices-based eCommerce platform built with Spring Boot and React.

## 🔑 Core Microservices

- **auth-service**: Traditional email/password login with JWT
- **keycloak**: Handles Google SSO via OpenID Connect
- **user-service**: User profiles, shipping addresses, etc.
- **product-service**: Product listing, search (integrated with Elasticsearch)
- **cart-service**: Cart operations stored in Redis
- **order-service**: Order placement, order history
- **payment-service**: Dummy payment flow 
- **review-service**: Ratings and product reviews

## 🔀 Supporting Services

- **discovery-service**: Eureka server for service registration
- **api-gateway**: Single entry point to all backend APIs

## 📦 Infrastructure & Tooling

- **Redis**: Fast cart data storage and caching
- **Elasticsearch**: Full-text search for products
- **Kibana + Logstash (ELK)**: Logging and error tracking
- **Prometheus + Grafana**: Monitoring, alerts, service health
- **Docker & Docker Compose**: Local dev container orchestration
- **Kubernetes (K8s)**: Future deployment on Minikube / GKE
- **GitHub Actions**: CI/CD automation

## 📲 Frontend

- **React**: NeoMart eCommerce user interface

## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose
- Java 21
- Gradle

### Running the Application

1. **Start all services using Docker Compose:**
   ```bash
   docker-compose up -d
   ```

2. **Access the services:**
   - API Gateway: http://localhost:8080
   - Auth Service: http://localhost:8082
   - Product Service: http://localhost:8081
   - PostgreSQL: localhost:5432
   - SonarQube: http://localhost:9000

3. **Test the APIs:**
   ```bash
   # Register a new user
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","password":"password123","email":"test@example.com","firstName":"Test","lastName":"User"}'
   
   # Login with username/password
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","password":"password123"}'
   
   # Google OAuth2 login (redirect to browser)
   curl http://localhost:8080/oauth2/authorization/google
   
   # Get all products (requires authentication)
   curl http://localhost:8080/api/products \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
   ```

### Development

1. **Build individual services:**
   ```bash
   # Auth Service
   cd auth-service && ./gradlew build
   
   # Product Service
   cd product-service && ./gradlew build
   
   # API Gateway
   cd api-gateway && ./gradlew build
   ```

2. **Run services locally:**
   ```bash
   # Start PostgreSQL first
   docker-compose up postgres -d
   
   # Run Auth Service
   cd auth-service && ./gradlew bootRun
   
   # Run Product Service
   cd product-service && ./gradlew bootRun
   
   # Run API Gateway
   cd api-gateway && ./gradlew bootRun
   ```

## 📁 Project Structure

```
NeoMart/
├── api-gateway/          # Spring Cloud Gateway
├── auth-service/         # Authentication service (JWT + OAuth2)
├── product-service/      # Product management service
├── docker-compose.yml    # Container orchestration
└── docs/                # Documentation
```
