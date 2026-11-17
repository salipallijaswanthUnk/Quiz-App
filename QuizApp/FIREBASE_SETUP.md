# Firebase setup (required)

1. Open https://console.firebase.google.com/ and create a Firebase project.
2. Add an Android app with package name `com.example.quizapp`.
3. Download the real `google-services.json` from Firebase and place it at `app/google-services.json`. Do not create a fake file or share this configuration publicly.
4. In Firebase Console > Authentication > Sign-in method, enable Google.
5. Add your debug SHA-1 and SHA-256 fingerprints in Project settings > Your apps. Get them from Android Studio Gradle `signingReport` task.
6. Download/refresh `google-services.json` after registering the app and adding fingerprints.
7. Sync Gradle and run. The generated resource `default_web_client_id` comes from the real Firebase configuration.

## QR payload format
Use plain text such as `category=9;difficulty=easy`. Category ID must exist in Open Trivia DB and the difficulty must be easy, medium, or hard.
