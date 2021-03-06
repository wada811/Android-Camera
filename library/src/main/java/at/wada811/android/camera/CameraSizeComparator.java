/*
 * Copyright 2014 wada811<at.wada811@gmail.com>
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
package at.wada811.android.camera;

import java.util.Comparator;

public class CameraSizeComparator implements Comparator<CameraSize>{

    private static final int LOW = 1;
    private static final int HIGH = -1;
    private static final int EQUAL = 0;

    @Override
    public int compare(CameraSize lhs, CameraSize rhs){
        if (lhs == null && rhs == null) {
            return EQUAL;
        }
        if (lhs == null) {
            return LOW;
        }
        if (rhs == null) {
            return HIGH;
        }

        final int lhsSize = lhs.width * lhs.height;
        final int rhsSize = rhs.width * rhs.height;
        if (lhsSize < rhsSize) {
            return LOW;
        } else if (lhsSize > rhsSize) {
            return HIGH;
        }
        return EQUAL;
    }
}
