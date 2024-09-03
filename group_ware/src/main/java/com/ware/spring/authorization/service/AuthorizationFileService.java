package com.ware.spring.authorization.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.board.domain.Board;
import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.repository.AuthorizationRepository;

@Service
public class AuthorizationFileService {

    private String fileDir = "C:\\document\\upload\\";
    
    private final AuthorizationRepository authorizationRepository;
    
    @Autowired
    public AuthorizationFileService(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }
    
    public String upload(MultipartFile file) {
        
        String newFileName = null;
        
        try {
        
            String oriFileName = file.getOriginalFilename();
            String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."), oriFileName.length());
            
            UUID uuid = UUID.randomUUID();
            
            String uniqueName = uuid.toString().replaceAll("-", "");
            newFileName = uniqueName+fileExt;
            File saveFile = new File(fileDir+newFileName);

            if(!saveFile.exists()) {
                saveFile.mkdirs();
            }
            file.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }
    
    public ResponseEntity<Object> download(Long authorNo) {
        try {
            Authorization authorization = authorizationRepository.findByAuthorNo(authorNo);
            
            if (authorization == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String newFileName = authorization.getAuthorNewThumbnail();
            String oriFileName = URLEncoder.encode(authorization.getAuthorOriThumbnail(), "UTF-8");
            String downDir = fileDir + newFileName;

            Path filePath = Paths.get(downDir);

            if (!Files.exists(filePath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Resource resource = new InputStreamResource(Files.newInputStream(filePath));
            File file = new File(downDir);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(oriFileName).build());

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
