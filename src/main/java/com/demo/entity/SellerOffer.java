package com.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller_offer")
@IdClass(SellerOfferId.class)
public class SellerOffer {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private String description;
}
