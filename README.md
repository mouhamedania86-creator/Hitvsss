# MovieFlix
Netflix-style Android client — black + red. **By FaresCodeX**

## Features
- Splash screen
- Home browse + search
- Continue watching (local progress)
- My List
- Player: quality / audio / subs when available · PiP
- Offline list storage
- Watch stats in prefs
- minSdk 21 · targetSdk 34

## GitHub Actions
Push to main → **Build MovieFlix APK** → download artifact.

## Local
```bash
echo "sdk.dir=$ANDROID_HOME" > local.properties
gradle wrapper --gradle-version 8.7
./gradlew assembleRelease
```
