package com.uuhnaut69.search.document;

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

    @Field(type = FieldType.Date)
    private Timestamp createdDate;

    @Field(type = FieldType.Date)
    private Timestamp updatedDate;
}
