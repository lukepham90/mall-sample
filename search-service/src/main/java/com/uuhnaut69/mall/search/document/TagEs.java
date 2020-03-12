package com.uuhnaut69.mall.search.document;

import com.uuhnaut69.mall.search.constant.EsConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author uuhnaut
 * @project mall
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Document(indexName = EsConstants.TAG_INDEX, type = EsConstants.TAG_INDEX_TYPE, shards = 1, replicas = 0, refreshInterval = "-1")
public class TagEs extends AbstractDocument {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String tagName;
}