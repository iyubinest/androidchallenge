package com.knomatic.weather.ui.weather;

import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import com.knomatic.weather.ui.weather.WeatherScreen;
import com.knomatic.weather.ui.weather.WeatherSource;
import com.knomatic.weather.ui.weather.WeatherView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class WeatherScreenShould {
  @Mock WeatherView view;
  @Mock WeatherSource source;
  @Captor ArgumentCaptor<WeatherSource.Callback> captor;
  private WeatherScreen screen;

  @Before public void before() {
    initMocks(this);
    screen = new WeatherScreen(view, source);
  }

  @Test public void show_loading_at_request() {
    screen.request();
    verify(view, times(1)).showLoading();
  }

  @Test public void show_retry_on_failure() {
    screen.request();

    //throw the listener manually
    verify(source, times(1)).weather(captor.capture());
    captor.getValue().onFailure();

    verify(view, times(1)).showRetry();
  }

  @Test public void show_weather_list_on_success() {
    screen.request();

    //throw the listener manually
    verify(source, times(1)).weather(captor.capture());
    captor.getValue().onSuccess(any(WeatherHeader.class), anyListOf(Weather.class));

    verify(view, times(1)).renderWeather(any(WeatherHeader.class), anyListOf(Weather.class));
  }
}