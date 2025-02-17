package com.shop.servicve;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    // c:/shop 파일 저장
    public String uploadFile(String uploadPath, String orginalFileName, byte[] fileDate) throws Exception {

        UUID uuid = UUID.randomUUID();

        //test.jpg  확장자 읽기
        String extension = orginalFileName.substring(orginalFileName.lastIndexOf("."));

        log.info("extension -> " + extension);

        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath +"/" +  savedFileName;  // c:/shop/item/1607e07f-c0f9-4545-8329-f6d88927fad9.jpg

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileDate);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        }else{
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
