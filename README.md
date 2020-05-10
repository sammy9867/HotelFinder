# Hotel Finder
Hotel Finder is a native mobile application that is built using the MVVM architecture in Kotlin. It helps the user to find the cheapest hotels near him, view details of each hotel and adjust his preferences.
<p align="center">
  <img src="recording/GIF-200510_211120.gif" width=200 hspace="5" />
  <img src="recording/GIF-200510_211403.gif" width="200" hspace="5" /> 
  <img src="recording/GIF-200510_211715.gif" width="200" hspace="5"/>
</p>

## Getting Started
The following instructions will help you to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
* Make sure you created an account at [RapidAPI](https://rapidapi.com) and signed up for TripAdvisor API basic plan (500 free api calls/month) [here](https://rapidapi.com/apidojo/api/tripadvisor1).
* After signing up, you will receive ***X-RapidAPI-Host*** and ***X-RapidAPI-Key***, which you will require in this project.

### Installing

* To clone this repository, you need to have [GIT](https://git-scm.com) installed on your local machine.
* Paste the following on the command line:
```
$ git clone https://github.com/sammy9867/HotelFinder.git
```

### Deployment
* After you have cloned the repository, navigate to *Constants.kt*  in the *util* package and enter your ***X-RapidAPI-Host*** and ***X-RapidAPI-Key***.
```
class Constants{

    companion object{

        const val RAPID_API_HOST = "Your RAPID API HOST"
        const val RAPID_API_KEY = "Your RAPID API KEY"
    }
}
```
* You can then build and run the project and deploy the APK file either on an android emulator or your android device.

## Built With
* **Navigation component** to navigate between multiple fragments within one activity.
* **Retrofit** to convert HTTP API into a Kotlin interface.
* **Kotlin Coroutines** for async tasks.
* **Room** for local persistent storage.
* **AndroidX lifecycle** that provides ViewModel and LiveData classes to build lifecycle-aware
components.
* **Material Design** to style the UI components.
* **Glide** to display images received from the server.

## Contributing
All pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Author
* **Samuel Menezes**
