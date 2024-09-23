package com.jw.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/stack/{fileName}")
    public String updateStockWithFile(@PathVariable String fileName) {
        log.info("Received request to update stock with file name {}", fileName);
        stockService.updateStockFromFile(fileName);
        return "Stock file read successfully";
    }
}
