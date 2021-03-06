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

package io.github.vpavic.traintracker.domain.model.voyage;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.github.vpavic.traintracker.domain.model.carrier.Carrier;

public class Voyage implements Serializable {

    private Carrier carrier;

    private LocalDate date;

    private Station currentStation;

    private Collection<Station> stations;

    private List<URI> sources;

    private LocalTime generatedTime;

    public Voyage(Carrier carrier, LocalDate date, Station currentStation, Collection<Station> stations,
            List<URI> sources, LocalTime generatedTime) {
        Objects.requireNonNull(carrier, "Carrier must not be null");
        Objects.requireNonNull(currentStation, "Current station must not be null");
        Objects.requireNonNull(stations, "Stations must not be null");
        this.carrier = carrier;
        this.date = date;
        this.currentStation = currentStation;
        this.stations = Collections.unmodifiableCollection(stations);
        this.sources = Collections.unmodifiableList(sources);
        this.generatedTime = generatedTime;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Station getCurrentStation() {
        return this.currentStation;
    }

    public Collection<Station> getStations() {
        return this.stations;
    }

    public List<URI> getSources() {
        return this.sources;
    }

    public LocalTime getGeneratedTime() {
        return this.generatedTime;
    }

}
