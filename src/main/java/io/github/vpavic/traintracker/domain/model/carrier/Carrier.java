/*
 * Copyright 2020 the original author or authors.
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

package io.github.vpavic.traintracker.domain.model.carrier;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Objects;

public class Carrier implements Serializable {

    private String id;

    private String name;

    private String website;

    private ZoneId timezone;

    public Carrier(String id, String name, String website, ZoneId timezone) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.website = Objects.requireNonNull(website);
        this.timezone = Objects.requireNonNull(timezone);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getWebsite() {
        return this.website;
    }

    public ZoneId getTimezone() {
        return this.timezone;
    }

}
