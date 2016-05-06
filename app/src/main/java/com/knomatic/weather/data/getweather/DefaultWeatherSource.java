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
package com.knomatic.weather.data.getweather;

import com.knomatic.weather.data.cache.WeatherCache;
import com.knomatic.weather.data.gps.GPS;
import com.knomatic.weather.data.weather.WeatherService;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import com.knomatic.weather.ui.weather.WeatherSource;
import java.util.List;

public class DefaultWeatherSource implements WeatherSource {
  private final GPS gps;
  private final WeatherService service;
  private final WeatherCache cache;
  private Callback callback;

  public DefaultWeatherSource(GPS gps, WeatherService service, WeatherCache cache) {
    this.gps = gps;
    this.service = service;
    this.cache = cache;
  }

  @Override public void weather(Callback callback) {
    this.callback = callback;
    if (cache.isValid()) {
      callback.onSuccess(cache.getHeader(), cache.getWeather());
    } else {
      locate();
    }
  }

  private void locate() {
    gps.locate(new GPS.Callback() {
      @Override public void onFailure() {
        callback.onFailure();
      }

      @Override public void onSuccess(double lat, double lon) {
        weathers(lat, lon);
      }
    });
  }

  private void weathers(double lat, double lon) {
    service.get(lat, lon, new WeatherService.Callback() {
      @Override public void onFailure() {
        callback.onFailure();
      }

      @Override public void onSuccess(WeatherHeader header, List<Weather> weathers) {
        cache.store(header, weathers);
        callback.onSuccess(header, weathers);
      }
    });
  }
}
