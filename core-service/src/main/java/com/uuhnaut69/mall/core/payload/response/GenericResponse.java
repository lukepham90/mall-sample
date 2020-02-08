package com.uuhnaut69.mall.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Common Api Response", description = "Common Api Response")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GenericResponse {

    @ApiModelProperty(value = "Payload")
    private Object data;

    @ApiModelProperty(value = "HTTP status code")
    private static final Integer CODE = 200;

}
