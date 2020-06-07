# codetest-gumtree

### Summary ###

* __What was my apporach?__
    - Architecture  
    I have used Google's MVVM architecture with Jetpack. For background work I have used Kotlin Coroutines. For caching data I have used Room database.
    - UI  
    I have completed all the functionality requirements of the challenge. This includes search by GPS, city name, zip code, maintain search history and delete search history.
    - Progress Tracking
    I created a Trello board, broke down the app in small chunks and filled the board with tasks before I started working on it. It helped me keep track of what's done and what's left to do.
    https://trello.com/b/yWtiSHJV/gumtree-code-challenge

* __What 3rd party libraries did I use and why?__
    - Retrofit (https://github.com/square/retrofit)  
    I used Retrofit mainly for its integration of Coroutines. It is easy to setup and use coroutines out of the box, instead of setting up coroutines to work with Callback in other network libraries.
    
    - Stetho (https://github.com/facebook/stetho)  
    Stetho is useful for debugging Room database. You can inspect the database in Chrome browser via chrome://inspect.
    
* __Tests__
    - I have written two Instrumentation tests. The MainFragmentTest runs some UI tests, while the DatabaseTest runs some tests on the database functionality.
    - The UI test checks search functionality with a city name and zip code. There is also a test for search history functionality however I had trouble with it. I have left a comment in the file which will make more sense than explaining here.
    - I did not get enough time to write test for GPS functionality.
    
* __Any known issues?__
    - I noticed an intermittent issue with receiving last known location. The app keeps waiting for user's location, which for strange reason does not update even if you turn off Location and on again. It happened only few times to me. In a real app I would definitely investigate and fix this issue.
    - I have not come across any crashes. 

* __What would I do if I had more time?__
    - Add test for GPS functionality.
    - Add unit tests.
    - Add more comments to the code.

### Repo owner ###

Ali Kazi   
[LinkedIn](linkedin.com/in/mdalikazi)  
