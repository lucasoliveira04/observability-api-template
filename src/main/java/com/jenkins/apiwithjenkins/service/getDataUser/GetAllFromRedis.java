package com.jenkins.apiwithjenkins.service.getDataUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service("getAllFromRedis")
@RequiredArgsConstructor
public class GetAllFromRedis implements IGetData<UserResponse> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<UserResponse> getUsers(Pageable pageable) {

        Set<String> keys = redisTemplate.keys("users::*");
        if (keys == null || keys.isEmpty()) {
            return List.of();
        }

        List<String> sortedKeys = keys.stream().sorted().toList();

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        int start = page * size;
        int end = Math.min(start + size, sortedKeys.size());

        if (start >= sortedKeys.size()) {
            return List.of();
        }

        List<String> selectedKeys = sortedKeys.subList(start, end);

        return selectedKeys.stream()
                .map(key -> redisTemplate.opsForValue().get(key))
                .map(this::convert)
                .map(userMapper::toResponse)
                .toList();
    }


    private Users convert(Object obj) {
        if (obj instanceof Users u) {
            return u;
        }

        if (obj instanceof Map<?, ?> map) {
            return objectMapper.convertValue(map, Users.class);
        }

        throw new IllegalArgumentException("Formato inesperado vindo do Redis: " + obj);
    }
}

