package com.uuhnaut69.mall.service.catalog.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Catalog;
import com.uuhnaut69.mall.mapper.CatalogMapper;
import com.uuhnaut69.mall.payload.request.CatalogRequest;
import com.uuhnaut69.mall.repository.catalog.CatalogRepository;
import com.uuhnaut69.mall.service.catalog.CatalogService;
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
public class CatalogServiceImpl implements CatalogService {

    private final CatalogMapper catalogMapper;

    private final CatalogRepository catalogRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Catalog> findAll(Pageable pageable) {
        return catalogRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Catalog findById(UUID id) {
        Optional<Catalog> catalog = catalogRepository.findById(id);
        return catalog.orElseThrow(() -> new NotFoundException(MessageConstant.CATALOG_NOT_FOUND));
    }

    @Override
    public Catalog create(CatalogRequest catalogRequest) {
        return save(catalogRequest, new Catalog());
    }

    @Override
    public Catalog update(UUID id, CatalogRequest catalogRequest) {
        Catalog catalog = findById(id);
        if (!catalog.getCatalogName().equals(catalogRequest.getCatalogName())) {
            checkCatalogNameValid(catalogRequest.getCatalogName());
        }
        return save(catalogRequest, catalog);
    }

    @Override
    public void delete(UUID id) {
        Catalog catalog = findById(id);
        catalogRepository.delete(catalog);
    }

    @Override
    public void deleteAll(List<UUID> ids) {
        List<Catalog> catalogs = catalogRepository.findByIdIn(ids);
        catalogRepository.deleteAll(catalogs);
    }

    /**
     * Check catalog name valid or not
     *
     * @param catalogName
     */
    private void checkCatalogNameValid(String catalogName) {
        if (catalogRepository.existsByCatalogName(catalogName)) {
            throw new BadRequestException(MessageConstant.CATALOG_ALREADY_EXIST);
        }
    }

    /**
     * Save catalog entity
     *
     * @param catalogRequest
     * @param catalog
     * @return Catalog
     */
    private Catalog save(CatalogRequest catalogRequest, Catalog catalog) {
        catalogMapper.toCatalogEntity(catalogRequest, catalog);
        return catalogRepository.save(catalog);
    }
}
