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

package net.tclemens.calcium.demo.label;

import android.content.Context;
import android.graphics.Typeface;

import net.tclemens.calcium.engine.graphics.color.Color;
import net.tclemens.calcium.engine.graphics.material.Material;
import net.tclemens.calcium.engine.graphics.material.MaterialFactory;
import net.tclemens.calcium.engine.graphics.material.program.Program;
import net.tclemens.calcium.engine.graphics.material.program.ProgramFactory;
import net.tclemens.calcium.engine.graphics.material.property.Property;
import net.tclemens.calcium.engine.graphics.material.property.PropertyFactory;
import net.tclemens.calcium.engine.graphics.material.shader.Shader;
import net.tclemens.calcium.engine.graphics.material.shader.ShaderFactory;
import net.tclemens.calcium.engine.graphics.text.Text;
import net.tclemens.calcium.engine.graphics.text.TextFactory;
import net.tclemens.calcium.engine.graphics.text.font.Font;
import net.tclemens.calcium.engine.graphics.text.font.FontFactory;
import net.tclemens.calcium.engine.graphics.texture.Texture;
import net.tclemens.calcium.engine.graphics.texture.TextureFactory;
import net.tclemens.calcium.engine.graphics.texture.image.Image;
import net.tclemens.calcium.engine.graphics.texture.image.ImageFactory;
import net.tclemens.calcium.engine.graphics.texture.parameter.Filter;
import net.tclemens.calcium.engine.graphics.texture.parameter.Wrapping;
import net.tclemens.calcium.math.vector.Vector3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * This class is responsible for creating and initializing labels
 *
 * @author Tim Clemens
 */
public final class LabelFactory {

    /** The collection of symbols available for each label */
    private static final Collection<Character> SYMBOLS = new HashSet<>();

    /** The typeface for each label */
    private static final String TYPEFACE = "OpenSans-Regular.ttf";

    /** The initial height of each label */
    private static final float HEIGHT = 0.5f;

    private LabelFactory() {
    }

    /**
     * Create a label at the specified position with the specified color and symbols
     *
     * @param position The position of the label
     * @param context The application context
     * @param color The color of the font for the label
     * @param symbols The symbols for the label
     *
     * @return The label
     */
    public static Label createLabel(Vector3D position, Context context, Color color, String symbols) {

        Font font = createFont(context);
        Text text = TextFactory.createText(position, font, symbols, HEIGHT);
        Texture texture = createTexture(font, color);
        Material material = createMaterial(texture);

        return new Label(text, material);
    }

    /**
     * Create a font from specified application context
     *
     * @param context The application context
     *
     * @return The font
     */
    private static Font createFont(Context context) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), TYPEFACE);

        if (SYMBOLS.isEmpty()) {

            SYMBOLS.add('H');
            SYMBOLS.add('e');
            SYMBOLS.add('l');
            SYMBOLS.add('l');
            SYMBOLS.add('o');

            SYMBOLS.add('L');
            SYMBOLS.add('e');
            SYMBOLS.add('f');
            SYMBOLS.add('t');

            SYMBOLS.add('R');
            SYMBOLS.add('i');
            SYMBOLS.add('g');
            SYMBOLS.add('h');
            SYMBOLS.add('t');
        }

        return FontFactory.createFont(typeface, SYMBOLS, 30f, 0f);
    }

    /**
     * Create a texture from the specified font and color
     *
     * @param font The font drawn on the texture
     * @param color The color of the font
     *
     * @return The font texture
     */
    private static Texture createTexture(Font font, Color color) {

        Image image = ImageFactory.createFont(font, color, 1);

        return TextureFactory.createTexture(image, Filter.LINEAR, Filter.LINEAR, Wrapping.STRETCH, Wrapping.STRETCH);
    }

    /**
     * Create a material from the specified texture
     *
     * @param texture The texture of the material
     *
     * @return The material
     */
    private static Material createMaterial(Texture texture) {

        Shader vertex = ShaderFactory.createVertex(
                "uniform mat4 u_ModelViewProjection[1];" +
                "attribute float a_Model;" +
                "attribute vec4 a_Position;" +
                "attribute vec2 a_TextureCoordinates;" +
                "varying vec2 v_TextureCoordinates;" +
                "void main() {" +
                "  int index = int(a_Model);" +
                "  v_TextureCoordinates = a_TextureCoordinates;" +
                "  gl_Position = u_ModelViewProjection[index] * a_Position;" +
                "}");

        Shader fragment = ShaderFactory.createFragment(
                "precision mediump float;" +
                "uniform sampler2D u_Sampler;" +
                "varying vec2 v_TextureCoordinates;" +
                "void main() {" +
                "  gl_FragColor = texture2D(u_Sampler, v_TextureCoordinates);" +
                "}");

        Program program = ProgramFactory.createProgram(vertex, fragment);
        Collection<Property> properties = new ArrayList<>(4);

        properties.add(PropertyFactory.createModelViewProjection("u_ModelViewProjection"));
        properties.add(PropertyFactory.createTexture("a_TextureCoordinates", texture));
        properties.add(PropertyFactory.createModel("a_Model"));
        properties.add(PropertyFactory.createPosition("a_Position"));

        return MaterialFactory.createMaterial(program, properties);
    }
}
