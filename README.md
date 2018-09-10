## The Foursquare Coding Challenge

### The assignment: 
__Description__
Make use of the Foursquare API to build a native Android app that lets the user explore relevant venues nearby. You are free to choose any libraries as dependencies, but do not use any Foursquare API libraries. Decide yourself which aspects are most important to the user and make decisions about the focus and scope of your implementation.

__Requirements__
- Required: Get the user location and show available venues nearby 
- Optional: Allow the user to explore relevant venue details
- Optional: Give the user the ability to adjust some search parameters (e.g. location, radius, venue types)

### The implementation

#### MVVM
For this challenge I chose to use the [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture.
MVVM is part of [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/). Because of this we can find a lot of examples, guides and solutions to problems, which is always a good thing when developing an Android application :-)

#### Databinding 
The most powerful part of MVVM in android (in my opinion) is the __dataBinding__ aspect.
Note the following lines of code in the app gradle file that enables databinding:
```gradle
dataBinding {  
    enabled = true  
}
```
Another very nice aspect of MVVM is that the ViewModel Stores UI-related data that isn't destroyed on app rotations. The ViewModel has a lifecycle that is independent of the lifecycle of the views (Activities and Fragments). Because of this, the ViewModel is a good place to retrieve and to hold the UI related data.

In this challenge I created two different kinds of ViewModels
1. The ObservableViewModel that itself implements the interface Observable.
2. The AndroidViewModel that contains one or more Observable (public) fields.

I have a slight preference for the first (the ObservableViewModel) but I wanted to show both options, as I think it is a nice subject for discussion wether to use one or the other.

#### Retrofit and RXJava
The combination of [Retrofit](https://square.github.io/retrofit/) and RxJava for Android ([RxAndroid](https://github.com/ReactiveX/RxAndroid)) are a perfect match for MVVM and Databinding. The REST-API of foursquare can easily be connected to using Retrofit and the asynchronous handling of the results by RxAndroid will automatically be propagated to the UI (Views) by the databinding library while the ViewModel can remain agnostic to the existence of these Views.

#### Tools: Postman and jsonschema2pojo.org
The free (and online) tools [Postman](http://www.jsonschema2pojo.org) and [jsonschema2pojo.org](http://www.jsonschema2pojo.org) proved to be great tools to try-out REST-API's and to generate Pojo objects for the Retrofit integration.

After moving the generated Pojo files to the right folder in the Android Project, the following Interface will 'tell' Retrofit (almost) all it needs to know in order to connect to the REST-API:
```java
public interface FourSquareSearch {  
  
   @GET("/v2/venues/search")  
   Observable<VenuesListResponse> getVenuesByLocation(  
         @Query("v") String version,  
         @Query("client_id") String clientID,  
         @Query("client_secret") String clientSecret,  
		 @Query("ll") String latLng        
   );  
  
   @GET("/v2/venues/{venue_id}")  
   Observable<VenueDetailResponse> getVenueDetail(  
         @Path("venue_id") String venueID,  
         @Query("v") String version,  
         @Query("client_id") String clientID,  
         @Query("client_secret") String clientSecret  
   );  
}
```
#### The UI

__The App Icon__
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_0.jpg "App Icon")

__The Main Screen__
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_1.jpg "Main screen")

__Using 'Pull To Refresh'__
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_2.jpg "Pull to Refresh")

__Selecting a venue opens the detail screen__
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_3.jpg "Selecting a Venue")
Note: Error 429 shows up, indicating 'Too Many Requests'

Selecting the 'Settings Icon' on the top right
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_4.jpg "The Settings Icon")

The Settings screen, where the radius can be changed.
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_5.jpg "The Settings Screen")
Note: unfortunately the radius doesn't work with the endpoint that I selected for this app :-(
Foursquare: "Limit results to venues within this many meters of the specified location. Defaults to a city-wide area. Only valid for requests with intent=browse, or requests with intent=checkin and categoryId or query. Does not apply to intent=match requests. The maximum supported radius is currently 100,000 meters.""

Opening the Drawer by seleting the Home ('Hamburger') icon or by swiping from the left edge to the right..
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_6.jpg "The Drawer")

Pick an alternative Place with the Google Maps integration
![App Icon](https://raw.githubusercontent.com/diederv/foursquare/master/imgages/Screenshot_7.jpg "Pick an alternative Place")

#### Unfortunately

Unfortunately, considering the limited timeframe I had to implement this challenge, I wasn't able to:
- Create any test
- Optimize the 'Pull to Refresh' which often is 'in the way' to scroll back up
- To let the 'settings-radius' have any influence on the search results
- To fix the 429 http error for the Venbue detail-page by creating another account. (Did I really create too many requests!?)

Note:
I removed the following personal codes from the App:

- from the AndroidManifest.xml:        
```xml
<meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="{secret}"/>
```

- from res/values/string.xml

```xml
    <string name="foursquare_api_clientid">{secret}</string>
    <string name="foursquare_api_clientsecret">{secret}</string>
```

Please ask me for the values that make this app work! :-)

:-)
Regards,
Diederick Verweij