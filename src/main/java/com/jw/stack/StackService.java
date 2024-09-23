package com.jw.stack;

import static org.apache.commons.io.IOUtils.readLines;

import com.jw.service.QueueWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackService {

    private final QueueWriter queueWriter;

    private List<UpdateProduct> readProductsUpdatesFromFile(String fileToPath) throws IOException {
        List<String> strings =
                readLines(new InputStreamReader(new FileInputStream(fileToPath), StandardCharsets.UTF_8));
        List<UpdateProduct> updateProducts = strings.stream()
                .map(line -> line.split(","))
                .map(lineArray -> new UpdateProduct(Long.parseLong(lineArray[0]), Integer.parseInt(lineArray[1])))
                .toList();
        System.out.println(updateProducts.size());
        return updateProducts;
    }

    private void sendUpdateProductsToQueue(List<UpdateProduct> updateProducts) {
        updateProducts.forEach(queueWriter::sendUpdateProduct);
    }

    public void updateStackFromFile(String fileName) throws IOException {
        String path = "src/main/resources/%s".formatted(fileName);
        List<UpdateProduct> updateProducts = readProductsUpdatesFromFile(path);
        sendUpdateProductsToQueue(updateProducts);
    }
}
