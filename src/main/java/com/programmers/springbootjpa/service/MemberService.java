package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.dto.request.CreateRequestDto;
import com.programmers.springbootjpa.dto.response.ResponseDto;
import com.programmers.springbootjpa.entity.Member;
import com.programmers.springbootjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(CreateRequestDto createRequestDto) {
        Member member = createRequestDto.toEntity();
        memberRepository.save(member);
    }

    public List<ResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(ResponseDto::fromEntity)
                .toList();
    }

    public ResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 고객이 존재하지 않습니다."));

        return ResponseDto.fromEntity(member);
    }

    public void deleteById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NoSuchElementException("삭제하려는 고객을 찾지 못했습니다.");
        }

        memberRepository.deleteById(id);
    }
}
