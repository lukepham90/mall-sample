package com.uuhnaut69.mall.search.service.impl;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.UserEs;
import com.uuhnaut69.mall.search.service.ProductEsService;
import com.uuhnaut69.mall.search.service.UserEsService;
import com.uuhnaut69.mall.search.service.UserProductEsService;
import com.uuhnaut69.mall.search.utils.Operation;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
public class UserProductEsServiceImpl implements UserProductEsService {

    private final UserEsService userEsService;
    private final ProductEsService productEsService;

    public UserProductEsServiceImpl(UserEsService userEsService, ProductEsService productEsService) {
        this.userEsService = userEsService;
        this.productEsService = productEsService;
    }

    @Override
    public void maintainReadModel(Map<String, Object> userProductData, Operation operation) throws Exception {
        String userId = userProductData.get("user_id").toString();
        String productId = userProductData.get("product_id").toString();

        UserEs userEs = userEsService.findById(userId);
        ProductEs productEs = productEsService.findById(productId);

        if (Operation.DELETE.name().equals(operation.name())) {
            updateSeenProductIds(userEs, productEs.getId());
            userEsService.save(userEs);
        } else {
            updateSeenProductIds(userEs, productEs.getId());
            userEs.getProductIds().add(productEs.getId());
            userEsService.save(userEs);
        }
    }

    /**
     * Update seen product ids in user
     *
     * @param userEs
     * @param productId
     */
    private void updateSeenProductIds(UserEs userEs, String productId) {
        boolean checkExist = userEs.getProductIds().stream().anyMatch(id -> id.equals(productId));
        if (checkExist) {
            userEs.getProductIds().removeIf(id -> id.equals(productId));
        }
    }
}
