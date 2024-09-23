package com.jw.stack;

import com.jw.service.DbService;
import com.jw.service.OrderProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageReceiver {

    private final DbService dbService;
    private final OrderProductMapper orderProductMapper;

    public void receiveMessage(byte[] bytes) {
        UpdateProduct updateProduct = orderProductMapper.toUpdateProduct(new String(bytes));
        log.info(
                "Received updateProduct request {} [thread={}]",
                updateProduct,
                Thread.currentThread().getName());
        dbService.updateProductQuantity(updateProduct.productId(), updateProduct.amount());
    }
}
