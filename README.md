**Brazilian Government Expenditures**

This project interacts with the [Brazilian Government's Public API](https://api.portaldatransparencia.gov.br/api-de-dados) (a.k.a. Portal da Transparência) to fetch data about public expenditures. Expenditure can be shown specific for an organ (such as ministries, chamber of deputies, etc.) or categorized by different government branches such as Executive, Legislative, and Judiciary. 

This is a personal project to practice software architecture patterns, full-stack development, and is not intended for production or as a formal system, but a way to explore and practice various technologies.

**1st version (08/24/2025)** :
<img width="1492" height="790" alt="BR Gov Expenditures Dashboard" src="https://github.com/user-attachments/assets/eb12d648-7ea4-4433-8db2-3b08aa288dcd" />

**Features**
 - Developed in Spring Boot (Java) for the backend, and React.js for the frontend;
 - Fetches data from the Brazilian Government's API to show public spending;
 - Categorizes expenditures by government branches: Executive, Legislative, and Judiciary;
 - Uses Redis for caching API calls to minimize the load on the external service and improve performance;
 - Can be run on-premise or containerized using Docker.

**Tech Stack**
 - Backend: Spring Boot (Java)
 - Frontend: React.js
 - Caching: Redis (using Lettuce)
 - Containerization: Docker
 - Database: MySQL

**Requirements**
 - A gov.br API token that can be obtained [here](https://portaldatransparencia.gov.br/api-de-dados/cadastrar-email);
 - Java 17+;
 - Node.js and npm (for React app)
 - Docker (optional for containerized setup)
 - Redis (for caching)

