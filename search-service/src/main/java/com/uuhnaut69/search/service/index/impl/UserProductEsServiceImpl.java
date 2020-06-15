package com.uuhnaut69.search.service.index.impl;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.ProductEs;
import com.uuhnaut69.search.document.UserEs;
import com.uuhnaut69.search.service.index.ProductEsService;
import com.uuhnaut69.search.service.index.UserEsService;
import com.uuhnaut69.search.service.index.UserProductEsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProductEsServiceImpl implements UserProductEsService {

  private final UserEsService userEsService;
  private final ProductEsService productEsService;

  @Override
  public void handleCdcEvent(Map<String, Object> userProductData, Operation operation) {
    log.debug("Handle user <-> product data change event {}", userProductData);
    String userId = userProductData.get("user_id").toString();
    String productId = userProductData.get("product_id").toString();

    UserEs userEs = userEsService.findById(userId);
    ProductEs productEs = productEsService.findById(productId);

    updateSeenProductIds(userEs, productEs.getId());
    if (!Operation.DELETE.name().equals(operation.name())) {
      userEs.getProductIds().add(productEs.getId());
    }
    userEsService.save(userEs);
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
