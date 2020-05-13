package com.uuhnaut69.mall.cdc.config.offset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author uuhnaut
 * @project mall
 * @date 5/13/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OffsetPosition {

    private Integer id;

    private String offsetKey;

    private String offsetPayload;

    private String engineName;

    private Timestamp createdDate;
}
