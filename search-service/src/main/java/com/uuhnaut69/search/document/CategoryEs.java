package com.uuhnaut69.search.document;

import com.uuhnaut69.search.constant.EsConstants;
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
@EqualsAndHashCode(callSuper = true)
@Document(indexName = EsConstants.CATEGORY_INDEX)
public class CategoryEs extends AbstractDocument {

  @Id private String id;

  @Field(type = FieldType.Keyword)
  private String categoryName;
}
