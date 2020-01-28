package com.arvent.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Order_Items")
@ApiModel(description = "Customer Order Details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    /*
    @OneToOne
    private Product product;
       */

    @Column(name = "product_id")
    private Long productId;

    private int quantity;


    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    public OrderItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Column(name="purchased_product_price")
    private double purchasedProductPrice;


}