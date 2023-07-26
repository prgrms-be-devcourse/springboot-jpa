package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.exception.EntityNotFoundException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import kr.co.springbootjpaweeklymission.member.dto.MemberReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public MemberReadResponse getMember(String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorResult.NOT_FOUND_MEMBER));
        return MemberReadResponse.toDto(member);
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
