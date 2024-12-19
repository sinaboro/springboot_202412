package com.shop.servicve;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {


        //상품등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지등록
        for(int i=0; i<itemImgFileList.size(); i++){

            ItemImg itemImg = new ItemImg();

            itemImg.setItem(item);

            if(i==0){
                itemImg.setRepimgYn("Y"); //대표이미지
            }else{
                itemImg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }

//    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList
                = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    } // end getItemDtl


    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        //ItemFormDto 있는 변경 값들을 Item entity 저장
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }

        return item.getId();
    }
}
