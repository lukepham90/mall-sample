package com.uuhnaut69.search.document;

import com.uuhnaut69.search.constant.EsConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author uuhnaut
 * @project mall
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(indexName = EsConstants.USER_INDEX, type = EsConstants.USER_INDEX_TYPE)
public class UserEs extends AbstractDocument {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private Set<String> productIds = new HashSet<>();

    @Field(type = FieldType.Keyword)
    private Set<String> tags = new HashSet<>();
}
