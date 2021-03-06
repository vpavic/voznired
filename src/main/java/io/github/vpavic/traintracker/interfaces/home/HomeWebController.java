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

package io.github.vpavic.traintracker.interfaces.home;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import io.github.vpavic.traintracker.application.VoyageFetcher;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class HomeWebController {

    @GetMapping(path = "/")
    public String home(@ModelAttribute("countries") Set<String> countries) {
        return "redirect:/" + countries.iterator().next() + "/";
    }

    @GetMapping(path = "/{country:[a-z]{2}}")
    public String country(@PathVariable String country) {
        return "redirect:/" + country + "/";
    }

    @GetMapping(path = "/{country:[a-z]{2}}/")
    public String country(@PathVariable("country") VoyageFetcher fetcher, Model model) {
        if (fetcher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("country", fetcher.getCountry());
        return "home";
    }

}
