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
package com.knomatic.weather.data.weather;

import android.support.annotation.NonNull;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.TimeZone;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class RetrofitServiceShould {

  private final MockWebServer server = new MockWebServer();
  private RetrofitService service;
  @Mock WeatherService.Callback callback;

  @Before public void before() throws Exception {
    initMocks(this);
    server.start();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(server.url("/").toString())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    service = new RetrofitService(retrofit);
  }

  @After public void after() throws Exception {
    server.shutdown();
  }

  @Test public void error_on_network() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(500));
    service.get(0.0d, 0.0d, callback);
    waitResponse();
    verify(callback, times(1)).onFailure();
  }

  @Test public void success_items() throws Exception {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(body()));
    service.get(0.0d, 0.0d, callback);
    waitResponse();
    verify(callback, times(1)).onSuccess(any(WeatherHeader.class), anyListOf(Weather.class));
  }

  @Test public void day_name_is_right() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    assertThat(service.dayName(new Date(0)), is("THU"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24)), is("FRI"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 2)), is("SAT"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 3)), is("SUN"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 4)), is("MON"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 5)), is("TUE"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 6)), is("WED"));
    assertThat(service.dayName(new Date(1000 * 60 * 60 * 24 * 7)), is("THU"));
  }

  @NonNull private String body() throws Exception {
    InputStream stream = getClass().getResourceAsStream("/weather.json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line;
    StringBuilder builder = new StringBuilder("");
    while ((line = reader.readLine()) != null) {
      builder.append(line);
    }
    return builder.toString();
  }

  private void waitResponse() throws Exception {
    Thread.sleep(200);
  }
}
