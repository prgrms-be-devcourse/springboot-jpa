package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import kr.co.springbootjpaweeklymission.member.dto.MemberDetailResponse;
import kr.co.springbootjpaweeklymission.member.dto.MemberSimpleResponse;
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
    public Long createMember(MemberCreatorRequest creatorRequest) {
        isEmail(creatorRequest.getEmail());
        isCellPhone(creatorRequest.getCellPhone());
        final Member member = creatorRequest.toMember();

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
