package com.arvent.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@ApiModel(description = "Customer Order Details")
@Data
@Builder
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;
    /*
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    */

    private Long customerId;

    @Column(name = "current_status", nullable = false)
    private String currentStatus;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
            , mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private Double totalCost;

    public void addTotalCost(Double cost, int quantity)
    {
        setTotalCost(getTotalCost()+(cost*quantity));
    }

    public Order() {
        this.totalCost=0D;
    }
}