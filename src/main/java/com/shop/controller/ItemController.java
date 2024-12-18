package com.shop.controller;

import com.shop.dto.ItemFormDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String newItem(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }
}
