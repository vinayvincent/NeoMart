# Authentication API Documentation

## Overview
The NeoMart Authentication Service provides secure user authentication with support for:
- Username/password authentication
- Google OAuth2 SSO
- Microsoft OAuth2 SSO
- JWT token-based authorization

## Base URL
```
http://localhost:8082/api/auth
```

## Endpoints

### 1. User Registration
**POST** `/register`

Register a new user with username and password.

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }
}
```

### 2. User Login
**POST** `/login`

Authenticate user with username and password.

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "securePassword123"
}
```

**Response:** Same as registration response.

### 3. Google OAuth2 Login
**GET** `/oauth2/authorization/google`

Redirects to Google OAuth2 authorization page.

### 4. Microsoft OAuth2 Login
**GET** `/oauth2/authorization/microsoft`

Redirects to Microsoft OAuth2 authorization page.

### 5. OAuth2 Success Callback
**GET** `/oauth2/success?provider={google|microsoft}`

Internal endpoint called after successful OAuth2 authentication.

### 6. Logout
**POST** `/logout`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response:**
```json
{
  "message": "Logged out successfully"
}
```

## Error Responses

### Validation Errors (400)
```json
{
  "username": "Username is required",
  "email": "Email should be valid"
}
```

### Authentication Errors (401)
```json
{
  "error": "Invalid username or password"
}
```

### General Errors (400)
```json
{
  "error": "Username already exists"
}
```

## Authentication Flow

### Username/Password Flow
1. User submits credentials to `/login` or `/register`
2. Server validates credentials
3. Server returns JWT tokens
4. Client stores tokens and includes access token in subsequent requests

### OAuth2 Flow
1. Client redirects to `/oauth2/authorization/{provider}`
2. User authenticates with OAuth2 provider
3. Provider redirects back to application
4. Server creates/links user account
5. Server returns JWT tokens

## Security Features

- **Password Hashing:** BCrypt with salt
- **JWT Tokens:** HS256 algorithm with configurable expiration
- **Account Lockout:** After failed login attempts
- **Email Verification:** For new registrations
- **Audit Logging:** Security events tracking
- **Token Blacklisting:** Revoked token management

## Environment Variables

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/neomart_db
SPRING_DATASOURCE_USERNAME=neomart_user
SPRING_DATASOURCE_PASSWORD=neomart_pass

# JWT
JWT_SECRET=your-super-secret-jwt-key-change-in-production

# Google OAuth2
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

# Microsoft OAuth2
MICROSOFT_CLIENT_ID=your-microsoft-client-id
MICROSOFT_CLIENT_SECRET=your-microsoft-client-secret
```

## Setup Instructions

### 1. Google OAuth2 Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing
3. Enable Google+ API
4. Create OAuth2 credentials
5. Add authorized redirect URI: `http://localhost:8082/login/oauth2/code/google`

### 2. Microsoft OAuth2 Setup
1. Go to [Azure Portal](https://portal.azure.com/)
2. Register a new application in Azure AD
3. Add redirect URI: `http://localhost:8082/login/oauth2/code/microsoft`
4. Generate client secret
5. Configure API permissions for User.Read

### 3. Database Setup
Run the SQL scripts from `docs/database-schema.sql` to create the required tables.

### 4. Running the Service
```bash
# Using Docker Compose
docker-compose up auth-service

# Or locally
cd auth-service
./gradlew bootRun
```