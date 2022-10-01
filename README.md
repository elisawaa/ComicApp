# ComicApp
An app to display and explore the various comics of xkcd. Worked on as a project with a deadline of <16h. 

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

## Description
The app consists of three main screens. One for viewing a list of comics, where the user can also search for a comic by filtering on the comics title, number (id) and transcript. The user can also open the second screen to see a specific comic. On the comic screen, the user can see the available details for the comic, generate a random comic or mark the comic as favorite. Some functionality to zoom and pan the image has also been added. The third screen holds the favorited comics, which can be unfavorited both from the favorite page and the comic page. 

## Thought process / highlighted points
The xkcd API does not (to the extent of my knowledge) provide a single endpoint for fetching all comics at once, which makes the fetching of comics slow as you have to individually request each comic from the backend. In order to avoid a slow fetching time each time the user opens the app, I decided to save all comics to a database for caching. This gives the added benefit of the user being able to browse, favorite and read comic transcripts while being offline! 

For this project I decided to focus on the basic functionality and learning new tech. This is my first time using Dagger/Hilt, Flow and Compose Navigation and thus it took some extra time setting up. However, it was fun trying something new! :) 

I ran out of time, considering how this project was on a deadline, so I wanted to prioritize functionality. Therefore, I have only included one basic test to ensure the bottom bar navigation works as expected. 

I have borrowed and adjusted some code for zooming/panning the comics, because I felt like it improved the user experience a lot - especially on larger comics. 

Also added basic Loading/Empty/Error states to improve UX.

## Screenshots
Taken in dark mode with dynamic colors enabled. 

| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
![image](https://user-images.githubusercontent.com/20892062/193409112-95967619-e194-4bd6-a069-7fb70fbec9de.png)|![image](https://user-images.githubusercontent.com/20892062/193409122-1be11eaa-acf3-4265-ad3c-541a19058677.png)|![image](https://user-images.githubusercontent.com/20892062/193409161-7ceb498d-7e9f-4166-97ca-d39b0a2d7420.png) |
|![image](https://user-images.githubusercontent.com/20892062/193409189-6848c5ad-1bf3-4dda-b925-a5136dc05cae.png)| ![image](https://user-images.githubusercontent.com/20892062/193409195-7fff0f48-474b-4a75-b123-3889d56d93a8.png) | ![image](https://user-images.githubusercontent.com/20892062/193409202-37ac5fab-8a4b-496c-87ac-2e5c67e4db19.png)|

## Known issues
- The initial loading of comics is slow, however, once all comics have been cached this is no longer a problem.
- Some navigation/bottom bar state issues (minor)

