package com.whileimout.utilities;

import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.whileimout.models.Location;

public class GoogleMaps {

	public static void getGoogleMapBitmap(int width, int height,
			Location location, ImageView imageView) {
		String urlLocation = location.getURLEscapedLocation();
		String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center="
				+ urlLocation
				+ "&zoom=19&size="
				+ height
				+ "x"
				+ width
				+ "&scale=2&maptype=satellite&markers=color:blue%7Clabel:A%7C"
				+ urlLocation + "&sensor=false";
		UrlImageViewHelper.setUrlDrawable(imageView, imageUrl, null,
				UrlImageViewHelper.CACHE_DURATION_INFINITE);
	}

}
