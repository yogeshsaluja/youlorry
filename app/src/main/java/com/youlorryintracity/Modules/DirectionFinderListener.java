package com.youlorryintracity.Modules;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Status;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();

    void onTokenRefresh();

    void onDirectionFinderSuccess(List<Route> route);

    void onResult(@NonNull Status status);
}
