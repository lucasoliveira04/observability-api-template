package com.jenkins.apiwithjenkins.mapper;

import com.jenkins.apiwithjenkins.dto.AddressResponse;
import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Address;
import com.jenkins.apiwithjenkins.entity.Users;

import java.util.List;

public record UserMapper() {

    public Users toEntity(UserDto userDto) {
        Users user = Users.builder()
                .name(userDto.name())
                .email(userDto.email())
                .phone(userDto.phone())
                .birthDate(userDto.birthDate())
                .cpf(userDto.cpf())
                .build();

        List<Address> addresses = userDto.addresses().stream()
                .map(dto -> Address.builder()
                        .city(dto.city())
                        .country(dto.country())
                        .state(dto.state())
                        .street(dto.street())
                        .zipCode(dto.zipCode())
                        .user(user)
                        .build())
                .toList();

        user.setAddress(addresses);

        return user;
    }

    public UserResponse toResponse(Users user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getBirthDate(),
                user.getAddress() != null && !user.getAddress().isEmpty()
                        ? new AddressResponse(
                        user.getAddress().get(0).getStreet(),
                        user.getAddress().get(0).getCity(),
                        user.getAddress().get(0).getState(),
                        user.getAddress().get(0).getZipCode(),
                        user.getAddress().get(0).getCountry()
                )
                        : null
        );
    }
}
