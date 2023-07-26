package com.programmers.springbootjpa.ui.dto;

import com.programmers.springbootjpa.domain.Address;

public record MemberSaveRequest(
        String name,

        String password,

        Address address
) {
}
