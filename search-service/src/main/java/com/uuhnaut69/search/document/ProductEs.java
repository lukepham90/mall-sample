package com.uuhnaut69.search.document;

import com.uuhnaut69.search.constant.EsConstants;
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
@EqualsAndHashCode(callSuper = true)
@Document(indexName = EsConstants.PRODUCT_INDEX)
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

    @Field(type = FieldType.Integer)
    private Integer stocks;

    @Field(type = FieldType.Text)
    private String imageUrls;

    @Field(type = FieldType.Text)
    private String thumbNail;

    @Field(type = FieldType.Double)
    private Double originalPrice;

    @Field(type = FieldType.Double)
    private Double discountPrice;

    @Field(type = FieldType.Keyword)
    private Set<String> tags = new HashSet<>();

    @Field(type = FieldType.Keyword)
    private Set<String> categories = new HashSet<>();

    @Field(type = FieldType.Boolean)
    private Boolean trending;

    private Completion productSuggest;

}
