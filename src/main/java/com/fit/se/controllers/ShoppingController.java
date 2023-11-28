package com.fit.se.controllers;


import com.fit.se.dto.Cart;
import com.fit.se.models.*;
import com.fit.se.repositories.*;
import com.fit.se.services.ProductImageService;
import com.fit.se.services.ProductPriceService;
import com.fit.se.services.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ShoppingController {
    private final ProductService productService;
    private final ProductPriceService productPriceService;
    private final ProductImageService productImageService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

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
                PageRequest.of(currentPage - 1, pageSize), keyword
        );
        int totalPages = productPage.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);
        model.addAttribute("productPriceService", productPriceService);
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

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id,
                            HttpSession session
    ) {
        ArrayList<Cart> cartList = new ArrayList<>();
        Cart cart = new Cart();
        cart.setId(id);
        cart.setQuantity(1);
        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cartList");
        if (cart_list == null) {
            cartList.add(cart);
            session.setAttribute("cartList", cartList);
        } else {
            cartList = cart_list;
            boolean exist = false;
            for (Cart c : cart_list) {
                if (c.getId() == id) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                cartList.add(cart);
            }
        }
        session.setAttribute("sizeCart", cartList.size());
        return "redirect:/shopping";
    }

    @GetMapping("/show-check-out")
    public String showCheckoutFrom(Model model, HttpSession session) {
        ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
        List<Cart> carts = null;
        double totalPrice = 0;
        if (cartList != null) {
            carts = productService.getCartsProduct(cartList);
            totalPrice = productService.getTotalCartPrice(cartList);
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts", carts);
        session.setAttribute("cartList", carts);
        model.addAttribute("productImage", productImageService);
        return "client/checkout-page";
    }

    @GetMapping("/increment/{id}")
    public String incrementQuantityProduct(@PathVariable("id") long id, HttpSession session) {
        ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
        for (Cart c : cartList) {
            if (c.getId() == id) {
                int quantity = c.getQuantity();
                quantity++;
                c.setQuantity(quantity);
                break;
            }
        }
        return "redirect:/show-check-out";
    }

    @GetMapping("/decrement/{id}")
    public String decrementQuantityProduct(@PathVariable("id") long id, HttpSession session) {
        ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
        for (Cart c : cartList) {
            if (c.getId() == id && c.getQuantity() > 1) {
                int quantity = c.getQuantity();
                quantity--;
                c.setQuantity(quantity);
                break;
            }
        }
        return "redirect:/show-check-out";
    }

    @GetMapping("/remove-cart/{id}")
    public String removeCart(@PathVariable("id") long id, HttpSession session) {
        ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
        if (cartList != null) {
            for (Cart cart : cartList) {
                if (cart.getId() == id) {
                    cartList.remove(cart);
                    break;
                }
            }
        }
        assert cartList != null;
        session.setAttribute("sizeCart", cartList.size());
        return "redirect:/show-check-out";
    }

    @GetMapping("/checkout")
    public String checkoutOrder(HttpSession session) {
        ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
        Random random = new Random();
        long customerId = (long) random.nextInt((200 - 1) + 1) + 1;
        long employeeId = (long) random.nextInt((200 - 1) + 1) + 1;
        if (cartList != null) {
            Order order = Order.builder()
                    .customer(Customer.builder().id(customerId).build())
                    .employee(Employee.builder().id(employeeId).build())
                    .orderDate(LocalDateTime.now())
                    .build();
            orderRepository.save(order);
            for (Cart c : cartList) {
                System.out.println(c.getPrice());
                OrderDetail orderDetail = OrderDetail.builder()
                        .note("Order Complete")
                        .price(c.getPrice())
                        .quantity(c.getQuantity())
                        .order(order)
                        .product(productRepository.findById(c.getId()).orElseThrow(
                                () -> new IllegalArgumentException("Not found with id: " + c.getId())))
                        .build();
                orderDetailRepository.save(orderDetail);
            }
            cartList.clear();
            session.setAttribute("sizeCart", 0);
            return "redirect:/shopping";
        } else {
            return "redirect:/show-check-out";
        }
    }
}
