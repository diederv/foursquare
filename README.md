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


![App Icon](master/imgages/Screenshot_0.jpg?raw=true "App Icon")