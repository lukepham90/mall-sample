package com.uuhnaut69.mall.service.product.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.Tag;
import com.uuhnaut69.mall.mapper.ProductMapper;
import com.uuhnaut69.mall.payload.request.ProductRequest;
import com.uuhnaut69.mall.repository.product.ProductRepository;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.tag.TagService;
import lombok.RequiredArgsConstructor;
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
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new NotFoundException(MessageConstant.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product create(ProductRequest productRequest) {
        return save(productRequest, new Product());
    }

    @Override
    public Product update(UUID id, ProductRequest productRequest) {
        Product product = findById(id);
        return save(productRequest, product);
    }

    @Override
    public void delete(UUID id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Override
    public void deleteAll(List<UUID> ids) {
        List<Product> list = productRepository.findByIdIn(ids);
        productRepository.deleteAll(list);
    }

    /**
     * Save product entity
     *
     * @param productRequest {@link ProductRequest}
     * @param product        {@link Product}
     * @return Product
     */
    private Product save(ProductRequest productRequest, Product product) {
        productMapper.toProductEntity(productRequest, product);
        Set<Tag> tags = tagService.findListTagInListIds(productRequest.getUuidTags());
        product.setTags(tags);
        return productRepository.save(product);
    }
}
