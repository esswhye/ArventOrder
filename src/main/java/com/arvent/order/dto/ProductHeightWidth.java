package com.arvent.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Builder
public class ProductHeightWidth {

    private Long id;

    private int productHeight;

    private int productWidth;

    @ToString.Exclude
    @JsonIgnore
    private Product product;
}
