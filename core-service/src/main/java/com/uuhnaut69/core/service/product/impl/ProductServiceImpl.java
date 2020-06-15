package com.uuhnaut69.core.service.product.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.domain.model.Product;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.mapper.ProductMapper;
import com.uuhnaut69.core.payload.request.ProductRequest;
import com.uuhnaut69.core.repository.product.ProductRepository;
import com.uuhnaut69.core.service.category.CategoryService;
import com.uuhnaut69.core.service.product.ProductService;
import com.uuhnaut69.core.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  private final TagService tagService;

  private final CategoryService categoryService;

  @Override
  @Transactional(readOnly = true)
  public Page<Product> findAll(Pageable pageable) {
    log.debug("Request to get products");
    return productRepository.findAllProducts(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Product findById(UUID id) {
    log.debug("Request to get product by id {}", id);
    Optional<Product> product = productRepository.findById(id);
    return product.orElseThrow(() -> new NotFoundException(MessageConstant.PRODUCT_NOT_FOUND));
  }

  @Override
  public Product create(ProductRequest productRequest) {
    log.debug("Request to create product {}", productRequest);
    return save(productRequest, new Product());
  }

  @Override
  public Product update(UUID id, ProductRequest productRequest) {
    log.debug("Request to update product by id {} with data {}", id, productRequest);
    Product product = findById(id);
    return save(productRequest, product);
  }

  @Override
  public void delete(UUID id) {
    log.debug("Request to delete product by id {}", id);
    productRepository.deleteById(id);
  }

  @Override
  public void deleteAll(List<UUID> ids) {
    log.debug("Request to delete product has id in {}", ids);
    productRepository.deleteByIdIn(ids);
  }

  private Product save(ProductRequest productRequest, Product product) {
    productMapper.toProductEntity(productRequest, product);
    Set<Tag> tags = tagService.findListTagInListIds(productRequest.getUuidTags());
    product.setTags(tags);
    Set<Category> categories = categoryService.findByIdIn(productRequest.getUuidCategories());
    product.setCategories(categories);
    return productRepository.save(product);
  }
}
