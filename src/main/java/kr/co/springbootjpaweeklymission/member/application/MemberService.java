package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.request.MemberPutRequest;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberDetailResponse;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(MemberPutRequest putRequest) {
        isEmail(putRequest.getEmail());
        isCellPhone(putRequest.getCellPhone());
        final Member member = putRequest.toMember();

        return memberRepository.save(member).getMemberId();
    }

    public MemberDetailResponse getMember(String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));

        return MemberDetailResponse.toDto(member);
    }

    public List<MemberSimpleResponse> getMembers() {
        final List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberSimpleResponse::toDto)
                .toList();
    }

    @Transactional
    public Long updateMember(Long memberId, MemberPutRequest putRequest) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));
        member.updateMemberInformation(putRequest);

        return memberId;
    }

    @Transactional
    public Long deleteMember(Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));
        memberRepository.delete(member);

        return memberId;
    }

    private void isEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateException(ErrorResult.DUPLICATED_EMAIL);
        }
    }

    private void isCellPhone(String cellPhone) {
        if (memberRepository.existsByCellPhone(cellPhone)) {
            throw new DuplicateException(ErrorResult.DUPLICATED_CELL_PHONE);
        }
    }
}
