# 🎯 Quiz App – Android Application

## 📌 Project Overview

Quiz App is an interactive Android application developed using Kotlin. It allows users to participate in quizzes by scanning a QR code or manually selecting a category and difficulty level.

The application fetches questions from Open Trivia DB API, displays 10 multiple-choice questions, calculates scores automatically, and stores previous quiz results using Room Database.

## ✨ Features

- **Google Sign-In:** Secure user authentication using Firebase.
- **QR Code Scanner:** Join quizzes by scanning QR codes.
- **Manual Quiz Selection:** Choose category and difficulty manually.
- **Dynamic Questions:** Fetch questions from Open Trivia DB API.
- **Multiple Choice Questions:** 10 questions with four shuffled options.
- **Instant Feedback:** Correct answers are highlighted in green and incorrect answers in red.
- **Progress Bar:** Track quiz progress from 1 to 10.
- **Result Screen:** Display final score, correct answers, and percentage.
- **Play Again:** Restart quizzes with fresh questions.
- **Quiz History:** Store and view previous quiz attempts.
- **Local Storage:** Save quiz history using Room Database.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Android development |
| XML | UI design |
| Firebase Authentication | Google Sign-In |
| Google Code Scanner | QR scanning |
| Retrofit | API integration |
| Open Trivia DB | Quiz questions |
| Room Database | Local storage |
| RecyclerView | Quiz history |
| MVVM | Application architecture |

## 📱 Application Workflow

1. User logs in using Google Sign-In.
2. Home screen displays available quiz options.
3. User scans a QR code or selects a category manually.
4. Application retrieves category and difficulty.
5. Open Trivia DB API provides 10 questions.
6. User answers questions one by one.
7. Application validates answers and calculates the score.
8. Result screen displays the final performance.
9. Quiz results are stored in Room Database.
10. User can view previous quiz attempts.

## 📷 QR Code Format

The application supports the following QR code format:

```text
category=9;difficulty=easy
```

Examples:

```text
category=9;difficulty=easy
category=18;difficulty=medium
category=21;difficulty=hard
```

## 🌐 API Integration

**Open Trivia DB**

Fetch quiz categories:

https://opentdb.com/api_category.php

Fetch 10 multiple-choice questions:

```text
https://opentdb.com/api.php?amount=10&category={id}&difficulty={level}&type=multiple
```

No API key is required.

## 🔥 Firebase Setup

1. Create a project in Firebase Console.
2. Register your Android application.
3. Download `google-services.json`.
4. Add the file inside the app directory.
5. Enable Google Authentication.
6. Configure SHA-1 and SHA-256 fingerprints.
7. Sync Gradle and run the application.

Firebase Console: https://console.firebase.google.com/

## ⚙️ Installation and Setup

1. Clone this repository:

```bash
git clone https://github.com/USERNAME/Quiz-App.git
```

2. Open the project in Android Studio.

3. Add the Firebase configuration file (`google-services.json`).

4. Sync Gradle dependencies.

5. Start an Android Emulator or connect an Android device.

6. Click the Run button.

## 💾 Database

Room Database stores:

- User ID
- Category Name
- Difficulty Level
- Total Questions
- Correct Answers
- Incorrect Answers
- Final Score
- Percentage
- Date and Time

## 🎯 Future Enhancements

- Leaderboard and ranking system.
- Timer-based quizzes.
- Online multiplayer quizzes.
- Cloud-based score synchronization.
- Custom quiz creation.
- Detailed performance analytics.

## 👨‍💻 Developer

**Developed as an Android Application Development Project.**

## 📄 License

This project is intended for educational and learning purposes.

---

⭐ If you find this project useful, consider giving the repository a star!
