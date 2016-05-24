/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emergencyescape.commonbehaviour;

import android.os.Bundle;
import android.os.Parcelable;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;

/**
 * @author Hannes Dorfmann
 */
public abstract class CommonBehaviourViewState<V extends MvpView> implements RestorableViewState<V> {

  // TODO: dove salvare la partenza e la destinazione per ora, alla fine dovranno essere passati al model in modo che la funzione di calcolo potrà andarli a prendere

  private final String KEY_DEPARTURE = "Departure-data";
  private final String KEY_DESTINATION = "Destination-data";

  public String departure;
  public String destination;

  @Override public void saveInstanceState(Bundle out) {
    out.putString(KEY_DEPARTURE, departure);
    out.putString(KEY_DESTINATION, destination);
  }

  @Override public RestorableViewState restoreInstanceState(Bundle in) {
    if (in == null) {
      return null;
    }

    departure = in.getString(KEY_DEPARTURE);
    destination = in.getString(KEY_DESTINATION);

    return this;
  }

  @Override public void apply(V view, boolean retained) {

  }



}
