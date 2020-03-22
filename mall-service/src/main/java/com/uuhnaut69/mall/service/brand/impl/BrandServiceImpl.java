package com.uuhnaut69.mall.service.brand.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Brand;
import com.uuhnaut69.mall.mapper.BrandMapper;
import com.uuhnaut69.mall.payload.request.BrandRequest;
import com.uuhnaut69.mall.repository.brand.BrandRepository;
import com.uuhnaut69.mall.service.brand.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Brand findById(UUID id) {
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new NotFoundException(MessageConstant.BRAND_NOT_FOUND));
    }

    @Override
    public Brand create(BrandRequest brandRequest) {
        checkBrandNameValid(brandRequest.getBrandName());
        return save(brandRequest, new Brand());
    }

    @Override
    public Brand update(UUID id, BrandRequest brandRequest) {
        Brand brand = findById(id);
        if (!brand.getBrandName().equals(brandRequest.getBrandName())) {
            checkBrandNameValid(brandRequest.getBrandName());
        }
        return save(brandRequest, brand);
    }

    @Override
    public void delete(UUID id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }

    @Override
    public void deleteAll(List<UUID> idList) {
        List<Brand> brands = brandRepository.findByIdIn(idList);
        brandRepository.deleteAll(brands);
    }

    /**
     * Save brand entity
     *
     * @param brandRequest {@link BrandRequest}
     * @param brand        {@link Brand}
     * @return Brand
     */
    private Brand save(BrandRequest brandRequest, Brand brand) {
        brandMapper.toBrandEntity(brandRequest, brand);
        return brandRepository.save(brand);
    }

    /**
     * Check brand name valid or not
     *
     * @param brandName Brand's Name
     */
    private void checkBrandNameValid(String brandName) {
        if (brandRepository.existsByBrandName(brandName)) {
            throw new BadRequestException(MessageConstant.BRAND_ALREADY_EXIST);
        }
    }
}
