**Brazilian Government Expenses**

This project interacts with the [Brazilian Government's Public API](https://api.portaldatransparencia.gov.br/api-de-dados) (a.k.a. Portal da Transparência) to fetch data about public expenditures. Expenses can be shown specific for an organ (such as ministries, chamber of deputies, etc.) or categorized by different government branches such as Executive, Legislative, and Judiciary. 

This is a personal project to practice software architecture patterns, full-stack development, and is not intended for production or as a formal system, but a way to explore and practice various technologies.

**1st version (08/20/2025)** :
<img width="1471" height="799" alt="BR Gov Expenses Dashboard" src="https://github.com/user-attachments/assets/5df5a1d8-7748-492e-95c3-63ee3cc45cc2" />

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

