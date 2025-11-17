package com.jenkins.apiwithjenkins.service.getDataUser;


import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.mapper.UserMapper;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("getAllFromPostgres")
@RequiredArgsConstructor
public class GetAllFromPostgres implements IGetData<UserResponse> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getUsers(Pageable pageable) {
        return userRepository
                .findAllWithAddresses(pageable)
                .map(userMapper::toResponse)
                .getContent();
    }
}
