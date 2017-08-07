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

package net.tclemens.calcium.demo.box;

import net.tclemens.calcium.engine.graphics.animation.Animation;
import net.tclemens.calcium.engine.graphics.color.Color;
import net.tclemens.calcium.engine.graphics.material.Material;
import net.tclemens.calcium.engine.graphics.material.MaterialFactory;
import net.tclemens.calcium.engine.graphics.material.program.Program;
import net.tclemens.calcium.engine.graphics.material.program.ProgramFactory;
import net.tclemens.calcium.engine.graphics.material.property.Property;
import net.tclemens.calcium.engine.graphics.material.property.PropertyFactory;
import net.tclemens.calcium.engine.graphics.material.shader.Shader;
import net.tclemens.calcium.engine.graphics.material.shader.ShaderFactory;
import net.tclemens.calcium.engine.graphics.sprite.Sprite;
import net.tclemens.calcium.engine.graphics.sprite.SpriteFactory;
import net.tclemens.calcium.math.vector.Vector3D;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is responsible for creating and initializing boxes
 *
 * @author Tim Clemens
 */
public final class BoxFactory {

    /** The width of each box */
    private static final float WIDTH = 1f;

    /** The height of each box */
    private static final float HEIGHT = 1f;

    private BoxFactory() {
    }

    /**
     * Create a label at the specified position with the specified color and symbols
     *
     * @param position The position of the box
     * @param animation The animation applied to the box
     * @param color The color of the box
     *
     * @return The box
     */
    public static Box createBox(Vector3D position, Animation animation, Color color) {

        Sprite sprite = SpriteFactory.createSprite(position, WIDTH, HEIGHT);
        Material material = createMaterial(color);

        return new Box(sprite, animation, material);
    }

    /**
     * Create a material from the specified color
     *
     * @param color The color of the material
     *
     * @return The material
     */
    private static Material createMaterial(Color color) {

        Shader vertex = ShaderFactory.createVertex(
                "uniform mat4 u_ModelViewProjection[1];" +
                "attribute float a_Model;" +
                "attribute vec4 a_Position;" +
                "void main() {" +
                "  int index = int(a_Model);" +
                "  gl_Position = u_ModelViewProjection[index] * a_Position;" +
                "}");

        Shader fragment = ShaderFactory.createFragment(
                "precision mediump float;" +
                "uniform lowp vec4 u_Color;" +
                "void main() {" +
                "  gl_FragColor = u_Color;" +
                "}");

        Program program = ProgramFactory.createProgram(vertex, fragment);
        Collection<Property> properties = new ArrayList<>(4);

        properties.add(PropertyFactory.createModelViewProjection("u_ModelViewProjection"));
        properties.add(PropertyFactory.createColor("u_Color", color));
        properties.add(PropertyFactory.createModel("a_Model"));
        properties.add(PropertyFactory.createPosition("a_Position"));

        return MaterialFactory.createMaterial(program, properties);
    }
}
