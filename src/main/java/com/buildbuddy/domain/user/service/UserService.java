package com.buildbuddy.domain.user.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.BalanceTransactionRepository;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.jsonresponse.DataResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditorAwareImpl auditorAware;

    public DataResponse<UserResponseDto> getUserByUsername(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
         log.info("{}", userDetails.getUsername());

        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        UserEntity user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting user")
                .data(data)
                .build();
    }

    public DataResponse<Object> getUser(Integer userId){

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .data(data)
                .message("Success getting user")
                .build();
    }

    @Transactional
    public DataResponse<UserResponseDto> createUser(UserRequestDto userDto){

        String pass = passwordEncoder.encode(userDto.getPassword());

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(pass)
                .role("user")
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .balance(BigDecimal.ZERO)
                .createdTime(LocalDateTime.now())
                .build();

        user = userRepository.saveAndFlush(user);

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.CREATED)
                .message("Success creating user")
                .data(data)
                .build();
    }
}
