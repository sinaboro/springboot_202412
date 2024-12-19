package com.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private  JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus itemSellStatus) {
        return itemSellStatus == null ? null : QItem.item.itemSellStatus.eq(itemSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();  //현재시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" +searchQuery+ "%");
        }else if(StringUtils.equals("createBy", searchBy)){
            return QItem.item.createBy.like("%" +searchQuery+ "%");
        }
        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        log.info("-------------------------------------------------------------------");
        log.info(regDtsAfter(itemSearchDto.getSearchDateType()));
        log.info(searchSellStatusEq(itemSearchDto.getItemSellStatus()));
        log.info(searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()));
        log.info(pageable.getOffset());
        log.info(pageable.getPageSize());
        log.info("-------------------------------------------------------------------");

        List<Item> content = jpaQueryFactory.select(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType())
                        , searchSellStatusEq(itemSearchDto.getItemSellStatus())
                        , searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                )
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType())
                        , searchSellStatusEq(itemSearchDto.getItemSellStatus())
                        , searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
