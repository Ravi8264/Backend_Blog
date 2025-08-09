# Backend Blog API

A comprehensive Spring Boot REST API for a blog application with JWT authentication, refresh tokens, and CRUD operations.

## 🚀 Features

- **User Authentication**: JWT-based login/register with refresh token support
- **Blog Management**: Create, read, update, delete posts
- **Category Management**: Organize posts by categories  
- **Comment System**: Users can comment on posts
- **File Upload**: Image upload functionality for posts
- **Role-based Authorization**: Admin and User roles
- **Security**: Spring Security with JWT tokens (1-hour expiry)
- **Database**: PostgreSQL with Hibernate ORM

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Build Tool**: Maven
- **Java Version**: 21

## 📚 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/refresh` - Refresh access token
- `POST /api/v1/auth/logout` - User logout

### Posts
- `GET /api/posts` - Get all posts (paginated)
- `GET /api/posts/{id}` - Get post by ID
- `POST /api/user/{userId}/category/{categoryId}/posts` - Create post
- `PUT /api/posts/{id}` - Update post (owner/admin only)
- `DELETE /api/posts/{id}` - Delete post (owner/admin only)
- `GET /api/posts/search/{keyword}` - Search posts

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Comments
- `GET /api/comments/post/{postId}/comments` - Get post comments
- `POST /api/comments/post/{postId}/comments` - Add comment
- `DELETE /api/comments/{id}` - Delete comment

## 🔧 Local Development

### Prerequisites
- Java 21
- PostgreSQL
- Maven

### Setup
1. Clone the repository
```bash
git clone https://github.com/Ravi8264/Backend_Blog.git
cd Backend_Blog
```

2. Configure database in `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Blog
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run the application
```bash
./mvnw spring-boot:run
```

## 🚀 Deployment

### Zeabur Deployment
This project is configured for easy deployment on Zeabur:

1. Fork this repository
2. Connect to Zeabur
3. Add PostgreSQL service
4. Set environment variables:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`
5. Deploy!

### Environment Variables (Production)
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=your_database_url
DATABASE_USERNAME=your_db_user
DATABASE_PASSWORD=your_db_password
PORT=8080
FILE_UPLOAD_PATH=/tmp/uploads/
```

## 📖 API Documentation

Swagger UI is available at: `/swagger-ui.html`

## 🔐 Authentication Flow

1. **Register/Login**: Get access token (1 hour) + refresh token (7 days)
2. **API Calls**: Use access token in Authorization header
3. **Token Refresh**: Use refresh token to get new access token
4. **Authorization**: Role-based access control for sensitive operations

## 🏗️ Project Structure

```
src/main/java/com/blog/blog/
├── controller/          # REST Controllers
├── entities/           # JPA Entities
├── service/            # Business Logic
├── repository/         # Data Access Layer
├── security/           # Security Configuration
├── jwt/               # JWT Utilities
└── payloads/          # DTOs and Request/Response objects
```

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Developer

**Ravi Kumar**
- GitHub: [@Ravi8264](https://github.com/Ravi8264)

---

⭐ Star this repository if you find it helpful!
