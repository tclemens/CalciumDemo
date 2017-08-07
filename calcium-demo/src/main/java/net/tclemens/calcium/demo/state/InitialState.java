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

package net.tclemens.calcium.demo.state;

import android.content.Context;
import android.support.annotation.NonNull;

import net.tclemens.calcium.engine.updates.base.Updatable;
import net.tclemens.calcium.engine.updates.event.Event;
import net.tclemens.calcium.engine.updates.event.ViewEvent;

/**
 * This class represents the initial state of the app
 *
 * @author Tim Clemens
 */
final class InitialState implements Updatable {

    InitialState() {
    }

    @NonNull
    @Override
    public Updatable update(@NonNull Context context, @NonNull Event event) {

        if (event instanceof ViewEvent) {

            ViewEvent view = (ViewEvent) event;

            return StateFactory.createSplash(context, view.getWidth(), view.getHeight());
        }

        return this;
    }
}
