# NeoMart 

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
