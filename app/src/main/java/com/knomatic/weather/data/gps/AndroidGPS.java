/*************************************************************************
 * CONFIDENTIAL
 * __________________
 *
 * [2016] All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of {The Company} and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to {The Company}
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from {The Company}.
 */
package com.knomatic.weather.data.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

@SuppressWarnings("MissingPermission") public class AndroidGPS implements GPS {
  private final LocationManager service;
  private Callback callback;

  private final LocationListener listener = new LocationListener() {
    @Override public void onLocationChanged(Location location) {
      callback.onSuccess(location.getLatitude(), location.getLongitude());
      service.removeUpdates(listener);
    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override public void onProviderEnabled(String provider) {

    }

    @Override public void onProviderDisabled(String provider) {
      callback.onFailure();
    }
  };

  public AndroidGPS(Context context) {
    service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  @Override public void locate(Callback callback) {
    this.callback = callback;
    Location location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location != null && location.getLatitude() != 0d && location.getLongitude() != 0d) {
      callback.onSuccess(location.getLatitude(), location.getLongitude());
      service.removeUpdates(listener);
    } else {
      service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, listener);
    }
  }
}
