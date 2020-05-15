package com.uuhnaut69.mall.search.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class AbstractDocument implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Field(type = FieldType.Text)
    private String createdBy;

    @JsonIgnore
    @Field(type = FieldType.Text)
    private String updatedBy;

    @Field(type = FieldType.Date)
    private Timestamp createdDate;

    @Field(type = FieldType.Date)
    private Timestamp updatedDate;
}
