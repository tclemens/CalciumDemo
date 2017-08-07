/*
 * Copyright (C) 2017 Tim Clemens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tclemens.calcium.demo;

import android.app.Activity;
import android.os.Bundle;

import net.tclemens.calcium.engine.Engine;
import net.tclemens.calcium.engine.EngineFactory;
import net.tclemens.calcium.demo.state.StateFactory;

/**
 * This class represents the entry point of the app
 *
 * @author Tim Clemens
 */
public class MainActivity extends Activity {

    /** The engine for the app */
    private volatile Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        engine = EngineFactory.createEngine(this);

        setContentView(engine.getView());

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {

        engine.start(StateFactory.createInitial());

        super.onResume();
    }

    @Override
    protected void onPause() {

        engine.stop();

        super.onPause();
    }
}
