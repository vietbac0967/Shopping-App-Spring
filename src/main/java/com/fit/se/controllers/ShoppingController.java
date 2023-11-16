package com.fit.se.controllers;


import com.fit.se.models.Product;
import com.fit.se.models.ProductPrice;
import com.fit.se.repositories.ProductImageRepository;
import com.fit.se.repositories.ProductPriceRepository;
import com.fit.se.services.ProductPriceService;
import com.fit.se.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ShoppingController {
    private final ProductService productService;
    private final ProductPriceService productPriceService;

    @GetMapping("/shopping")
    public String showProductListPaging(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("keyword") Optional<String> keyword
            ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(7);
        Page<Product> productPage = productService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize),keyword
        );
        int totalPages = productPage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);
        model.addAttribute("productPriceService",productPriceService);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "client/shopping-page";
    }
}
