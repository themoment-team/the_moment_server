package com.moment.the.service;

import com.moment.the.advice.exception.UserAlreadyExistsException;
import com.moment.the.advice.exception.UserNotFoundException;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AnswerService answerService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Override
    public void signUp(AdminDto adminDto) {
        if(adminRepository.findByAdminId(adminDto.getAdminId()) != null){
            throw new UserAlreadyExistsException();
        }
        adminDto.setAdminPwd(passwordEncoder.encode(adminDto.getAdminPwd()));
        adminRepository.save(adminDto.toEntity());
    }

    @Override
    public AdminDomain loginUser(String id, String password) {
        AdminDomain adminDomain = adminRepository.findByAdminId(id);
        if (adminDomain == null) throw new UserNotFoundException();
        boolean passwordCheck = passwordEncoder.matches(password, adminDomain.getPassword());
        System.out.println("passwordCheck = " + passwordCheck);
        if (!passwordCheck) throw new UserNotFoundException();
        return adminDomain;
    }

    // 로그아웃
    @Override
    public void logout() {
        String userEmail = this.GetUserEmail();
        redisUtil.deleteData(userEmail);
    }

    @Override
    public void withdrawal(SignInDto SignInDto) throws Exception {
        AdminDomain adminDomain = loginUser(SignInDto.getAdminId(), SignInDto.getAdminPwd());
        AnswerDomain answerDomain = answerService.answerFindBy(adminDomain);
        answerService.delete(answerDomain.getAnswerIdx());
        adminRepository.delete(adminDomain);
    }

    //현재 사용자의 ID를 Return
    public String GetUserEmail() {
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails)principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
