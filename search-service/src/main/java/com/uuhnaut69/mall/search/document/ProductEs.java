package com.uuhnaut69.mall.search.document;

import com.uuhnaut69.mall.search.constant.EsConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.completion.Completion;

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
@EqualsAndHashCode(callSuper = false)
@Document(indexName = EsConstants.PRODUCT_INDEX, type = EsConstants.PRODUCT_INDEX_TYPE, shards = 1, replicas = 0, refreshInterval = "-1")
public class ProductEs extends AbstractDocument {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String productName;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String reviewsCnt;

    @Field(type = FieldType.Text)
    private String likesCnt;

    @Field(type = FieldType.Text)
    private String stocks;

    @Field(type = FieldType.Text)
    private String soldsCnt;

    @Field(type = FieldType.Text)
    private String imageUrls;

    @Field(type = FieldType.Text)
    private String thumbNail;

    @Field(type = FieldType.Text)
    private String originalPrice;

    @Field(type = FieldType.Text)
    private String discountPrice;

    @Field(type = FieldType.Nested)
    private CatalogEs catalogEs;

    @Field(type = FieldType.Nested)
    private BrandEs brandEs;

    @Field(type = FieldType.Keyword)
    private Set<String> tags = new HashSet<>();

    @Field(type = FieldType.Boolean)
    private Boolean important;

    /**
     * Declare field used for auto complete suggestion
     */
    private Completion productSuggest;

}