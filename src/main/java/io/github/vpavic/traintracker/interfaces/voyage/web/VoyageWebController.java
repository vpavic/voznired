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

package io.github.vpavic.traintracker.interfaces.voyage.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import io.github.vpavic.traintracker.application.VoyageFetcher;
import io.github.vpavic.traintracker.domain.model.voyage.Station;
import io.github.vpavic.traintracker.domain.model.voyage.Voyage;

@Controller
@RequestMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.TEXT_HTML_VALUE)
public class VoyageWebController {

    @GetMapping
    public String voyage(@PathVariable("country") VoyageFetcher fetcher, @PathVariable String train,
            @RequestHeader(name = "X-PJAX", required = false) boolean pjax, Model model) {
        if (fetcher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("country", fetcher.getCountry());
        model.addAttribute("train", train);
        Voyage voyage = fetcher.getVoyage(train);
        if (voyage == null) {
            return "not-found" + (pjax ? " :: fragment" : "");
        }
        model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
        model.addAttribute("voyage", voyage);
        return "voyage" + (pjax ? " :: fragment" : "");
    }

    private String calculateDelayLevel(Station station) {
        Integer delay = (station.getDepartureDelay() != null) ? station.getDepartureDelay() : station.getArrivalDelay();
        if (delay == null) {
            return "info";
        }
        else if (delay < 5) {
            return "success";
        }
        else if (delay < 20) {
            return "warning";
        }
        else {
            return "danger";
        }
    }

}
