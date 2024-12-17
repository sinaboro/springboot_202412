package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart  extends  BaseEntity{

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FetchType.LAZY -> 필요한 시점에만 연관 데이터를 조회하므로 초기 로딩 시 성능을 최적화 할 수 있음.
    @OneToOne(fetch = FetchType.LAZY) //FetchType.LAZY -> Member테이블 사용 되기 직전 select, 지연로딩
//    @OneToOne(fetch = FetchType.EAGER) // 즉시 로딩
    //Member table있는 member_id를 외래키 설정하라!
    @JoinColumn(name="member_id")
    private Member member;
}
