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

import java.util.ArrayList;
import java.util.Collection;

import net.tclemens.calcium.demo.label.Label;
import net.tclemens.calcium.engine.graphics.base.Renderable;
import net.tclemens.calcium.engine.graphics.batch.Batch;
import net.tclemens.calcium.engine.graphics.camera.Camera;
import net.tclemens.calcium.engine.graphics.camera.CameraFactory;
import net.tclemens.calcium.engine.graphics.color.Color;
import net.tclemens.calcium.engine.graphics.color.ColorFactory;
import net.tclemens.calcium.engine.graphics.frame.Frame;
import net.tclemens.calcium.engine.graphics.frame.FrameFactory;
import net.tclemens.calcium.engine.graphics.scene.Scene;
import net.tclemens.calcium.engine.graphics.scene.SceneFactory;
import net.tclemens.calcium.engine.schedules.action.Action;
import net.tclemens.calcium.engine.schedules.action.ActionFactory;
import net.tclemens.calcium.engine.schedules.base.Schedulable;
import net.tclemens.calcium.engine.updates.base.Updatable;
import net.tclemens.calcium.engine.updates.event.ActionEvent;
import net.tclemens.calcium.engine.updates.event.Event;
import net.tclemens.calcium.math.matrix.Matrix3D;
import net.tclemens.calcium.math.matrix.MatrixFactory;
import net.tclemens.calcium.math.vector.Vector3D;
import net.tclemens.calcium.math.vector.VectorFactory;

/**
 * This class represents the app state with a splash message
 *
 * @author Tim Clemens
 */
final class SplashState implements Updatable, Schedulable, Renderable {

    /** The background color of the frame */
    private static final Color BACKGROUND = ColorFactory.createColor(0, 0, 0, 255);

    /** The name of the action used to transition this state to the next state */
    private static final String ACTION = "Start";

    /** The delay between the start of this state and the next state */
    private static final long DELAY = 2000L;

    /** The label to display */
    private final Label label;

    /** The width of the frame */
    private final int width;

    /** The height of the frame */
    private final int height;

    /**
     * @param label The label to display
     * @param width The width of the frame
     * @param height The height of the frame
     */
    SplashState(Label label, int width, int height) {

        this.label = label;
        this.width = width;
        this.height = height;
    }

    @NonNull
    @Override
    public Updatable update(@NonNull Context context, @NonNull Event event) {

        if (event instanceof ActionEvent) {

            ActionEvent action = (ActionEvent) event;

            if (ACTION.equals(action.getName())) {

                return StateFactory.createLeft(context, event.getTime(), width, height);
            }
        }

        return this;
    }

    @NonNull
    @Override
    public Action schedule() {

        long time = System.currentTimeMillis() + DELAY;

        return ActionFactory.createDelayed(ACTION, time);
    }

    @NonNull
    @Override
    public Frame render() {

        Collection<Scene> scenes = createScenes(label, width, height);

        return FrameFactory.createStatic(scenes, BACKGROUND, width, height);
    }

    /**
     * Create a collection of scenes from the specified label and frame dimensions
     *
     * @param label The label to display
     * @param width The width of the frame
     * @param height The height of the frame
     *
     * @return The collection of scenes
     */
    private static Collection<Scene> createScenes(Label label, int width, int height) {

        Camera camera = createCamera(width, height);
        Collection<Batch> batches = createBatches(label);
        Collection<Scene> scenes = new ArrayList<>(1);

        scenes.add(SceneFactory.createDynamic(camera, batches));

        return scenes;
    }

    /**
     * Create a camera with an aspect ratio for the specified frame dimensions
     *
     * @param width The specified width
     * @param height The specified height
     *
     * @return The camera
     */
    private static Camera createCamera(int width, int height) {

        Vector3D eye = VectorFactory.createPosition3D(0f, 0f, 5f);
        Vector3D center = VectorFactory.createPosition3D(0f, 0f, 0f);
        Vector3D up = VectorFactory.createPosition3D(0f, 1f, 0f);

        float h = 3f;
        float w = h * width / height;

        Matrix3D view = MatrixFactory.createView3D(eye, center, up);
        Matrix3D projection = MatrixFactory.createOrthographic3D(-w, w, -h, h, 0f, 6f);

        return CameraFactory.createStatic(projection, view);
    }

    /**
     * Create a collection of batches from the specified label
     *
     * @param label The label to display
     *
     * @return The collection of batches
     */
    private static Collection<Batch> createBatches(Label label) {

        Collection<Batch> batches = new ArrayList<>(1);

        batches.add(label.toBatch());

        return batches;
    }
}
