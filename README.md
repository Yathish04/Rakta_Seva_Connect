# Rakta-Seva Connect 🩸

Rakta-Seva Connect is a healthcare-focused Android application designed to bridge the critical gap during medical emergencies by providing real-time, proximity-based blood donor coordination and privacy-first alerts.

During emergencies, finding a specific blood group quickly is difficult because replacement donors are often not organized in a real-time, local manner. This system acts as a highly focused emergency alert platform that pushes requests to nearby registered donors, strictly filtering them by blood group, a 90-day cooldown eligibility rule, and a 10 km GPS radius.

## 🚀 Core Features

* **Role-Based Access Control (RBAC):** Distinct registration workflows and dynamic dashboard UI switching for Hospital Representatives and Blood Donors.
* **Smart Eligibility Engine:** Automatically enforces a strict 90-day cooldown period for donors. Ineligible donors are programmatically hidden from emergency queries.
* **Real-Time Proximity Filtering:** Integrates Google Play Location Services to capture hospital GPS coordinates and strictly filter donors within a 10 km radius using local Haversine math (`LocationUtils`).
* **Privacy-First "Two-Way Reveal":** Donor contact information is kept strictly confidential. The hospital only receives the donor's phone number *after* the donor explicitly accepts the emergency request, and vice versa.
* **Zero-Config Offline Mode:** Features a custom `RepositoryProvider` that allows the app to run perfectly using local Mock Data for easy GitHub cloning and testing, without needing Firebase credentials.

## 🛠️ Technical Stack & Architecture

* **Platform:** Android (Minimum SDK 24+)
* **Language:** Kotlin
* **Architecture:** MVVM (Model-View-ViewModel) + Interface-Driven Repository Pattern
* **Database:** Firebase Cloud Firestore (Real-time NoSQL)
* **Location:** Google Play Services Location API (`FusedLocationProviderClient`)
* **UI Design:** XML Imperative UI with Material standard components.

### 📂 Clean Architecture Implementation
This project utilizes a scalable architecture by abstracting the database layer. 
The ViewModels communicate solely with the `DonorRepository` interface, which is fulfilled by either the `FirebaseDonorRepository` or the `MockDonorRepository`.

## ⚙️ How to Run & Test Locally

This repository is built with developer experience in mind. You can run it instantly without setting up cloud databases.
