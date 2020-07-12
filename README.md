
# Movie App  

  This app shows a list of top 5 rated movies of each year in specific genre  

<a href="https://ibb.co/VBRfFtX"><img src="https://i.ibb.co/88q1y0F/Screenshot-1578820066.png" alt="Screenshot-1578820066" width="200px" height="400px" border="0"></a> <a href="https://ibb.co/1XDpXrZ"><img src="https://i.ibb.co/94Ff4bn/Screenshot-1578820072.png" alt="Screenshot-1578820072" width="200px" height="400px" border="0"></a> <a href="https://ibb.co/xMshqCJ"><img src="https://i.ibb.co/9qvnH8s/Screenshot-1578820096.png" alt="Screenshot-1578820096" width="200px" height="400px" border="0"></a> <a href="https://ibb.co/R67GJf9"><img src="https://i.ibb.co/gW6bcN3/Screenshot-1578820130.png" alt="Screenshot-1578820130" width="200px" height="400px" border="0"></a>

## Getting Started  
  
*To get this project up and running on your local machine for development and testing purposes.* <li> Make sure all prerequisties are met  
<li> Clone this project on your machine by running  

     git clone https://github.com/ohefny/CleanMovies.git   

Or you can simply download the code from here    https://github.com/ohefny/CleanMovies/archive/master.zip 
<li> Open the project on AndroidStudio  
<li> Attach your device via (usb or wifi) or start an emulator   
<li> Select the device from the menu   
<li> Click the run button   

![enter image description here](https://i.ibb.co/Y7zh8b8/running-app.png)

### Prerequisites  
  
-   JDK 1.8
-  Android Studio
-   [Android SDK](https://developer.android.com/studio/index.html)
-   Android 10 ([API 29](https://developer.android.com/preview/api-overview.html))
-   Latest Android SDK Tools and build tools.  

  ### Languages, libraries and tools used

-   [Kotlin](https://kotlinlang.org/)
-   [Room](https://developer.android.com/topic/libraries/architecture/room.html)
-   [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
-   [RxJava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0)
-   [Dagger2](https://github.com/google/dagger)
-   [Glide](https://github.com/bumptech/glide)
-   [Retrofit](http://square.github.io/retrofit/)
-   [OkHttp](http://square.github.io/okhttp/)
-   [Gson](https://github.com/google/gson)
-   [Timber](https://github.com/JakeWharton/timber)
-   [Mockito](http://site.mockito.org/)

### Architecture
![enter image description here](https://i.ibb.co/zZy6J7Q/celan-arch-mvvm-arch.png)  

### Deployment  
  
To generate a release APK version of the app  please follow these steps
<li> First get a key for flickr images to work  [https://www.flickr.com/services/apps/create/]
<li>  Make sure Keytool is added to your path  or run it from your JRE bin folder
<li> Navigate to desired destination of the keys inisde the project prefarly "../app/keys "
<li> Generate your signing key by running this 

```  
keytool -genkey -v -keystore release_key.keystore -alias {alias_name} -storepass {store_pass} -keypass {key_pass} -keyalg RSA -validity 36500
```  
Replace {alias_name} , {store_pass} and {key_pass} with the desired values
<li> Add these values inside your local.properties

```
flickr.key = "{flickr_key}"
release.storePassword =  {store_pass}
release.keyAlias ={alias_name}
release.keyPassword ={key_pass}
release.keyPath= {key_path/release_key.keystore}
```
{key_path} :: relative to your app folder
 
<li> Run this command inside your project folder 

``` ./gradlew assembleRelease ```
<li> Navigate to app/build/outputs/apk/release


### Running the tests  

   ```./gradlew test```

### Downloads 
Download release APK from releases tab or https://github.com/ohefny/cleanmovies/releases/download/v1.0/app-release.apk
