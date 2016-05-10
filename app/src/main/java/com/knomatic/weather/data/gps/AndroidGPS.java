/**
 * Copyright (C) 2015 Cristian Gómez Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.knomatic.weather.data.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

@SuppressWarnings("MissingPermission") public final class AndroidGPS implements GPS {
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
