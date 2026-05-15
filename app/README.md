# Rakta-Seva Connect 🩸

Rakta-Seva Connect is a healthcare-focused Android application designed to support emergency blood donor coordination at the local and taluka levels.

During medical emergencies, finding a specific blood group quickly is difficult because replacement donors are often not organized in a real-time, local, and secure manner. This system acts as a focused emergency alert platform that pushes requests to nearby registered donors of the required blood group, improving response speed during the critical golden hour.

## 🚀 Key Features

* **Role-Based Access Control (RBAC):** Distinct workflows and dashboards for Hospital Representatives and Blood Donors.
* **Smart Eligibility Engine:** Automatically calculates and enforces a strict 90-day cooldown period for donors after their last donation.
* **Proximity Filtering:** Integrates device GPS to strictly filter and alert only eligible donors who are within a 10 km radius of the hospital making the request.
* **Privacy-First Notifications:** Donor contact information is kept strictly confidential. The hospital only receives the donor's phone number *after* the donor explicitly accepts the emergency request via a push notification.
* **Cloud Sync:** Utilizes Firebase Cloud Firestore for real-time donor querying and emergency alert distribution.

## 🛠️ Technical Stack

* **Platform:** Android
* **Language:** Kotlin
* **Architecture:** MVVM (Model-View-ViewModel) with the Repository Pattern
* **Database:** Firebase Cloud Firestore
* **Location Services:** Google Play Services (FusedLocationProviderClient)
* **UI/UX:** XML-based imperative UI with standard Android Material Components

## 📂 Project Architecture Highlights

* `data/`: Contains the core data models (`Donor`, `Hospital`, `EmergencyRequest`) and Enums for strict input validation.
* `repository/`: Manages cloud database interactions and houses the 90-day eligibility logic.
* `viewmodel/`: Handles the business logic, including the multi-variable filtering algorithm (Blood Group + Eligibility + Location).
* `ui/`: Contains role-specific activities and the split-view Dashboard.
* `utils/`: Houses mathematical utility functions for calculating GPS distances.

## ⚙️ Local Setup & Installation

To run this project locally, you will need Android Studio and a Firebase account.

1. Clone the repository:
   ```bash
   git clone [https://github.com/yourusername/Rakta-Seva-Connect.git](https://github.com/yourusername/Rakta-Seva-Connect.git)