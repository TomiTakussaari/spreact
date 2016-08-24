package com.github.tomitakussaari;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class Routes {

    @RequestMapping(value = "/")
    String main(Model model) throws JsonProcessingException {
        List items = Arrays.asList(new Item("foobar", "First item"), new Item("barfoo", "Second item"));
        model.addAttribute("items", new ObjectMapper().writeValueAsString(items));
        return "index";
    }

    @Getter
    @RequiredArgsConstructor
    private static class Item {
        private final String name;
        private final String content;

    }

}
