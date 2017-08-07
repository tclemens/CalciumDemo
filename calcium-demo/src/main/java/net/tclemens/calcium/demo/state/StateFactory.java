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

import net.tclemens.calcium.demo.box.Box;
import net.tclemens.calcium.demo.box.BoxFactory;
import net.tclemens.calcium.demo.label.Label;
import net.tclemens.calcium.demo.label.LabelFactory;
import net.tclemens.calcium.engine.graphics.animation.Animation;
import net.tclemens.calcium.engine.graphics.animation.AnimationFactory;
import net.tclemens.calcium.engine.graphics.animation.interpolation.Interpolation;
import net.tclemens.calcium.engine.graphics.animation.interpolation.InterpolationFactory;
import net.tclemens.calcium.engine.graphics.animation.transformation.Transformation;
import net.tclemens.calcium.engine.graphics.animation.transformation.TransformationFactory;
import net.tclemens.calcium.engine.graphics.color.Color;
import net.tclemens.calcium.engine.graphics.color.ColorFactory;
import net.tclemens.calcium.engine.updates.base.Updatable;
import net.tclemens.calcium.math.matrix.Matrix3D;
import net.tclemens.calcium.math.matrix.MatrixFactory;
import net.tclemens.calcium.math.vector.Vector3D;
import net.tclemens.calcium.math.vector.VectorFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * This class is responsible for creating and initializing states
 *
 * @author Tim Clemens
 */
public final class StateFactory {

    /** A position at the center of the screen */
    private static final Vector3D CENTER = VectorFactory.createPosition3D(0f, 0f, 0f);

    /** A position at the bottom of the screen */
    private static final Vector3D BOTTOM = VectorFactory.createPosition3D(0f, -2f, 0f);

    /** The random number generator used to generate colors */
    private static final Random RANDOM = new Random();

    /** The duration of each moving state */
    private static final long DURATION = 2000L;

    private StateFactory() {
    }

    /**
     * Create an initial state
     *
     * @return The state
     */
    public static Updatable createInitial() {

        return new InitialState();
    }

    /**
     * Create a splash state
     *
     * @param context The application context
     * @param width The frame width
     * @param height The frame height
     *
     * @return The state
     */
    public static Updatable createSplash(Context context, int width, int height) {

        Color color = createRandomColor();
        Label label = LabelFactory.createLabel(CENTER, context, color, "Hello");

        return new SplashState(label, width, height);
    }

    /**
     * Create a left moving state
     *
     * @param context The application context
     * @param start The start time
     * @param width The frame width
     * @param height The frame height
     *
     * @return The state
     */
    public static Updatable createLeft(Context context, long start, int width, int height) {

        Animation animation = createLeftAnimation(start);
        Color color = createRandomColor();
        Box box = BoxFactory.createBox(CENTER, animation, color);
        Label label = LabelFactory.createLabel(BOTTOM, context, color, "Left");

        return new LeftState(box, label, width, height);
    }

    /**
     * Create a right moving state
     *
     * @param context The application context
     * @param start The start time
     * @param width The frame width
     * @param height The frame height
     *
     * @return The state
     */
    public static Updatable createRight(Context context, long start, int width, int height) {

        Animation animation = createRightAnimation(start);
        Color color = createRandomColor();
        Box box = BoxFactory.createBox(CENTER, animation, color);
        Label label = LabelFactory.createLabel(BOTTOM, context, color, "Right");

        return new RightState(box, label, width, height);
    }

    /**
     * Create the animation for a left moving state
     *
     * @param start The start time
     *
     * @return The animation
     */
    private static Animation createLeftAnimation(long start) {

        Collection<Animation> animations = new ArrayList<>(2);

        animations.add(createFirstAnimation(1f, 0f));
        animations.add(createSecondAnimation(start, -2f, 0f));

        return AnimationFactory.createComposite(animations);
    }

    /**
     * Create the animation for a right moving state
     *
     * @param start The start time
     *
     * @return The animation
     */
    private static Animation createRightAnimation(long start) {

        Collection<Animation> animations = new ArrayList<>(2);

        animations.add(createFirstAnimation(-1f, 0f));
        animations.add(createSecondAnimation(start, 2f, 0f));

        return AnimationFactory.createComposite(animations);
    }

    /**
     * Create an animation to perform an immediate translation
     *
     * @param x The <tt>x</tt> offset of the animation
     * @param y The <tt>y</tt> offset of the animation
     *
     * @return The animation
     */
    private static Animation createFirstAnimation(float x, float y) {

        Matrix3D matrix = MatrixFactory.createTranslate3D(x, y, 0f);

        return AnimationFactory.createStatic(matrix);
    }

    /**
     * Create an animation to perform a translation over time
     *
     * @param start The start time
     * @param x The <tt>x</tt> offset of the animation
     * @param y The <tt>y</tt> offset of the animation
     *
     * @return The animation
     */
    private static Animation createSecondAnimation(long start, float x, float y) {

        Interpolation interpolation = InterpolationFactory.createLinear(start, DURATION);
        Transformation transformation = TransformationFactory.createTranslate(x, y, 0f, interpolation);

        return AnimationFactory.createDynamic(transformation);
    }

    /**
     * Create a random color
     *
     * @return The color
     */
    private static Color createRandomColor() {

        int red = Math.abs(RANDOM.nextInt()) % (Color.DEPTH - 1);
        int green = Math.abs(RANDOM.nextInt()) % (Color.DEPTH - 1);
        int blue = Math.abs(RANDOM.nextInt()) % (Color.DEPTH - 1);
        int alpha = Color.DEPTH - 1;

        return ColorFactory.createColor(red, green, blue, alpha);
    }
}
