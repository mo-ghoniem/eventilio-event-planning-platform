# 🎉 Eventify  
### *Plan. Organize. Celebrate — effortlessly.*

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue?logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-green?logo=mongodb&logoColor=white)
![Keycloak](https://img.shields.io/badge/Keycloak-Security-critical?logo=keycloak&logoColor=white)
![Build](https://img.shields.io/badge/Build-Passing-success?logo=githubactions&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?logo=github)

---

## 🏰 Overview

**Eventify** is a **Spring Boot microservices application** that simplifies planning weddings and special events.  
It empowers users to manage guest lists, track RSVPs, and connect with a curated network of trusted vendors — all through a unified, secure, and resilient platform.

---

## 🚀 Features

- 🎟️ **Guest Management:** Create and manage guest lists, send invitations, and track RSVPs in real time.  
- 🏢 **Vendor Marketplace:** Explore and connect with event vendors (venues, catering, florists, DJs, etc.).  
- ⚡ **Smart Data Integration:** Django scraper aggregates vendor data from **158+ websites** across **11 categories** in seconds.  
- 🔐 **Authentication & Authorization:** Powered by **Keycloak** for secure, seamless login and role-based access.  
- 🔍 **Service Discovery:** Managed by **Eureka Discovery Server** for dynamic service registration.  
- 🌐 **API Gateway:** Central routing and load balancing across microservices.  
- 💪 **Fault Tolerance:** **Resilience4j** circuit breakers and fallback mechanisms for high availability.  
- 🧩 **Hybrid Databases:** Combines **PostgreSQL** (relational) and **MongoDB** (NoSQL) for maximum flexibility.  

---

## 🧱 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Java 17, Spring Boot (Microservices Architecture) |
| **Security** | Keycloak (OAuth2, OpenID Connect) |
| **Service Discovery** | Eureka Discovery Server |
| **API Gateway** | Spring Cloud Gateway |
| **Database** | PostgreSQL, MongoDB |
| **Resilience** | Resilience4j |
| **Data Integration** | Django (Python) Web Scraper |
| **Containerization** | Docker, Docker Compose |

---

## 🧩 Microservices Architecture

Eventify is built on a modular, distributed architecture with the following core services:

- 🧑‍💼 **User Service** – Handles registration, authentication, and user profile management.  
- 💌 **Guest Service** – Manages guest lists, invitations, and RSVP tracking.  
- 🏪 **Vendor Service** – Fetches and organizes vendor data from external sources.  
- 🚪 **Gateway Service** – Routes all client requests securely between services.  
- 🛰️ **Discovery Server** – Central registry for service discovery and load balancing.  

---

## 🔐 Security

- Integrated **Keycloak** for centralized authentication and authorization.  
- Supports **JWT-based** secure communication between services.  
- Implements **role-based access control (RBAC)** for admins, planners, and guests.  

---

## ⚙️ Resilience & Fault Tolerance

- ⚡ **Circuit Breakers:** Using **Resilience4j** to isolate failing services.  
- 🔁 **Retry & Fallback Mechanisms:** Maintain stability during network issues.  
- 🧠 **Graceful Degradation:** Ensures uptime even during partial failures.  

---

## 🗄️ Databases

- 🐘 **PostgreSQL:** For structured relational data (users, guests, RSVPs).  
- 🍃 **MongoDB:** For dynamic vendor and event data scraped from external sites.  

---

## 🕸️ Vendor Data Scraper

A **Django-based scraper** integrates directly with Eventify to fetch and update vendor data automatically.

- Scrapes **158+ vendor pages** in ~10 seconds.  
- Classifies vendors into **11 event categories** (venues, catering, florists, DJs, etc.).  
- Syncs data with the **Vendor Service** database in real time.  

---

## 🐳 Deployment

All microservices are containerized and orchestrated with **Docker Compose** for easy setup and scalability.

```bash
docker-compose up --build
