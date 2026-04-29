# CapoCoin App

CapoCoin is an Android application built using Jetpack Compose, Room Database, and MVVM architecture.  
The app allows users to register, log in, and manage financial data such as transactions and categories.

---

## Features

- User registration stored in RoomDB  
- Login system with validation  
- Input validation   
- Navigation using Jetpack Compose  
- Local data persistence using Room Database  
- MVVM architecture (ViewModel, DAO, Database)
- Log Transactions
- PhotoPicker Image upload
- In app calculator
- Custom Category Creation
- Filtering transaction by user selectable peroid
- View transctions uploaded image 
- Multiple screens (Home, Transactions, Analytics, etc.)

---

## Architecture

The project follows the MVVM pattern:

UI ->ViewModel -> DAO -> Room Database local storage

---

## Prerequisite

- Android Studio
- Emulator (min SDK 24)

---

## Setup Instructions

1. Clone the repository

2. Open the project in Android Studio

3. Allow Gradle to sync

4. Run the application using an emulator or physical device

---

## Room Database

The application uses Room Database for storing user data.

Components used:
- User entity  
- UserDAO
- Category entity
- CategoryDAO
- Transaction entity
- TransactionDAO 
- AppDatabase singleton instance

---

## ChangeLog 



