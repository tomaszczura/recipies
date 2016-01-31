/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.czura.recipes.mvp.presenters;

import android.os.Bundle;

import com.czura.recipes.mvp.views.View;

public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView(View v);

    void onCreate(Bundle savedInstanceState);
}
