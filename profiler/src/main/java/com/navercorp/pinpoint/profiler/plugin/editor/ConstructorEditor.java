/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.plugin.editor;

import java.util.Arrays;

import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentException;
import com.navercorp.pinpoint.bootstrap.instrument.MethodInfo;

public class ConstructorEditor implements MethodEditor {
    private final String[] targetParameterTypes;
    private final MethodRecipe recipe;
    private final boolean ignoreIfNotExist;

    public ConstructorEditor(String[] targetParameterTypes, MethodRecipe recipe, boolean ignoreIfNotExist) {
        this.targetParameterTypes = targetParameterTypes;
        this.recipe = recipe;
        this.ignoreIfNotExist = ignoreIfNotExist;
    }

    @Override
    public void edit(ClassLoader classLoader, InstrumentClass target) throws InstrumentException {
        MethodInfo targetConstructor = target.getConstructor(targetParameterTypes);
        
        if (targetConstructor == null) {
            if (ignoreIfNotExist) {
                return;
            } else {
                throw new InstrumentException("No constructor with parameter type (" + Arrays.deepToString(targetParameterTypes) + ")");
            }
        }
        
        recipe.edit(classLoader, target, targetConstructor);
    }
}
