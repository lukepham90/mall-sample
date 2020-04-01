package com.uuhnaut69.mall.search.service.index.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.repository.TagEsRepository;
import com.uuhnaut69.mall.search.service.index.ProductEsService;
import com.uuhnaut69.mall.search.service.index.TagEsService;
import com.uuhnaut69.mall.search.service.index.UserEsService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
public class TagEsServiceImpl implements TagEsService {

    private final TagEsRepository tagEsRepository;
    private final UserEsService userEsService;
    private final ProductEsService productEsService;

    public TagEsServiceImpl(TagEsRepository tagEsRepository, UserEsService userEsService,
                            ProductEsService productEsService) {
        this.tagEsRepository = tagEsRepository;
        this.userEsService = userEsService;
        this.productEsService = productEsService;
    }

    @Override
    public void maintainReadModel(Map<String, Object> tagData, Operation operation) {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final TagEs tagEs = mapper.convertValue(tagData, TagEs.class);

        Optional<TagEs> optional = tagEsRepository.findById(tagEs.getId());
        if (Operation.DELETE.name().equals(operation.name())) {
            // TODO update later
            tagEsRepository.deleteById(tagEs.getId());
        } else {
            if (optional.isPresent()) {
                userEsService.findByTagAndUpdate(optional.get().getTagName(), tagEs);
                productEsService.findByTagAndUpdate(optional.get().getTagName(), tagEs);
            }
            tagEsRepository.save(tagEs);
        }
    }

    @Override
    public TagEs findById(String id) {
        Optional<TagEs> tagEs = tagEsRepository.findById(id);
        return tagEs.orElseThrow(() -> new NotFoundException(MessageConstant.TAG_NOT_FOUND));
    }

}
