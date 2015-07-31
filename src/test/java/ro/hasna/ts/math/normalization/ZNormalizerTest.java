/**
 * Copyright (C) 2015 Octavian Hasna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.hasna.ts.math.normalization;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.FastMath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.hasna.ts.math.util.TimeSeriesPrecision;

/**
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ZNormalizerTest {
    @InjectMocks
    private ZNormalizer normalizer;

    @Mock
    private Mean mean;

    @Mock
    private StandardDeviation standardDeviation;

    @Test
    public void testCalls() throws Exception {
        Mockito.when(mean.evaluate(Matchers.any(double[].class))).thenReturn(3.0);
        double[] v = {1.0, 2.0, 3.0, 4.0, 5.0};

        normalizer.normalize(v);

        Mockito.verify(mean).evaluate(v);
        Mockito.verify(standardDeviation).evaluate(v, 3.0);
    }

    @Test
    public void testNormalize() throws Exception {
        normalizer = new ZNormalizer();
        double[] v = {1.0, 2.0, 3.0, 4.0, 5.0};
        double aux = FastMath.sqrt(2);
        double[] expected = {-2 / aux, -1 / aux, 0, 1 / aux, 2 / aux};

        double[] out = normalizer.normalize(v);

        Assert.assertArrayEquals(expected, out, TimeSeriesPrecision.EPSILON);
    }
}