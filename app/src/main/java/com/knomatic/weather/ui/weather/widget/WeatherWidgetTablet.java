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
package com.knomatic.weather.ui.weather.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.knomatic.weather.R;
import com.knomatic.weather.ui.ImageLoader;
import com.knomatic.weather.ui.weather.Weather;
import com.knomatic.weather.ui.weather.WeatherHeader;
import java.util.List;

public class WeatherWidgetTablet extends RelativeLayout implements WeatherWidget {

  private ImageView iconView;
  private ImageView backgroundView;
  private TextView dayView;
  private TextView temperatureView;
  private TextView summaryView;
  private RecyclerView weatherView;
  private Adapter adapter;

  public WeatherWidgetTablet(Context context) {
    super(context);
    init();
  }

  public WeatherWidgetTablet(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WeatherWidgetTablet(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public WeatherWidgetTablet(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.weather_tablet, this, false);
    addView(view);
    iconView = (ImageView) view.findViewById(R.id.weather_icon);
    backgroundView = (ImageView) view.findViewById(R.id.weather_background);
    dayView = (TextView) view.findViewById(R.id.weather_day);
    temperatureView = (TextView) view.findViewById(R.id.weather_temperature);
    summaryView = (TextView) view.findViewById(R.id.weather_summary);
    weatherView = (RecyclerView) view.findViewById(R.id.weathers);
    weatherView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.grids)));
    weatherView.setHasFixedSize(false);
    adapter = new Adapter();
    weatherView.setAdapter(adapter);
  }

  @Override public void header(WeatherHeader header) {
    iconView.setImageResource(header.icon());
    dayView.setText(header.date());
    temperatureView.setText(header.temperature());
    summaryView.setText(header.summary());
    ImageLoader.load(backgroundView, header.urlImage());
  }

  @Override public void weathers(List<Weather> weathers) {
    adapter.weathers(weathers);
  }

  private class Adapter extends RecyclerView.Adapter<Holder> {

    private List<Weather> weathers;

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new Holder(
          LayoutInflater.from(getContext()).inflate(R.layout.weather_item, parent, false));
    }

    @Override public void onBindViewHolder(Holder holder, int position) {
      holder.weather(weathers.get(position));
    }

    @Override public int getItemCount() {
      if (weathers == null) return 0;
      return weathers.size();
    }

    public void weathers(List<Weather> weathers) {
      this.weathers = weathers;
      notifyDataSetChanged();
    }
  }

  private class Holder extends RecyclerView.ViewHolder {

    private final ImageView iconView;
    private final TextView dayView;
    private final TextView temperatureView;

    public Holder(View itemView) {
      super(itemView);
      iconView = (ImageView) itemView.findViewById(R.id.weather_icon);
      dayView = (TextView) itemView.findViewById(R.id.weather_day);
      temperatureView = (TextView) itemView.findViewById(R.id.weather_temperature);
    }

    public void weather(Weather weather) {
      iconView.setImageResource(weather.icon());
      dayView.setText(weather.day());
      temperatureView.setText(weather.temperature());
    }
  }
}
