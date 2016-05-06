package com.knomatic.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import com.knomatic.weather.ui.weather.WeatherScreen;
import com.knomatic.weather.ui.weather.WeatherSource;
import com.knomatic.weather.ui.weather.WeatherView;
import com.knomatic.weather.ui.weather.widget.WeatherWidget;
import java.util.List;
import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherActivityFragment extends BaseFragment implements WeatherView {

  private static final int PERMISSION_REQUEST_CODE = 100;
  @BindView(R.id.weather) WeatherWidget weatherView;
  @BindView(R.id.loading) View loadingView;
  @BindView(R.id.retry) View retryView;
  @Inject WeatherSource source;
  private WeatherScreen screen;

  public WeatherActivityFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_weather, container, false);
    ButterKnife.bind(this, view);
    injector().inject(this);
    screen = new WeatherScreen(this, source);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setRetainInstance(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (checkPermission()) {
        screen.request();
      } else {
        requestPermissions();
      }
    } else {
      screen.request();
    }
  }

  @Override public void showLoading() {
    show(loadingView);
  }

  @Override public void showRetry() {
    show(retryView);
  }

  @Override public void renderWeather(WeatherHeader header, List<Weather> weathers) {
    show((View) weatherView);
    weatherView.header(header);
    weatherView.weathers(weathers);
  }

  @OnClick(R.id.retry_button) public void retry() {
    screen.request();
  }

  private void show(View view) {
    loadingView.setVisibility(GONE);
    retryView.setVisibility(GONE);
    ((View) weatherView).setVisibility(GONE);
    view.setVisibility(VISIBLE);
  }

  @SuppressLint("NewApi") private void requestPermissions() {
    ActivityCompat.requestPermissions(getActivity(), new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    }, PERMISSION_REQUEST_CODE);
  }

  @SuppressLint("NewApi") private boolean checkPermission() {
    return ContextCompat.checkSelfPermission(getActivity(),
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(getActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PERMISSION_REQUEST_CODE
        && grantResults[0] == PackageManager.PERMISSION_GRANTED
        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
      screen.request();
    } else {
      Toast.makeText(getContext(), R.string.location_error, Toast.LENGTH_SHORT).show();
    }
  }
}
