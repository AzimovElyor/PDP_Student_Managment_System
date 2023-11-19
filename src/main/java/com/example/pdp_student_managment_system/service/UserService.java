package com.example.pdp_student_managment_system.service;

import com.example.pdp_student_managment_system.dto.PageDto;
import com.example.pdp_student_managment_system.dto.user.UserRequestDto;
import com.example.pdp_student_managment_system.dto.user.UserResponseDto;
import com.example.pdp_student_managment_system.dto.user.UserSearchDto;
import com.example.pdp_student_managment_system.entity.UserEntity;
import com.example.pdp_student_managment_system.repository.UserRepository;
import com.example.pdp_student_managment_system.util.MessageConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public UserResponseDto findById(UUID id){
        UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND_BY_ID.formatted(id)));
       return modelMapper.map(userEntity,UserResponseDto.class);
    }
    public PageDto<UserResponseDto> searchUser(UserSearchDto searchDto){
        Pageable pageable = PageRequest.of(searchDto.getPage(),searchDto.getSize());

        Page<UserEntity> search = userRepository.
                search(searchDto.getName(), searchDto.getEmail(), searchDto.getSurname(), searchDto.getPhoneNumber(), searchDto.getUserRole(), pageable);
        search.getPageable();

        return this.mapPageToPageDto(search);
    }
    public PageDto<UserResponseDto> getAll(Integer page, Integer size){
       Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> getAll = userRepository.getAllByIsActiveTrue(pageable);
        return mapPageToPageDto(getAll);
    }
    @Transactional
    public void deleteById(UUID id){
        userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND_BY_ID.formatted(id)));
        userRepository.deleteUserById(id);
    }
    @Transactional
    public String newUserPassword(UUID id,String oldPassword, String newPassword){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND_BY_ID.formatted(id)));
        if(!passwordEncoder.matches(oldPassword,userEntity.getPassword()))
            throw new RuntimeException("Old password incorrect");
        userRepository.updatePassword(newPassword,id);
        return MessageConstants.UPDATE_PASSWORD;
    }

    @Transactional
    public String forgotPassword(String email, String newPassword) {
        userRepository.forgotPassword(email,newPassword);
        return MessageConstants.UPDATE_PASSWORD;
    }

    private PageDto<UserResponseDto> mapPageToPageDto(Page<UserEntity> page){
        PageDto<UserResponseDto> pageDto = new PageDto<>();
        pageDto.setContent(modelMapper.map(page.getContent(),new TypeToken<List<UserResponseDto>>(){}.getType()));
        pageDto.setPageNumber(page.getPageable().getPageNumber());
        pageDto.setSize(page.getPageable().getPageSize());
        pageDto.setLast(pageDto.isLast());
        pageDto.setFirst(pageDto.isFirst());
        pageDto.setSorted(page.getPageable().getSort().isSorted());
        pageDto.setOffset(page.getPageable().getOffset());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setTotalElements(page.getTotalElements());
        return pageDto;
    }


}
