package com.kdt.exercise.member.controller;

import com.kdt.exercise.ApiResponse;
import com.kdt.exercise.member.dto.MemberDto;
import com.kdt.exercise.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ApiResponse<String> notFoundHandle(NoSuchElementException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/members")
    public ApiResponse<Long> saveMember(@RequestBody MemberDto memberDto) {
        Long memberId = memberService.saveMember(memberDto);
        return ApiResponse.ok(memberId);
    }

    @PostMapping("/members/{id}")
    public ApiResponse<Long> updateMember(
            @PathVariable Long id,
            @RequestBody MemberDto memberDto
    ) {
        Long memberId = memberService.updateMember(id, memberDto);
        return ApiResponse.ok(memberId);
    }

    @GetMapping("/members/{id}")
    public ApiResponse<MemberDto> getMembers(
            @PathVariable Long id
    ) {
        MemberDto memberDto = memberService.getMember(id);
        return ApiResponse.ok(memberDto);
    }
}