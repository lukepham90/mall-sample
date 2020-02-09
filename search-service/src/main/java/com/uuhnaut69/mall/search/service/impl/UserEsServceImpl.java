package com.uuhnaut69.mall.search.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.document.UserEs;
import com.uuhnaut69.mall.search.repository.UserEsRepository;
import com.uuhnaut69.mall.search.service.UserEsService;
import com.uuhnaut69.mall.search.utils.Operation;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
public class UserEsServceImpl implements UserEsService {

    private final UserEsRepository userEsRepository;

    public UserEsServceImpl(UserEsRepository userEsRepository) {
        this.userEsRepository = userEsRepository;
    }

    @Override
    public void maintainReadModel(Map<String, Object> userData, Operation operation) throws Exception {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final UserEs userEs = mapper.convertValue(userData, UserEs.class);

        Optional<UserEs> optional = userEsRepository.findById(userEs.getId());
        if (Operation.DELETE.name().equals(operation.name())) {
            userEsRepository.deleteById(userEs.getId());
        } else {
            if (optional.isPresent()) {
                userEs.setProductIds(optional.get().getProductIds());
                userEs.setTags(optional.get().getTags());
            }
            userEsRepository.save(userEs);
        }

    }

    @Override
    public UserEs findById(String id) throws Exception {
        Optional<UserEs> userEs = userEsRepository.findById(id);
        return userEs.orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }

    @Override
    public void save(UserEs userEs) throws Exception {
        userEsRepository.save(userEs);
    }

    @Override
    public void findByTagAndUpdate(String tagNameBefore, TagEs tagEs) {
        List<UserEs> list = userEsRepository.search(
                new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("tags", tagNameBefore)).build())
                .getContent();
        if (!list.isEmpty()) {
            list.forEach(user -> {
                user.getTags().removeIf(tag -> tag.equals(tagNameBefore));
                user.getTags().add(tagEs.getTagName());
            });
            userEsRepository.saveAll(list);
        }
    }

}
