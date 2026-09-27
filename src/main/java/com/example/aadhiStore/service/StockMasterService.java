package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.ProductItem;
import com.example.aadhiStore.entity.ProductItemPrice;
import com.example.aadhiStore.entity.StockMaster;
import com.example.aadhiStore.repository.ProductItemPriceRepository;
import com.example.aadhiStore.repository.ProductItemRepository;
import com.example.aadhiStore.repository.StockMasterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class StockMasterService {

    private final StockMasterRepository stockMasterRepository;
    private final ProductItemPriceRepository productItemPriceRepository;
    private final ProductItemRepository productItemRepository;

    public StockMasterService(StockMasterRepository stockMasterRepository, ProductItemPriceRepository productItemPriceRepository, ProductItemRepository productItemRepository) {
        this.stockMasterRepository = stockMasterRepository;
        this.productItemPriceRepository = productItemPriceRepository;
        this.productItemRepository = productItemRepository;
    }

    @Transactional
    public StockMaster saveStock(StockMaster stockMaster) {

        ProductItem productItem = productItemRepository.findByItemNameIgnoreCase(stockMaster.getItemName())
                        .orElseThrow(() -> new RuntimeException(
                                "Product Item not found: " + stockMaster.getItemName()
                        ));

        Optional<ProductItemPrice> existingPrice =
                productItemPriceRepository.findByProductItemId(productItem.getId());

        ProductItemPrice productItemPrice;

        if (existingPrice.isPresent()) {

            productItemPrice = existingPrice.get();


            long oldQuantity = productItemPrice.getQuantity() != null
                    ? productItemPrice.getQuantity()
                    : 0;

            long newQuantity = stockMaster.getQuantity() != null
                    ? stockMaster.getQuantity()
                    : 0;

            productItemPrice.setQuantity(oldQuantity + newQuantity);

        } else {

            // No price record exists -> create one
            productItemPrice = new ProductItemPrice();

            productItemPrice.setProductItem(productItem);

            productItemPrice.setQuantity(stockMaster.getQuantity());

            // Only used for first creation
            productItemPrice.setMrp(stockMaster.getMrp());
            productItemPrice.setMsp(stockMaster.getMsp());
        }

        productItemPrice =
                productItemPriceRepository.save(productItemPrice);

        Optional<StockMaster> existingStock = stockMasterRepository.findByitemNameIgnoreCase(
                        stockMaster.getItemName()
                );


        if (existingStock.isPresent()) {

            StockMaster stock = existingStock.get();

            long oldQuantity = stock.getQuantity() != null
                    ? stock.getQuantity()
                    : 0;

            long newQuantity = stockMaster.getQuantity() != null
                    ? stockMaster.getQuantity()
                    : 0;

            stock.setQuantity(oldQuantity + newQuantity);

            stock.setProductItemPriceId(productItemPrice.getId());

            return stockMasterRepository.save(stock);

        } else {

            stockMaster.setProductItemPriceId(
                    productItemPrice.getId()
            );

            return stockMasterRepository.save(stockMaster);
        }
    }
}
