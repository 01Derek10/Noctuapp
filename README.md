# Noctuapp - Platform for Nightlife Opportunities

A comprehensive Android application designed to connect workers with nightlife venues (clubs, bars, discotheques) and help them discover job opportunities in the entertainment industry. Noctuapp features both a mobile app and a backend API system for seamless management of companies, job offers, and user interactions.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![PHP](https://img.shields.io/badge/PHP-777BB4?logo=php&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

## Features

### Mobile Application (Android)
- **Company Discovery**: Browse nightlife venues with detailed information, location maps, and categorization by music genre/type
- **Job Opportunities**: View available job offers from connected companies
- **User Authentication**: Secure login and registration system
- **Company Details**: View company descriptions, location maps, offers, and ratings
- **Tag-Based Filtering**: Filter companies by music genre and type (Reggaeton, Trap, House, Pop, etc.)
- **Offline Support**: Core functionality available offline with local caching

### Backend API (PHP)
- **Company Management**: Get list of all companies with filtering capabilities
- **Offer Management**: Retrieve job offers and search offers by company
- **User Management**: User registration, login, and profile management
- **Location-Based Queries**: Filter companies by location and tags
- **RESTful Endpoints**: Clean API for mobile app communication

### Database (MySQL)
- **Companies Table**: Store company information with images, descriptions, locations
- **Offers Table**: Job opportunities linked to companies
- **Users Table**: User authentication and profiles
- **Tags System**: Classification and filtering capabilities

## Installation

### Prerequisites
- **Android Studio** 4.0 or higher
- **Android SDK** 21 (Android 5.0) or higher
- **Kotlin** 1.9+
- **Gradle** 7.0+
- **PHP** 8.0+ (for backend)
- **MySQL** 5.7+ (for database)

### Android App Setup

1. Clone the repository:
```bash
git clone https://github.com/01Derek10/Noctuapp.git
cd Noctuapp
```

2. Open the project in Android Studio:
   - File → Open → Select the project folder
   - Let Gradle sync automatically

3. Configure backend URL:
   - Update API base URL in `app/src/main/java/config/ApiConfig.kt`
   - Set your backend server address

4. Build and run:
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or press `Shift + F10`

### Backend Setup

1. Upload PHP files to your web server:
```bash
# Copy all PHP files to your web server
cp *.php /var/www/html/noctuapp/
```

2. Database setup:
```bash
# Import the SQL database
mysql -u root -p database_name < noctua_final.sql
```

3. Configure database connection in PHP files:
```php
// config/Database.php
$host = "localhost";
$user = "root";
$password = "your_password";
$database = "noctua";
```

## Project Structure

```
Noctuapp/
├── app/                              # Android application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/               # Kotlin source code
│   │   │   │   ├── activities/     # UI Activities
│   │   │   │   ├── models/         # Data models
│   │   │   │   ├── api/            # API client
│   │   │   │   └── utils/          # Utility classes
│   │   │   └── res/                # Resources (layouts, strings, etc.)
│   │   ├── test/                   # Unit tests
│   │   └── androidTest/            # Instrumented tests
│   └── build.gradle.kts            # Gradle build configuration
│
├── backend/                         # PHP Backend (if included)
│   ├── get_empresas.php            # Get all companies
│   ├── get_ofertas.php             # Get all job offers
│   ├── get_ofertas_where.php       # Filter offers by company
│   ├── get_ofertas_where_tags.php  # Filter offers by tags
│   ├── get_user.php                # Get user data
│   ├── login.php                   # User login endpoint
│   ├── register.php                # User registration
│   ├── update.php                  # Update user profile
│   ├── lugares.php                 # Get locations
│   └── config/                     # Database configuration
│
├── database/                        # Database files
│   ├── noctua_final.sql            # Complete database schema
│   └── noctua (2).sql              # Backup database
│
├── build.gradle.kts                # Project-level gradle config
├── settings.gradle.kts             # Gradle settings
├── .gitignore                      # Git ignore rules
└── README.md                       # This file

```

## Technologies Used

### Frontend (Mobile)
- **Language**: Kotlin
- **Platform**: Android (API 21+)
- **Build Tool**: Gradle 7.0+
- **Networking**: Retrofit/OkHttp (or native HttpURLConnection)
- **JSON**: Gson or Kotlinx Serialization
- **UI Components**: Material Design 3
- **Concurrency**: Coroutines

### Backend
- **Language**: PHP 8.0+
- **Database**: MySQL 5.7+
- **Authentication**: Token-based or Session-based
- **API Format**: RESTful JSON
- **Server**: Apache or Nginx

### Database
- **RDBMS**: MySQL
- **Tables**: empresas, ofertas, users
- **Relationships**: Foreign keys for data integrity
- **Charset**: UTF-8MB4 for full Unicode support

## API Endpoints

### Companies
```
GET /get_empresas.php
Response: Array of all companies with details, images, and tags
```

### Offers
```
GET /get_ofertas.php
Response: Array of all job offers

GET /get_ofertas_where.php?idEmpresa={id}
Response: Offers filtered by company

GET /get_ofertas_where_tags.php?tags={tag1,tag2}
Response: Offers filtered by tags
```

### User Management
```
POST /register.php
Body: { username, password, email, edad, apellidos }
Response: Registration success/error

POST /login.php
Body: { username, password }
Response: User data with authentication token

GET /get_user.php?userId={id}
Response: User profile data

POST /update.php
Body: { userId, field, value }
Response: Update confirmation
```

### Locations
```
GET /lugares.php
Response: Array of company locations and map data
```

## Usage

### Mobile App
1. **Launch the app** on your Android device
2. **Create an account** or login with existing credentials
3. **Browse companies** using the company listing screen
4. **View details** by clicking on a company card
5. **Search for jobs** in the offers section
6. **Filter** by location, music genre, or tags
7. **Save favorites** (if implemented)
8. **Contact companies** for job inquiries

### Backend API
Make HTTP requests to retrieve data:

**Get all companies:**
```bash
curl -X GET "https://your-backend.com/get_empresas.php"
```

**Login user:**
```bash
curl -X POST "https://your-backend.com/login.php" \
  -H "Content-Type: application/json" \
  -d '{"username":"ivan","password":"4321"}'
```

## Database Schema

### Empresas (Companies)
- `id`: Unique identifier
- `nombre`: Company name
- `ubicacion`: Physical location
- `map`: Google Maps URL
- `tags`: Genre/type classification (Reggaeton, Trap, House, Pop, etc.)
- `ofertas`: Number of active offers
- `descripcion`: Company description
- `imagen`: Company logo/image (BLOB)

### Ofertas (Offers)
- `id`: Unique identifier
- `idEmpresa`: Foreign key to company
- `titulo`: Job title
- `descripcion`: Job description
- `salario`: Salary range
- `fechaCreacion`: Offer creation date
- `estado`: Status (active/inactive)

### Users
- `id`: Unique identifier
- `username`: Login username
- `password`: Hashed password
- `email`: User email
- `edad`: User age
- `apellidos`: User surname
- `descripcion`: User profile description

## Development

### Building the APK
```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires signing key)
./gradlew assembleRelease
```

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests on device
./gradlew connectedAndroidTest
```

### Code Quality
- Use Kotlin style guide conventions
- Follow Material Design guidelines
- Implement proper error handling
- Add null safety checks

## Deployment

### Android App
1. Build signed release APK in Android Studio
2. Upload to Google Play Store or distribute directly
3. Test on multiple devices before release

### Backend
1. Deploy PHP files to production server
2. Configure database with production credentials
3. Set up SSL/TLS for secure connections
4. Implement rate limiting and input validation
5. Set up monitoring and logging

## Configuration

### Backend Configuration
Update these files with your server details:

```php
// config/database.php
define('DB_HOST', 'your_host');
define('DB_USER', 'your_user');
define('DB_PASS', 'your_password');
define('DB_NAME', 'noctua');
```

### Android Configuration
Update API base URL:

```kotlin
// config/ApiConfig.kt
const val BASE_URL = "https://your-backend.com/"
```

## Security Considerations

- Implement HTTPS for all API communications
- Hash user passwords with bcrypt or similar
- Use token-based authentication (JWT recommended)
- Validate all user inputs on backend
- Implement CORS properly on backend
- Use ProGuard/R8 for Android release builds
- Store sensitive data in Android Keystore
- Implement rate limiting on API endpoints

## Troubleshooting

### Android App Won't Build
- Clear cache: `./gradlew clean`
- Update Android SDK
- Check Java version compatibility

### Backend Connection Issues
- Verify server is running
- Check API endpoint URLs
- Verify database connection credentials
- Check firewall and network settings

### Database Errors
- Verify MySQL is running
- Check database encoding (UTF-8MB4)
- Verify table relationships and foreign keys
- Check database user permissions

## Future Enhancements

- [ ] Offline-first architecture with local database
- [ ] Push notifications for new job offers
- [ ] User favorites and bookmarking
- [ ] Company ratings and reviews
- [ ] Direct messaging between users and companies
- [ ] Job application tracking system
- [ ] Analytics dashboard
- [ ] Advanced filtering and recommendations
- [ ] Multi-language support
- [ ] Dark mode theme

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support & Contact

For support, questions, or feedback:
- **Email**: derekboultonarevalo@gmail.com
- **GitHub Issues**: [GitHub Repository Issues](https://github.com/01Derek10/Noctuapp/issues)
- **Author**: [@01Derek10](https://github.com/01Derek10)

## Acknowledgments

- Android development community
- Material Design principles
- Open-source libraries and frameworks
- All contributors and testers

---

**Version**: 1.0.0  
**Last Updated**: 2024  
**Status**: Active Development

Made with ❤️ by [@01Derek10](https://github.com/01Derek10)