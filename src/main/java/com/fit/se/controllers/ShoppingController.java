package com.fit.se.controllers;


import com.fit.se.repositories.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ShoppingController {
    private final ProductImageRepository productImageRepository;
    @GetMapping("/shopping")
    public String getShoppingPage(Model model) {

        return "client/shopping-page";
    }
}
