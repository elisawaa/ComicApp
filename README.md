# ComicApp
An app to display and explore the various comics of xkcd.

## The requirements
- [X] browse through the comics
- [X] see the comic details, including its description
- [X] search for comics by the comic number as well as text
- [X] get the comic explanation
- [X] favorite the comics, which would be available offline too
- [X] support multiple form factors. (to a certain extent - should work nicely on tablets and "normal" phones)

Not considered "MVP": 
- [ ] send comics to others
- [ ] get notifications when a new comic is published

## Tech stack
- Kotlin
- Jetpack Compose
- Dagger/Hilt
- Retrofit
- Room DB
- Flow

## Screenshots
Taken in dark mode with dynamic colors enabled. 

| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
![image](https://user-images.githubusercontent.com/20892062/193409112-95967619-e194-4bd6-a069-7fb70fbec9de.png)|![image](https://user-images.githubusercontent.com/20892062/193409122-1be11eaa-acf3-4265-ad3c-541a19058677.png)|![image](https://user-images.githubusercontent.com/20892062/193409161-7ceb498d-7e9f-4166-97ca-d39b0a2d7420.png) |
|![image](https://user-images.githubusercontent.com/20892062/193409189-6848c5ad-1bf3-4dda-b925-a5136dc05cae.png)| ![image](https://user-images.githubusercontent.com/20892062/193409195-7fff0f48-474b-4a75-b123-3889d56d93a8.png) | ![image](https://user-images.githubusercontent.com/20892062/193409202-37ac5fab-8a4b-496c-87ac-2e5c67e4db19.png)|

## Known issues
- The initial loading of comics is slow, however, once all comics have been cached this is no longer a problem.
- Some navigation/bottom bar state issues (minor)

