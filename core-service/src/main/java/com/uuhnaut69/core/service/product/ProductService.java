package com.uuhnaut69.core.service.product;

import com.uuhnaut69.core.domain.model.Product;
import com.uuhnaut69.core.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductService {

  Page<Product> findAll(Pageable pageable);

  Product findById(UUID id);

  Product create(ProductRequest productRequest);

  Product update(UUID id, ProductRequest productRequest);

  void delete(UUID id);

  void deleteAll(List<UUID> ids);
}
