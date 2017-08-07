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
import net.tclemens.calcium.engine.graphics.batch.Batch;
import net.tclemens.calcium.engine.graphics.batch.BatchFactory;
import net.tclemens.calcium.engine.graphics.material.Material;
import net.tclemens.calcium.engine.graphics.model.Model;
import net.tclemens.calcium.engine.graphics.model.ModelFactory;
import net.tclemens.calcium.engine.graphics.sprite.Sprite;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a moving box
 *
 * @author Tim Clemens
 */
public final class Box {

    /** The sprite of the box */
    private final Sprite sprite;

    /** The animation of the box */
    private final Animation animation;

    /** The material of the box */
    private final Material material;

    /**=
     * @param sprite The sprite of the box
     * @param animation The animation of the box
     * @param material The material of the box
     */
    Box(Sprite sprite, Animation animation, Material material) {

        this.sprite = sprite;
        this.animation = animation;
        this.material = material;
    }

    /**
     * Convert the box into a renderable batch
     *
     * @return The renderable batch
     */
    public final Batch toBatch() {

        Collection<Model> models = createModels(sprite, animation);

        return BatchFactory.createDynamic(material, models);
    }

    /**
     * Create a collection of models from the specified sprite an animation
     *
     * @param sprite The sprite of the box
     * @param animation The animation of the box
     *
     * @return The collection of models
     */
    private static Collection<Model> createModels(Sprite sprite, Animation animation) {

        Collection<Model> models = new ArrayList<>(1);

        models.add(ModelFactory.createDynamic(sprite.toMesh(), animation));

        return models;
    }
}
