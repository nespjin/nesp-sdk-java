/*
 * Copyright (c) 2022.   NESP Technology.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.nesp.sdk.java.util;

import java.util.Timer;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/10/27 下午3:26
 * Description:
 **/
public interface Watcher {

    void reWatchIfNotRunning();

    void watch();

    void setTimer(Timer timer);

    void setWatchDelay(long watchDelay);

    void setWatchPeriod(long watchPeriod);

    void reset();

    boolean needRest();

    boolean isRunning();
}
