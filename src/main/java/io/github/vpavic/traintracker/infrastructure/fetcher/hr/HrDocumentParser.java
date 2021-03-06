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

package io.github.vpavic.traintracker.infrastructure.fetcher.hr;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Deque;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.vpavic.traintracker.domain.model.voyage.Station;

final class HrDocumentParser {

    private HrDocumentParser() {
    }

    static Station parseCurrentPosition(Document doc) {
        Station station = null;
        Elements tables = doc.getElementsByTag("tbody");
        if (tables.size() == 2) {
            Elements rows = tables.last().children();
            String nameRaw = rows.get(1).child(0).child(1).text().trim();
            Element position = rows.get(2);
            String direction = position.child(0).child(0).text().trim();
            String timeRaw = position.child(0).child(1).text().trim();
            String delayRaw = rows.get(3).child(0).child(0).child(0).text().trim();
            String name = nameRaw.replace('+', ' ');
            LocalTime time = LocalTime.parse(timeRaw.substring(12, 17));
            int delay = delayRaw.startsWith("Kasni") ? Integer.parseInt(delayRaw.substring(6, delayRaw.indexOf(' ', 6)))
                    : 0;
            if (direction.equals("Odlazak")) {
                station = new Station(name);
                station.setDepartureTime(time);
                station.setDepartureDelay(delay);
            }
            else if (direction.equals("Dolazak") || direction.equals("Završio vožnju")) {
                station = new Station(name);
                station.setArrivalTime(time);
                station.setArrivalDelay(delay);
            }
        }
        return station;
    }

    static Deque<Station> parseOverview(Document doc) {
        Deque<Station> stations = new ArrayDeque<>();
        Elements tables = doc.getElementsByTag("tbody");
        if (tables.size() == 3) {
            Elements rows = tables.last().children();
            rows.remove(0);
            for (Element row : rows) {
                String name = row.child(0).text().trim();
                String direction = row.child(1).text().trim();
                String time = row.child(3).text().trim();
                String delay = row.child(4).text().trim();
                Station station;
                if (direction.equals("Dolazak")) {
                    station = new Station(name);
                    station.setArrivalTime(LocalTime.parse(time));
                    station.setArrivalDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
                }
                else {
                    if (!stations.isEmpty() && stations.peekLast().getName().equals(name)) {
                        station = stations.removeLast();
                    }
                    else {
                        station = new Station(name);
                    }
                    station.setDepartureTime(LocalTime.parse(time));
                    station.setDepartureDelay(delay.isEmpty() ? 0 : Integer.parseInt(delay));
                }
                stations.add(station);
            }
        }
        return stations;
    }

}
