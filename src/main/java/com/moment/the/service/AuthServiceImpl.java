package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(AdminDto adminDto) throws Exception {
        if(adminRepository.findByAdminId(adminDto.getAdminId()) != null){
            throw new Exception("아이디 중복");
        }
        adminDto.setAdminPwd(passwordEncoder.encode(adminDto.getAdminPwd()));
        adminRepository.save(adminDto.toEntity());
    }

    @Override
    public AdminDomain loginUser(String id, String password) throws Exception {
        AdminDomain adminDomain = adminRepository.findByAdminId(id);
        if(adminDomain == null) throw new Exception("이메일이 없습니다.");
        boolean passwordCheck = passwordEncoder.matches(password, adminDomain.getPassword());
        System.out.println("passwordCheck = " + passwordCheck);
        if(!passwordCheck) throw new Exception("비밀번호를 다시 입력해주세요.");
        return adminDomain;
    }
}
