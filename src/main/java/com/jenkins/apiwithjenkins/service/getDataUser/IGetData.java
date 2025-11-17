package com.jenkins.apiwithjenkins.service.getDataUser;

import com.jenkins.apiwithjenkins.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGetData<T> {
    List<T> getUsers(Pageable pageable);
}
