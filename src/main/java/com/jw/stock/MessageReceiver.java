package com.jw.stock;

import com.jw.service.DbService;
import com.jw.service.OrderProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageReceiver {

    private final DbService dbService;
    private final OrderProductMapper orderProductMapper;

    //        @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Transactional
    public void receiveMessage(byte[] bytes) {
        UpdateProduct updateProduct = orderProductMapper.toUpdateProduct(new String(bytes));
        log.info(
                "Updating product: {} [{}]",
                updateProduct,
                Thread.currentThread().getName());
        dbService.updateProductQuantity(updateProduct.productId(), updateProduct.amount());
    }
}
