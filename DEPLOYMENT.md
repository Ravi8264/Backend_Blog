# Blog API Deployment Guide

## Free Tier Deployment Options

### 1. Railway (Recommended)

**Steps:**
1. Push your code to GitHub
2. Go to [Railway.app](https://railway.app)
3. Click "Deploy from GitHub repo"
4. Select your repository
5. Railway will automatically detect the Java project and deploy

**Environment Variables to Set:**
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=(Railway will provide this automatically)
JWT_SECRET=your-very-secure-jwt-secret-key-here
ADMIN_USERNAME=your-admin-username
ADMIN_PASSWORD=your-secure-admin-password
CORS_ORIGINS=https://your-frontend-domain.com
```

**Database:** Railway provides free PostgreSQL (500MB limit)

### 2. Render

**Steps:**
1. Push your code to GitHub
2. Go to [Render.com](https://render.com)
3. Create a new "Web Service"
4. Connect your GitHub repository
5. Use the provided `render.yaml` configuration

**Environment Variables:**
Same as Railway above.

**Database:** Render provides free PostgreSQL (1GB limit, expires after 90 days)

### 3. Heroku (Limited Free Tier)

**Steps:**
1. Install Heroku CLI
2. `heroku create your-app-name`
3. `heroku addons:create heroku-postgresql:mini`
4. Set environment variables with `heroku config:set`
5. `git push heroku main`

## Important Security Notes

⚠️ **MUST DO BEFORE PRODUCTION:**

1. **Change JWT Secret:**
   ```
   JWT_SECRET=your-very-long-secure-random-string-at-least-256-bits
   ```

2. **Change Admin Credentials:**
   ```
   ADMIN_USERNAME=your-secure-admin-username
   ADMIN_PASSWORD=your-very-secure-password
   ```

3. **Set CORS Origins:**
   ```
   CORS_ORIGINS=https://your-frontend-domain.com,https://your-app.com
   ```

## API Documentation

Once deployed, your Swagger documentation will be available at:
- `https://your-app-url.com/swagger-ui.html`
- API docs: `https://your-app-url.com/v3/api-docs`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Posts
- `GET /api/posts` - Get all posts (paginated)
- `POST /api/user/{userId}/category/{categoryId}/posts` - Create post
- `PUT /api/posts/{postId}` - Update post
- `DELETE /api/posts/{postId}` - Delete post
- `GET /api/posts/{postId}` - Get post by ID
- `GET /api/posts/search/{keyword}` - Search posts

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{categoryId}` - Update category
- `DELETE /api/categories/{categoryId}` - Delete category

### Comments
- `POST /api/post/{postId}/comments` - Add comment
- `DELETE /api/comments/{commentId}` - Delete comment

### File Upload
- `POST /api/post/image/upload/{postId}` - Upload post image

## Testing the Deployment

1. Test health: `GET https://your-app-url.com/actuator/health`
2. Test API docs: `https://your-app-url.com/swagger-ui.html`
3. Test login: `POST https://your-app-url.com/api/auth/login`

## Troubleshooting

### Common Issues:

1. **Database Connection Issues:**
   - Check DATABASE_URL environment variable
   - Ensure PostgreSQL addon is provisioned

2. **Memory Issues:**
   - Apps are configured with 512MB heap limit for free tiers
   - Monitor memory usage in platform dashboards

3. **Build Failures:**
   - Ensure Java 21 is being used
   - Check build logs for Maven errors

4. **Authentication Issues:**
   - Verify JWT_SECRET is set
   - Check admin credentials

### Monitoring:
- Railway: Check deployment logs in dashboard
- Render: Monitor via Render dashboard
- Use application logs to debug issues

## Free Tier Limitations

- **Railway:** 500MB storage, $5/month credit
- **Render:** 750 hours/month, 1GB PostgreSQL (90 days)
- **Heroku:** 550-1000 dyno hours/month

Choose based on your needs and preferences!