package com.shop.servicve;

import com.shop.dto.OrderDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //               >>상품번호,수량,         회원ID
    public Long order(OrderDto orderDto, String email){

        //상품정보 가져오기
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException());

        //회원정보 가져오기
        Member member = memberRepository.findByEmail(email);

        //주문저장 리스트
        List<OrderItem> orderItemList = new ArrayList<>();

        //상품명과 갯수로 주문 신청
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        //여러 상품 주문
        orderItemList.add(orderItem);

        //회원정보와 상품리스트로 주문엔티티 생성
        Order order = Order.createOrder(member, orderItemList);

        //DB orders 테이블 저장
        orderRepository.save(order);

        return order.getId();
    }
}
