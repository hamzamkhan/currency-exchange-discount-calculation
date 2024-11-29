package org.example.cedc.controller;

import org.example.cedc.model.dto.request.OrderBillRequestDTO;
import org.example.cedc.model.dto.response.OrderBillResponseDTO;
import org.example.cedc.service.OrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@RestController
@RequestMapping("/api")
public class OrderCalcController {

    @Autowired
    private OrderBillService orderBillService;

    @PostMapping("/calculate")
    public ResponseEntity<OrderBillResponseDTO> orderCalculate(@RequestBody OrderBillRequestDTO requestDTO){
        return ResponseEntity.ok(orderBillService.calculateOrder(requestDTO));
    }
}
