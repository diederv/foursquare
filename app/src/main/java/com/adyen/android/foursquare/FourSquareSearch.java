package com.adyen.android.foursquare;

import com.adyen.android.foursquare.detail.VenueDetailResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FourSquareSearch {

	@GET("/v2/venues/search")
	Observable<VenuesListResponse> getVenues(
			@Query("v") String version,
			@Query("client_id") String clientID,
			@Query("client_secret") String clientSecret,
			@Query("query") String placeType,
			@Query("near") String near
	);

	@GET("/v2/venues/search")
	Observable<VenuesListResponse> getVenuesByLocation(
			@Query("v") String version,
			@Query("client_id") String clientID,
			@Query("client_secret") String clientSecret,
			//@Query("query") String placeType,
			@Query("ll") String latLng,
			@Query("radius") String radius
	);

	@GET("/v2/venues/{venue_id}")
	Observable<VenueDetailResponse> getVenueDetail(
			@Path("venue_id") String venueID,
			@Query("v") String version,
			@Query("client_id") String clientID,
			@Query("client_secret") String clientSecret
	);
}
