package com.fit.se.controllers;

import com.fit.se.services.OrderService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/show-chart-order")
    public String showChartOrderPage(Model model) {
//        Gson gsonObj = new Gson();
//        Map<Object,Object> map = null;
//        List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
//        map = new HashMap<Object,Object>(); map.put("label", "FY11"); map.put("y", 146.65); list.add(map);
//        map = new HashMap<Object,Object>(); map.put("label", "FY12"); map.put("y", 201.46); list.add(map);
//        map = new HashMap<Object,Object>(); map.put("label", "FY13"); map.put("y", 202.69); list.add(map);
//        map = new HashMap<Object,Object>(); map.put("label", "FY14"); map.put("y", 201.7); list.add(map);
//        map = new HashMap<Object,Object>(); map.put("label", "FY15"); map.put("y", 175.51); list.add(map);
//        map = new HashMap<Object,Object>(); map.put("label", "FY16"); map.put("y", 132.03); list.add(map);
//        String dataPoints = gsonObj.toJson(list);
//        model.addAttribute("dataPoints", dataPoints);
        return "admin/report/chart-order";
    }

    public Map<Object, Object> createDataPoint(LocalDateTime x, double y) {
        Map<Object, Object> map = new HashMap<>();
        map.put("label", x.toString());
        map.put("y", y);
        return map;
    }

    @GetMapping("/chart-paint")
    public String getChartOrderByDate(Model model,
                                      @RequestParam("fromDate") LocalDate fromDate,
                                      @RequestParam("toDate") LocalDate toDate
    ) {
        Gson gsonObj = new Gson();
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atStartOfDay();
        List<Map<Object, Object>> list = new ArrayList<>();
        Map<LocalDateTime, Double> mapOrder = orderService.getTotalPriceByDate(fromDateTime, toDateTime);
        System.out.println(mapOrder);
        for (LocalDateTime key : mapOrder.keySet()) {
            Map<Object, Object> data = createDataPoint(key, mapOrder.get(key));
            list.add(data);
        }
        String dataPoints = gsonObj.toJson(list);
        model.addAttribute("dataPoints", dataPoints);
        return "admin/report/chart-order";
    }
}
