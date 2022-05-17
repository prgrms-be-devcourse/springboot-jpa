package com.prgms.springbootjpa.domain.order.vo;

import static com.prgms.springbootjpa.exception.ExceptionMessage.NICK_NAME_LENGTH_EXP_MSG;

import com.prgms.springbootjpa.exception.InvalidNameLengthException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NickName {

    private static final int MIN = 1;
    private static final int MAX = 30;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    protected NickName() {
    }

    public NickName(String nickName) {
        validateNickNameFormat(nickName);
        this.nickName = nickName;
    }

    private void validateNickNameFormat(String nickName) {
        if (nickName.length() < MIN || nickName.length() > MAX) {
            throw new InvalidNameLengthException(NICK_NAME_LENGTH_EXP_MSG);
        }
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NickName nickName1 = (NickName) o;
        return getNickName().equals(nickName1.getNickName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickName());
    }
}
