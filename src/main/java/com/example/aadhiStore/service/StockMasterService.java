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

        ProductItem productItem =
                productItemRepository.findByItemNameIgnoreCase(stockMaster.getItemName())
                        .orElseThrow(() -> new RuntimeException(
                                "Product Item not found: " + stockMaster.getItemName()
                        ));

        Optional<ProductItemPrice> existingPrice =
                productItemPriceRepository.findByProductItemId(productItem.getId());

        ProductItemPrice productItemPrice;

        if (existingPrice.isPresent()) {

            productItemPrice = existingPrice.get();

            productItemPrice.setQuantity(stockMaster.getQuantity());
            productItemPrice.setMrp(stockMaster.getMrp());
            productItemPrice.setMsp(stockMaster.getMsp());

        } else {

            productItemPrice = new ProductItemPrice();

            productItemPrice.setProductItem(productItem);

            productItemPrice.setQuantity(stockMaster.getQuantity());
            productItemPrice.setMrp(stockMaster.getMrp());
            productItemPrice.setMsp(stockMaster.getMsp());
        }

        productItemPrice = productItemPriceRepository.save(productItemPrice);


        stockMaster.setProductItemPriceId(productItemPrice.getId());

        // Save StockMaster
        return stockMasterRepository.save(stockMaster);
    }
}
