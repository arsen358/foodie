//Created by Michał Basiński on 23.04.2020, 13:23;
package com.example.foodieapp.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/danie/{name}")
    public String getDish(@PathVariable String name, Model model) {
        Optional<Item> item = itemRepository.findByNameIgnoreCase(name);
        item.ifPresent(i -> model.addAttribute("item", i));
        return item.map(i -> "item").orElse("redirect:/");
    }
}
