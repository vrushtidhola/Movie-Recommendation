🎬 Movie-Recommendation – Full-Stack Application (React + Spring Boot)

A modern full-stack web application built using React (frontend) and Spring Boot (backend).
This project includes user authentication, movie management, reviews, and recommendation features.

movieverse-app/
│
├── frontend/                 # React UI
│   ├── src/
│   ├── public/
│   └── package.json
│
├── backend/                  # Spring Boot API
│   ├── src/
│   ├── pom.xml
│   └── ...
│
├── README.md
└── .gitignore

**🚀 Tech Stack
**
**Frontend**
React (Vite or CRA)
Material UI
Axios
React Router
Context API / Redux (optional)

**Backend (Spring Boot)
**
Spring Boot 3+
Spring Web
Spring Data JPA
Spring Security (if using auth)
MySQL / PostgreSQL

**🖥️ Frontend Setup (React)
**cd frontend
npm install
npm run dev


**Frontend runs at:
**
http://localhost:5173

**🛠️ Backend Setup (Spring Boot)
**
cd backend
mvn spring-boot:run


OR build JAR:

mvn clean install
java -jar target/*.jar


**Backend runs at:
**
http://localhost:8080
