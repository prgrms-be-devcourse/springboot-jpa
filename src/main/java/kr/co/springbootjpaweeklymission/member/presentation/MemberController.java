package kr.co.springbootjpaweeklymission.member.presentation;

import kr.co.springbootjpaweeklymission.member.application.MemberService;
import kr.co.springbootjpaweeklymission.member.dto.MemberCreatorRequest;
import kr.co.springbootjpaweeklymission.member.dto.MemberReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> createMember(@RequestBody @Validated MemberCreatorRequest creatorRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.createMember(creatorRequest));
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberReadResponse> getMember(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberService.getMember(email));
    }
}
