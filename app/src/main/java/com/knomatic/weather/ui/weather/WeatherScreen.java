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
package com.knomatic.weather.ui.weather;

import java.util.List;

public class WeatherScreen {
  private final WeatherView view;
  private final WeatherSource source;

  public WeatherScreen(WeatherView view, WeatherSource source) {
    this.view = view;
    this.source = source;
  }

  public void request() {
    view.showLoading();
    source.weather(new WeatherSource.Callback() {
      @Override public void onFailure() {
        view.showRetry();
      }

      @Override public void onSuccess(WeatherHeader header, List<Weather> weathers) {
        view.renderWeather(header, weathers);
      }
    });
  }
}
