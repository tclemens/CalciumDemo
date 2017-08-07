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

import net.tclemens.calcium.demo.box.Box;
import net.tclemens.calcium.demo.label.Label;
import net.tclemens.calcium.engine.updates.base.Updatable;
import net.tclemens.calcium.engine.updates.event.AnimationEvent;
import net.tclemens.calcium.engine.updates.event.Event;
import net.tclemens.calcium.engine.updates.event.TouchEvent;
import net.tclemens.calcium.engine.updates.input.Touch;

/**
 * This class represents an app state with a box moving to the right
 *
 * @author Tim Clemens
 */
final class RightState extends MovingState {

    /**
     * @param box The box to display
     * @param label The label to display
     * @param width The width of the frame
     * @param height The height of the frame
     */
    RightState(Box box, Label label, int width, int height) {

        super(box, label, width, height);
    }

    @NonNull
    @Override
    public Updatable update(@NonNull Context context, @NonNull Event event) {

        if (event instanceof TouchEvent) {

            TouchEvent touch = (TouchEvent) event;

            if (touch.getInput() == Touch.UP) {

                return StateFactory.createRight(context, event.getTime(), getWidth(), getHeight());
            }
        }

        if (event instanceof AnimationEvent) {

            return StateFactory.createLeft(context, event.getTime(), getWidth(), getHeight());
        }

        return this;
    }
}
