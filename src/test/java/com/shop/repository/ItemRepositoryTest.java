package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        item.setItemSellStatus(ItemSellStatus.SELL);

        Item savedItem = itemRepository.save(item);

        System.out.println(savedItem);
    }

    @Test
    @DisplayName("레코드 삭제")
    public void delteItemTest(){
        itemRepository.deleteById(2L);
    }

    @Test
    @DisplayName("레코드 조회")
    public void findItemTest(){
        Optional<Item> item = itemRepository.findById(1L);
        System.out.println("--------------------");
        item.ifPresent(System.out::println);

    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest2(){
        for(int i=0; i<10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000*i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setStockNumber(100*i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            item.setItemSellStatus(ItemSellStatus.SELL);
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("레크드 개수 조회")
    public void countItemTest(){
        long count = itemRepository.count();
        System.out.println("count : " + count);
    }

    @Test
    @DisplayName("전체 레코드 조회")
    public void selectAllTest(){
        List<Item> items = itemRepository.findAll();
        items.forEach(item-> log.info(item.toString()));
    }

    @Test
    @DisplayName("레코드 수정")
    public void selectByIdTest(){
        Optional<Item> result = itemRepository.findById(1L);
        Item item = result.get();
        item.setItemNm("수정된 상품 이름");
        item.setPrice(9999);
        itemRepository.save(item);
    }

    @Test
    @DisplayName("상품명 조회(테스트 상품5)")
    public void selectByItemNnTest(){
        String name = "테스트 상품5";
        List<Item> itemNm = itemRepository.findItemByItemNm(name);

        itemNm.forEach(item -> log.info(item.toString()));
    }

    @Test
    @DisplayName("상품명 조회(와이드카드)")
    public void selectByItemNmContaining(){
        String name = "상품";
        List<Item> itemNm = itemRepository.findItemByItemNmContaining(name);

        itemNm.forEach(item -> log.info(item.toString()));
    }

    @Test
    @DisplayName("price 가격 이상 조회")
    public void selectByPriceGreaterThanEqual(){
        List<Item> items = itemRepository.findItemByPriceGreaterThan(50000);

        items.forEach(item -> log.info(item.toString()));
    }

    @Test
    @DisplayName("order by desc")
    public void selectByItemNmContainingOrderByPriceDesc(){
        List<Item> items = itemRepository.findItemByItemNmContainingOrderByPriceDesc("상품");

        items.forEach(item -> log.info(item.toString()));
    }

    @Test
    @DisplayName("ItemDetail")
    public void selectByItemDetail(){
        List<Item> items = itemRepository.findByItemDetail("상품");

        items.forEach(item -> log.info(item.toString()));
    }

    @Test
    @DisplayName("ItemDetail2")
    public void selectByItemDetail2(){
        List<Item> items = itemRepository.findByItemDetail2("상품");

        items.forEach(item -> log.info(item.toString()));
    }
}