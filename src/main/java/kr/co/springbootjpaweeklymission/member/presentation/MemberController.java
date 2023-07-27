package kr.co.springbootjpaweeklymission.member.presentation;

import kr.co.springbootjpaweeklymission.member.application.MemberService;
import kr.co.springbootjpaweeklymission.member.dto.request.MemberPutRequest;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberDetailResponse;
import kr.co.springbootjpaweeklymission.member.dto.response.MemberSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> createMember(@RequestBody @Validated MemberPutRequest putRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.createMember(putRequest));
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberDetailResponse> getMember(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMember(email));
    }

    @GetMapping
    public ResponseEntity<List<MemberSimpleResponse>> getMembers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMembers());
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Long> updateMember(@PathVariable Long memberId, @Validated MemberPutRequest putRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.updateMember(memberId, putRequest));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long memberId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.deleteMember(memberId));
    }
}
