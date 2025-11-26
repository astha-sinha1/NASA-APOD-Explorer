# 🚀 NASA APOD Explorer

A simple full-stack project that lets you explore NASA’s **Astronomy Picture of the Day (APOD)** with:

- Dashboard (Today’s APOD)
- Date picker to view past APODs
- Gallery of recent APOD images
- Fully responsive UI
- Spring Boot backend with caching & NASA API integration


# Features

### Backend (Spring Boot)
- `/api/apod/today` – Today's APOD  
- `/api/apod/date?date=YYYY-MM-DD` – APOD for specific date  
- `/api/apod/recent?count=10` – Recent APOD images  
- NASA API integration using `RestTemplate`  
- Caching using Caffeine  
- Cache expiry (scheduler)  
- Timeout handling  
- Global exception handling  
- Future date validation  

### Frontend (HTML + CSS + JavaScript)
- Modern UI
- Fully responsive layout
- Image + explanation display
- Date picker
- Gallery grid
- Modal popup with:
  - Title
  - Date
  - Explanation
  - Copyright
- Smooth animations
- Error message handling


# Project Structure

```
nasa-apod-explorer/
│
├── backend/      # Spring Boot API
│   ├── src/
│   ├── pom.xml
│   ├── .mvn/
│   ├── mvnw
│   └── mvnw.cmd
│
└── frontend/     # HTML, CSS, JS UI
    ├── index.html
    ├── style.css
    └── app.js
```


# 🚀 How to Run the Project

## Run Backend (Spring Boot)

1. Go to backend folder  
   ```
   cd backend
   ```

2. Add your NASA API key in:
   ```
   src/main/resources/application.properties
   ```

   Example:
   ```
   nasa.api.key=YOUR_KEY_HERE
   nasa.api.apod.url=https://api.nasa.gov/planetary/apod
   server.port=8080
   ```

3. Run the project:
   ```
   mvn spring-boot:run
   ```

Your API will run at:

```
http://localhost:8080/api/apod/today
```

---

## Run Frontend

Open `frontend/index.html` in your browser  
(OR use Live Server / Python server / VSCode Live Server).

Frontend automatically calls:

```
http://localhost:8080/api/apod/...
```

---

# API Endpoints

| Endpoint                          | Description |
|-----------------------------------|-------------|
| `/api/apod/today`                 | Today’s APOD |
| `/api/apod/date?date=YYYY-MM-DD`  | APOD for given date |
| `/api/apod/recent?count=20`       | Recent APOD images (used for gallery) |



# Author  
Astha Sinha  
