package kr.co.springbootjpaweeklymission.member.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
