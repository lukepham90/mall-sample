package com.uuhnaut69.search.service.index.impl;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.TagEs;
import com.uuhnaut69.search.document.UserEs;
import com.uuhnaut69.search.service.index.TagEsService;
import com.uuhnaut69.search.service.index.UserEsService;
import com.uuhnaut69.search.service.index.UserTagEsService;
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
public class UserTagEsServiceImpl implements UserTagEsService {

    private final UserEsService userEsService;
    private final TagEsService tagEsService;

    @Override
    public void handleCdcEvent(
            Map<String, Object> userTagData, Map<String, Object> userTagDataBefore, Operation operation) {
        log.debug("Handle user <-> tag data change event {}", userTagData);
        String userId = userTagData.get("user_id").toString();
        String tagId = userTagData.get("tag_id").toString();
        UserEs userEs = userEsService.findById(userId);
        TagEs tagEs = tagEsService.findById(tagId);
        String tagIdBefore;
        TagEs tagEsBefore = new TagEs();
        if (!userTagDataBefore.isEmpty()) {
            tagIdBefore = userTagDataBefore.get("tag_id").toString();
            tagEsBefore = tagEsService.findById(tagIdBefore);
        }

        if (Operation.DELETE.name().equals(operation.name())) {
            updateTagInUser(userEs, tagEsBefore.getTagName());
        } else {
            updateTagInUser(userEs, tagEs.getTagName());
            userEs.getTags().add(tagEs.getTagName());
        }
        userEsService.save(userEs);
    }

    /**
     * Update tag in user
     *
     * @param userEs  User document
     * @param tagName Tag name
     */
    private void updateTagInUser(UserEs userEs, String tagName) {
        userEs.getTags().removeIf(tag -> tag.equals(tagName));
    }
}
