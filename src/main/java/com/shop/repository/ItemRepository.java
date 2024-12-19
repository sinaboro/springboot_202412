package com.shop.repository;

import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findItemByItemNm(String itemNm);
    List<Item> findItemByItemNmContaining(String itemNm);

//    List<Item> findItemByPriceGreaterThanEqual(int price);
    List<Item> findItemByPriceGreaterThan(int price);

    List<Item> findItemByItemNmContainingOrderByPriceDesc(String itemNm);

    @Query("select i from Item i where i.itemDetail like %:aaa% order by i.price desc")
    List<Item> findByItemDetail(@Param("aaa") String itemDetail);

    @Query(value = "select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
    List<Item> findByItemDetail2(@Param("itemDetail") String itemDetail);
}
