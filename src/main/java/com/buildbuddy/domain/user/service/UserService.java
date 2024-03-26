package com.buildbuddy.domain.user.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.user.dto.BalanceTransactionReqParam;
import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.domain.user.dto.response.BalanceTransDto;
import com.buildbuddy.domain.user.dto.response.BalanceTransSchema;
import com.buildbuddy.domain.user.dto.response.UserResponseDto;
import com.buildbuddy.domain.user.entity.BalanceTransaction;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.BalanceTransactionRepository;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.UserRole;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import com.paypal.base.codec.binary.Base64;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private PaginationCreator paginationCreator;

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
    public DataResponse<UserResponseDto> save(UserRequestDto userDto){

        UserEntity user = null;

        if(userDto.getUserId() == null){
            log.info("Creating user");
            String role = userDto.getRole();

            if(!UserRole.isAValidRole(role))
                throw new RuntimeException("role is not valid");
            String pass = passwordEncoder.encode(userDto.getPassword());

            user = UserEntity.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .password(pass)
                    .role(role)
                    .age(userDto.getAge())
                    .gender(userDto.getGender())
                    .balance(BigDecimal.ZERO)
                    .createdTime(LocalDateTime.now())
                    .build();

            if(role.equals(UserRole.CONSULTANT.getValue())){
                log.info("creating consultant detail");
                ConsultantDetail consultantDetail = ConsultantDetail.builder()
                        .user(user)
                        .fee(userDto.getFee())
                        .available(userDto.getAvailable())
                        .description(userDto.getDescription())
                        .build();
                user.setConsultantDetail(consultantDetail);
            }
        }
        else {
            log.info("Updating user");
            user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
            user.updateDetail(userDto);
        }

        user = userRepository.saveAndFlush(user);

        UserResponseDto data = UserResponseDto.convertToDto(user);

        return DataResponse.<UserResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving user detail")
                .data(data)
                .build();
    }

    public DataResponse<Object> getBalanceTrans(BalanceTransactionReqParam param){

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<BalanceTransaction> balanceTransactionPage = balanceTransactionRepository.findByUserId(param.getUserId(), pageable);

        List<BalanceTransDto> balanceTransDtos = balanceTransactionPage.stream()
                .map(BalanceTransDto::convertToDto)
                .toList();

        BalanceTransSchema data = BalanceTransSchema.builder()
                .balanceTransactionList(balanceTransDtos)
                .totalPages(balanceTransactionPage.getTotalPages())
                .totalData(balanceTransactionPage.getTotalElements())
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();

        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.CREATED)
                .message("Success creating user")
                .data(data)
                .build();
    }

    public DataResponse<Object> uploadProfile(Integer userId, MultipartFile file) throws IOException {

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        user.setProfilePicture(file.getBytes());

        userRepository.saveAndFlush(user);

        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success updating profile")
                .data(Base64.encodeBase64String(file.getBytes()))
                .build();
    }
}
