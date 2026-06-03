# CapoCoin App

## Overview

CapoCoin is an Android application built using Jetpack Compose, Room Database, and MVVM architecture.  
The app allows users to register, log in, and manage financial data such as transactions and categories.

---
[Youtube Link] (https://youtu.be/BmEcXMdU0PY)


## Features

- User registration stored in Supabase  
- Login system with validation  
- Input validation   
- Navigation using Jetpack Compose  
- Local data persistence using Room Database  
- MVVM architecture (ViewModel, DAO, Database)
- Log Transactions
- PhotoPicker Image upload
- In app calculator
- Custom Category Creation
- Filtering transaction by user selectable period
- Filtering Category Totals by user selecteable period
- View transctions uploaded image 
- Top and bottom navigation for ease-of-use
- Budget Progress tracker
- Analytical graph to track user habits
- Achievement system

---

## Architecture

The project follows the MVVM pattern:

UI ->ViewModel -> DAO -> Room Database local storage

---

## Prerequisite

- Android Studio
- Emulator or android device (min SDK 24)

---

## Setup Instructions

1. Clone the repository

2. Open the project in Android Studio

3. Allow Gradle to sync

4. Run the application using an emulator or physical device

---

## Supabase Database

The application uses Supabase Database for storing user data.

Components used:
- Data table
- Image bucket
- User Email Authentication



---
## Custom Features
- Built-in calculator to use within the app
- User Achievement tracking on User Profile to track what user has progress

---

## ChangeLog 

-- Part 2
- Menus different
- App customization features dropped
- Calculator screen layout
- User variable First and Last Name Combined into Name
- Category screen layout
- Welcome screen dropped

--Part 3
- Removed XP Bar
- Removed Level
- Removed Profile Title
- Adjusted doubleAmount variable to replace comma with decimal for validation checking
- Added Supabase Integration for Transactions
- Image is uploaded to bucket storage
- Added app icon
- Added graphs

