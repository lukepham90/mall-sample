package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.common.utils.PagingUtils;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.mapper.TagMapper;
import com.uuhnaut69.core.payload.request.TagRequest;
import com.uuhnaut69.core.payload.response.TagResponse;
import com.uuhnaut69.core.service.tag.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Tag", value = "Tag Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class TagController {

    private final TagMapper tagMapper;

    private final TagService tagService;

    @ApiOperation(value = "Get Tags Endpoint", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.TAG_URL)
    public GenericResponse getTagPage(@RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                      @RequestParam(value = "order", defaultValue = "desc") String order,
                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        Page<Tag> tags = tagService.findAll(pageable);
        List<TagResponse> list = tagMapper.toListTagResponse(tags.getContent());
        return GenericResponse.builder().data(list).build();
    }

    @ApiOperation(value = "Create Tag Endpoint", notes = "Admin endpoint")
    @PostMapping(path = UrlConstants.ADMIN_URL + UrlConstants.TAG_URL)
    public GenericResponse create(@RequestBody @Valid TagRequest tagRequest) {
        Tag tag = tagService.create(tagRequest);
        TagResponse tagResponse = tagMapper.toTagResponse(tag);
        return GenericResponse.builder().data(tagResponse).build();
    }

    @ApiOperation(value = "Update Tag Endpoint", notes = "Admin endpoint")
    @PutMapping(path = UrlConstants.ADMIN_URL + UrlConstants.TAG_URL + "/{id}")
    public GenericResponse update(@PathVariable UUID id, @RequestBody @Valid TagRequest tagRequest) {
        Tag tag = tagService.update(id, tagRequest);
        TagResponse tagResponse = tagMapper.toTagResponse(tag);
        return GenericResponse.builder().data(tagResponse).build();
    }

    @ApiOperation(value = "Delete Tag Endpoint", notes = "Admin endpoint")
    @DeleteMapping(path = UrlConstants.ADMIN_URL + UrlConstants.TAG_URL + "/{id}")
    public GenericResponse delete(@PathVariable UUID id) {
        tagService.delete(id);
        return new GenericResponse();
    }
}
