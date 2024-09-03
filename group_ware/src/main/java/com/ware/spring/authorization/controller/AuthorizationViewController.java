package com.ware.spring.authorization.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ware.spring.authorization.domain.AuthorizationDto;
import com.ware.spring.authorization.service.AuthorizationService;

@Controller
public class AuthorizationViewController {

    private final AuthorizationService authorizationService;
    
    private static final Logger LOGGER
    	= LoggerFactory.getLogger(AuthorizationViewController.class);
    
    @Autowired
    public AuthorizationViewController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    @GetMapping("/authorization/authorizationList")
    public String selectAuthorizationList(Model model){
        List<AuthorizationDto> resultList = authorizationService.selectAuthorizationList(); 
        model.addAttribute("resultList",resultList);
        return "authorization/authorizationList";
    }
   
    // 결재 창 
   @GetMapping("/authorization")
   public String selectAuthorization() {
       return "authorization/authorizationList";
   }
    
   // 결재 창 문서 생성
    @GetMapping("/authorization/authorizationCreate")
    public String createAuthorizationPage() {
        return "authorization/authorizationCreate";
    } 
    
	/* security 변경 후 위 내용을 아래 내용으로 변경
	 * @GetMapping("/authorization/authorizationCreate") public String
	 * createAuthorizationPage(Model model) { Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); Object principal =
	 * authentication.getPrincipal();
	 * 
	 * if (principal instanceof UserDetails) { String username = ((UserDetails)
	 * principal).getUsername(); Member member =
	 * memberRepository.findByUsername(username); model.addAttribute("memberNo",
	 * member.getMemNo()); }
	 * 
	 * return "authorization/authorizationCreate"; }
	 */
    
    // 모달 전달
    @GetMapping("/authorization/authorizationModal")
    public String showAuthorizationModal() {
        return "authorization/authorizationModal";
    }
    
    // 결재 문서함
    @GetMapping("/authorization/authorizationDocument")
    public String showAuthorizationDocument() {
        return "authorization/authorizationDocument";
    }
    // 문서함 연차 결재 서류 정보
    @GetMapping("/authorization/authorizationOff")
    public String showAuthorizationOffPage() {
        return "authorization/authorizationOff";
    }
    
    // 문서함 조퇴 결재 서류 정보
    @GetMapping("/authorization/authorizationLate")
    public String showAuthorizationLatePage() {
        return "authorization/authorizationLate";
    }
    
    // 문서함 해외 결재 서류 정보
    @GetMapping("/authorization/authorizationTrip")
    public String showAuthorizationTripPage() {
        return "authorization/authorizationTrip";
    }
}
