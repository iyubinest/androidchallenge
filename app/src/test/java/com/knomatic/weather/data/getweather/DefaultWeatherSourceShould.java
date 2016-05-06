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
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultWeatherSourceShould {

  @Mock GPS gps;
  @Captor ArgumentCaptor<GPS.Callback> gpsCaptor;
  @Mock WeatherService service;
  @Captor ArgumentCaptor<WeatherService.Callback> serviceCaptor;
  @Mock WeatherCache cache;
  @Mock WeatherSource.Callback callback;
  private WeatherSource source;

  @Before public void before() {
    initMocks(this);
    source = new DefaultWeatherSource(gps, service, cache);
  }

  @Test public void send_failure_when_position_fails() {
    when(cache.getHeader()).thenReturn(null);
    when(cache.getWeather()).thenReturn(null);
    when(cache.isValid()).thenReturn(false);
    source.weather(callback);

    verify(gps, times(1)).locate(gpsCaptor.capture());
    gpsCaptor.getValue().onFailure();

    verify(callback, times(1)).onFailure();
  }

  @Test public void send_cache() {
    when(cache.getHeader()).thenReturn(mock(WeatherHeader.class));
    when(cache.getWeather()).thenReturn(mock(List.class));
    when(cache.isValid()).thenReturn(true);
    source.weather(callback);

    verify(callback, times(1)).onSuccess(any(WeatherHeader.class), anyListOf(Weather.class));
  }

  @Test public void send_failure_when_service_fails() {
    source.weather(callback);

    verify(gps, times(1)).locate(gpsCaptor.capture());
    gpsCaptor.getValue().onSuccess(0.0d, 0.0d);

    verify(service, times(1)).get(anyDouble(), anyDouble(), serviceCaptor.capture());
    serviceCaptor.getValue().onFailure();

    verify(callback, times(1)).onFailure();
  }

  @Test public void send_success_when_service_success() {
    source.weather(callback);

    verify(gps, times(1)).locate(gpsCaptor.capture());
    gpsCaptor.getValue().onSuccess(0.0d, 0.0d);

    verify(service, times(1)).get(anyDouble(), anyDouble(), serviceCaptor.capture());
    serviceCaptor.getValue().onSuccess(mock(WeatherHeader.class), mock(List.class));

    verify(callback, times(1)).onSuccess(any(WeatherHeader.class), anyListOf(Weather.class));
    verify(cache, times(1)).store(any(WeatherHeader.class), anyListOf(Weather.class));
  }
}
