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

import net.tclemens.calcium.engine.graphics.batch.Batch;
import net.tclemens.calcium.engine.graphics.batch.BatchFactory;
import net.tclemens.calcium.engine.graphics.material.Material;
import net.tclemens.calcium.engine.graphics.model.Model;
import net.tclemens.calcium.engine.graphics.model.ModelFactory;
import net.tclemens.calcium.engine.graphics.text.Text;
import net.tclemens.calcium.math.matrix.Matrix3D;
import net.tclemens.calcium.math.matrix.MatrixFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a stationary label
 *
 * @author Tim Clemens
 */
public final class Label {

    /** The text of the label */
    private final Text text;

    /** The material of the label */
    private final Material material;

    /**
     * @param text The text of the label
     * @param material The material of the label
     */
    Label(Text text, Material material) {

        this.text = text;
        this.material = material;
    }

    /**
     * Convert the label into a renderable batch
     *
     * @return The renderable batch
     */
    public final Batch toBatch() {

        Collection<Model> models = createModels(text);

        return BatchFactory.createDynamic(material, models);
    }

    /**
     * Create a collection of models from the specified text
     *
     * @param text The text of the label
     *
     * @return The collection of models
     */
    private static Collection<Model> createModels(Text text) {

        Matrix3D matrix = createTransformation(text);
        Collection<Model> models = new ArrayList<>(1);

        models.add(ModelFactory.createStatic(text.toMesh(), matrix));

        return models;
    }

    /**
     * Create a transformation to center the specified text
     *
     * @param text The text of the label
     *
     * @return A transformation
     */
    private static Matrix3D createTransformation(Text text) {

        float offset = -text.getWidth() / 2f;

        return MatrixFactory.createTranslate3D(offset, 0f, 0f);
    }
}
