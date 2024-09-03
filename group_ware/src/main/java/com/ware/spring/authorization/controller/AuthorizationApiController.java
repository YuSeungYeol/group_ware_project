package com.ware.spring.authorization.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.authorization.domain.Authorization;
import com.ware.spring.authorization.domain.AuthorizationDto;
import com.ware.spring.authorization.service.AuthorizationFileService;
import com.ware.spring.authorization.service.AuthorizationService;

//@Controller
//public class AuthorizationApiController {
//
//    private final AuthorizationService authorizationService;
//    private final AuthorizationFileService authorizationFileService;
//
//    @Autowired
//    public AuthorizationApiController(AuthorizationService authorizationService, 
//                                      AuthorizationFileService authorizationFileService) {
//        this.authorizationService = authorizationService;
//        this.authorizationFileService = authorizationFileService;
//    }
//    
//    @GetMapping("/download/{author_no}")
//	public ResponseEntity<Object> boardImgDownload(
//			@PathVariable("author_no")Long author_no){
//		return authorizationFileService.download(author_no);
//	}
//
//    @ResponseBody
//    @PostMapping("/authorization")
//    public Map<String, String> createAuthorization(AuthorizationDto dto, 
//                                @RequestParam(value = "file", required = false) MultipartFile file) {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("res_code", "404");
//        resultMap.put("res_msg", "게시글 등록 중 오류가 발생했습니다.");
//
//        // 파일이 있을 경우에만 업로드 처리
//        if (file != null && !file.isEmpty()) {
//            String savedFileName = authorizationFileService.upload(file);
//            if (savedFileName != null) {
//                dto.setAuthorOriThumbnail(file.getOriginalFilename());  // 필드 이름 수정
//                dto.setAuthorNewThumbnail(savedFileName);  // 필드 이름 수정
//                if(savedFileName != null) {
//                	resultMap.put("res_code", "200");
//    				resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
//                }
//            } else {
//                resultMap.put("res_msg", "문서 업로드가 실패하였습니다.");  
//            }
//            return resultMap;
//        }
//
//        // Authorization 객체 생성 및 저장
//        if (authorizationService.createAuthorization(dto) != null) {
//            resultMap.put("res_code", "200");
//            resultMap.put("res_msg", "문서가 성공적으로 등록되었습니다.");
//        }
//
//        return resultMap;
//    }
//
//}
@Controller
public class AuthorizationApiController {

    private final AuthorizationService authorizationService;
    private final AuthorizationFileService authorizationFileService;

    @Autowired
    public AuthorizationApiController(AuthorizationService authorizationService, 
                                      AuthorizationFileService authorizationFileService) {
        this.authorizationService = authorizationService;
        this.authorizationFileService = authorizationFileService;
    }
    
    @GetMapping("/download/{author_no}")
	public ResponseEntity<Object> boardImgDownload(
			@PathVariable("author_no")Long author_no){
		return authorizationFileService.download(author_no);
	}

    @ResponseBody
    @PostMapping("/authorization")
    public Map<String, String> createAuthorization(AuthorizationDto dto, 
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("res_code", "404");
        resultMap.put("res_msg", "게시글 등록 중 오류가 발생했습니다.");

        // 파일이 있을 경우에만 업로드 처리
        if (file != null && !file.isEmpty()) {
            String savedFileName = authorizationFileService.upload(file);
            if (savedFileName != null) {
                dto.setAuthorOriThumbnail(file.getOriginalFilename());
                dto.setAuthorNewThumbnail(savedFileName);
            } else {
                resultMap.put("res_msg", "문서 업로드가 실패하였습니다.");  
                return resultMap;
            }
        }

        // Authorization 객체 생성 및 저장
        Authorization savedAuthorization = authorizationService.createAuthorization(dto);

        if (savedAuthorization != null && savedAuthorization.getAuthorNo() != null) {
            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "문서가 성공적으로 등록되었습니다.");
        } else {
            resultMap.put("res_msg", "Authorization 생성 중 오류가 발생했습니다.");
        }

        int a = 1;
        		
        return resultMap;
    }

}