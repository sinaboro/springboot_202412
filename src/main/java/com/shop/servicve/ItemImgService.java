package com.shop.servicve;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg,  MultipartFile itemImgFile  ) throws Exception {

        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        System.out.println("------------oriImgName------------------" + oriImgName);
        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());

            imgUrl = "/images/item/" + imgName;  //   /images/item/1607e07f-c0f9-4545-8329-f6d88927fad9.jpg

            //아래 주석 코드는 이미지 저장시 입력한 갯수만큼 이미지 저장함
            //그러나 수정시 추가 할 수가 없음.
//            itemImg.updateItemImg(oriImgName, imgName, imgUrl );
//            itemImgRepository.save(itemImg);
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl );
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile)  throws Exception {

        //itemImgFile null이 아니면 실행
        //기존 이미지는 지우고, 새 이미지 저장
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(() -> new EntityNotFoundException());

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;

            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl );
        }
    } // end updateItemImg
}
