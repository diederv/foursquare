package com.adyen.android.foursquare;

import android.content.res.Resources;

import com.adyen.android.foursquare.detail.VenueDetailResponse;
import com.adyen.android.R;
import com.google.android.gms.location.places.Place;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoursquareServiceFactory {

	private static Retrofit mRetrofit;
	private static FourSquareSearch fourSquareSearch;

	private static Retrofit getRetrofit(String baseUrl) {
		if (mRetrofit == null) {
			FoursquareServiceFactory.mRetrofit =
					new Retrofit.Builder().
							baseUrl(baseUrl).
							addConverterFactory(GsonConverterFactory.create()).
							addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
							build();
			FoursquareServiceFactory.fourSquareSearch = mRetrofit.create(FourSquareSearch.class);
		}
		return mRetrofit;
	}

	private static FourSquareSearch getFourSquareSearch(String baseUrl) {
		FoursquareServiceFactory.getRetrofit(baseUrl);
		return FoursquareServiceFactory.fourSquareSearch;
	}

	public static Observable<VenuesListResponse> getVenuesByLocation(LatLng location, int radius, Resources res) {

		// ll=52.3461151,4.9950819
		// &client_id=AQRXCYJIWOZJ1MAZTAYSWHISHH3NYFWETBD1CKCBHBMB4IWM
		// &client_secret=B0JCT4GLZK0XCNELV5JZP1RQ0KZLAUDVXL3PBMZ2ZL3QXXT
		// K&v=20180906

		return
				getFourSquareSearch(
					res.getString(R.string.foursquare_api_baseUrl)
				).getVenuesByLocation(
					res.getString(R.string.foursquare_api_version),
					res.getString(R.string.foursquare_api_clientid),
					res.getString(R.string.foursquare_api_clientsecret),
					location.getValue(),
					Integer.toString(radius)
				);
	}

	public static Observable<VenueDetailResponse> getVenueDetail(String venueId, Resources res) {
		return
				getFourSquareSearch(
						res.getString(R.string.foursquare_api_baseUrl)
				).getVenueDetail(
						venueId,
						res.getString(R.string.foursquare_api_version),
						res.getString(R.string.foursquare_api_clientid),
						res.getString(R.string.foursquare_api_clientsecret)
				);
	}

	public static class LatLng {

		private final String value;

		public LatLng(android.location.Location location) {
			value = Double.toString(location.getLatitude())+ ","+ Double.toString(location.getLongitude());
		}

		public LatLng(Place place) {
			value = place.getLatLng().latitude + "," + place.getLatLng().longitude;
		}

		public LatLng(String val) {
			value = val;
		}

		public String getValue() {
			return value;
		}
	}
}
