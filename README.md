# Description
Payoneer take home task which requires parsing of data from an API and displaying the data in a list without the use of external libraries (retrofit , rxjava and dagger)


# Architecture  🏗
This app uses MVVM (Model View View-Model) architecture with a single activity and two fragments(HomeFragment and DetailsFragment) and a repository pattern to access data.  

# App Flow
The app gets the data from the provided API using HttpUrlConection in the NetworkUtils class that gets the data as an inputstream which is then parsed / mapped using the Gson library to the respective model classes. The API call is made asynchronously using CompletableFuture (i.e supplyAsync() method) to make the call off the main thread. The state and data are handled by a Resource class which is observed from the viewmodel by UI via LiveData, updating the UI as the state changes (i.e hiding / showing the progress bar , showing toast messages based on the input validation result of the edit text input). 


# Built With  🛠	
* Java
* JUnit
* Espresso
* LiveData
* Viewmodel
* CompletableFuture (making non-blocking asynchronous api call)
* Gson

# App Screens UI 🎨

|     App Start    |    App Active        | 
| ------------- |:----------:| 
| ![](https://github.com/oscarnipps/payoneer-test/blob/ce6e23e5f0ffd370f425936f1b02524e85645484/item1.gif)      | ![](https://github.com/oscarnipps/payoneer-test/blob/ce6e23e5f0ffd370f425936f1b02524e85645484/item2.gif)| 
