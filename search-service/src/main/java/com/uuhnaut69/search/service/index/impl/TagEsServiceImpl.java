package com.uuhnaut69.search.service.index.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.TagEs;
import com.uuhnaut69.search.repository.TagEsRepository;
import com.uuhnaut69.search.service.index.ProductEsService;
import com.uuhnaut69.search.service.index.TagEsService;
import com.uuhnaut69.search.service.index.UserEsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TagEsServiceImpl implements TagEsService {

  private final TagEsRepository tagEsRepository;
  private final UserEsService userEsService;
  private final ProductEsService productEsService;

  @Override
  public void handleCdcEvent(Map<String, Object> tagData, Operation operation) {
    log.debug("Handle tag data change event {}", tagData);
    final ObjectMapper mapper =
        new ObjectMapper()
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
