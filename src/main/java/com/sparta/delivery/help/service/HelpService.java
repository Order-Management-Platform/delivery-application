package com.sparta.delivery.help.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.help.dto.HelpRequestDto;
import com.sparta.delivery.help.dto.HelpResponseDto;
import com.sparta.delivery.help.entity.Help;
import com.sparta.delivery.help.repository.HelpRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sparta.delivery.help.entity.Help.create;

@Service
@RequiredArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;
    private final UserRepository userRepository;

    // 문의 생성 로직
    @Transactional
    public ResponseDto createHelp(UUID userId, HelpRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_USER));
        helpRepository.save(create(user, request));
        return ResponseDto.of(ResponseCode.SUCC_HELP_CREATE);
    }

    // 문의 전체 조회 로직
    public ResponsePageDto<HelpResponseDto> getHelp(int page, int size, String sort, boolean asc) {
        Pageable pageable = createCustomPageable(page, size, sort, asc);

        Page<Help> helpList = helpRepository.findAll(pageable);
        Page<HelpResponseDto> helpResponseDtoPage = helpList.map(HelpResponseDto::of);
        return ResponsePageDto.of(ResponseCode.SUCC_HELP_GET, helpResponseDtoPage);
    }

    // 문의 수정 로직
    @Transactional
    public ResponseDto updateHelp(UUID helpId, HelpRequestDto request) {
        Help help = helpRepository.findById(helpId).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_HELP));
        String title = help.getTitle();
        String question = help.getQuestion();
        String answer = help.getAnswer();

        if (request.getTitle() != null)
            title = request.getTitle();
        if (request.getQuestion() != null)
            question = request.getQuestion();
        if (request.getAnswer() != null)
            answer = request.getAnswer();

        help.update(title, question, answer);

        return ResponseDto.of(ResponseCode.SUCC_HELP_UPDATE);
    }

    // 문의 삭제 로직
    @Transactional
    public ResponseDto deleteHelp(UUID helpId, UUID userId) {
        Help help = helpRepository.findById(helpId).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_HELP));
        help.markDeleted(userId);
        return ResponseDto.of(ResponseCode.SUCC_HELP_DELETE);
    }

    // Custom Pageable를 만드는 로직
    protected Pageable createCustomPageable(int page, int size, String sort, boolean asc) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(direction, sort);
        return PageRequest.of(page, size, sortBy);
    }
}
