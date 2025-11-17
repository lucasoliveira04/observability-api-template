package com.jenkins.apiwithjenkins.service;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    @Transactional(readOnly = false)
    UserResponse saveUser(UserDto userDto);

    @Transactional(readOnly = true)
    Page<UserResponse> getAllUsers(int page, int size, String strategy);

    @Transactional(readOnly = true)
    Iterable<Users> findByUserIds(List<UUID> userIds);

}
