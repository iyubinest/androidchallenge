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
package com.knomatic.weather;

import android.app.Application;
import com.knomatic.weather.di.AppComponent;
import com.knomatic.weather.di.AppModule;
import com.knomatic.weather.di.DaggerAppComponent;

public class App extends Application {
  private AppComponent injector;

  @Override public void onCreate() {
    super.onCreate();
    injector = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

  public AppComponent injector() {
    return injector;
  }
}
