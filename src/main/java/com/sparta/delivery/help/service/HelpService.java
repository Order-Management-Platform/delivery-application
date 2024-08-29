package com.sparta.delivery.help.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.help.dto.HelpRequestDto;
import com.sparta.delivery.help.repository.HelpRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.sparta.delivery.help.entity.Help.create;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;
    private final UserRepository userRepository;

    public ResponseDto createHelp(UUID userId, HelpRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_USER));
        helpRepository.save(create(user, request));
        return ResponseDto.of(ResponseCode.SUCC_HELP_CREATE);
    }
}
