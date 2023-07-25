package kr.co.springbootjpaweeklymission.member.application;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import kr.co.springbootjpaweeklymission.member.domain.entity.Member;
import kr.co.springbootjpaweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
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
