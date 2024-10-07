package com.jw.stock;

import static org.apache.commons.io.IOUtils.readLines;

import com.jw.service.QueueWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final QueueWriter queueWriter;

    public void updateStockFromFile(String fileName) {
        String path = "src/main/resources/%s".formatted(fileName);
        List<UpdateProduct> updateProducts = readProductsUpdatesFromFile(path);
        sendUpdateProductsToQueue(updateProducts);
    }

    private List<UpdateProduct> readProductsUpdatesFromFile(String fileToPath) {
        List<String> strings = readLinesFromStreamFile(fileToPath);
        List<UpdateProduct> updateProducts = strings.stream()
                .map(line -> line.split(","))
                .map(lineArray -> new UpdateProduct(Long.parseLong(lineArray[0]), Integer.parseInt(lineArray[1])))
                .toList();
        log.info("Read {} products from file: {}", updateProducts.size(), fileToPath);
        return updateProducts;
    }

    @SneakyThrows
    private List<String> readLinesFromStreamFile(String fileToPath) {
        return readLines(new InputStreamReader(new FileInputStream(fileToPath), StandardCharsets.UTF_8));
    }

    private void sendUpdateProductsToQueue(List<UpdateProduct> updateProducts) {
        updateProducts.forEach(queueWriter::sendUpdateProduct);
    }
}
