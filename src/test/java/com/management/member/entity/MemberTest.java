package com.management.member.entity;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestClassOrder(ClassOrderer.DisplayName.class)
class MemberTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;
    EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
    }

    @DisplayName("Member Create 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class CreateTest {
        @DisplayName("성공 : description을 제외한 파라미터 값이 채워져서 생성된 경우")
        @Test
        void success() {
            // Given
            transaction.begin();

            // When
            Member expectedMember = new Member("create", "create", 25, "create", null);
            entityManager.persist(expectedMember);
            transaction.commit();
            entityManager.clear();

            // Then
            Member actualMember = entityManager.find(Member.class, expectedMember.getId());
            assertThat(actualMember).isEqualTo(expectedMember);
        }

        @DisplayName("실패 : 파라미터 들의 값이 null로 생성된 경우")
        @Test
        void failedByNullParameter() {
            // Given
            transaction.begin();

            // When
            Member member = new Member(null, null, 25, "create", "create");
            Throwable actualResult = catchThrowable(() -> entityManager.persist(member));
            transaction.commit();

            // Then
            assertThat(actualResult).isInstanceOf(PersistenceException.class);
        }
    }

    @DisplayName("Member Read 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class ReadTest {
        @DisplayName("성공 : 존재하는 PK 값으로 조회한 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Member expectedMember = new Member("find1", "find1", 25, "find1", null);
            entityManager.persist(expectedMember);
            transaction.commit();

            // When
            entityManager.clear();
            Member actualMember = entityManager.find(Member.class, expectedMember.getId());

            // Then
            assertThat(actualMember).isEqualTo(expectedMember);
        }

        @DisplayName("실패 : 존재하지 않는 PK 값으로 조회한 경우")
        @Test
        void failedByNotExist() {
            // Given
            transaction.begin();
            Member expectedMember = new Member("find2", "find2", 25, "find2", null);
            entityManager.persist(expectedMember);
            transaction.commit();

            // When
            entityManager.clear();
            Member actualMember = entityManager.find(Member.class, 0L);

            // Then
            assertThat(actualMember).isNull();
        }
    }

    @DisplayName("Member Update 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class UpdateTest {
        @DisplayName("성공 : description을 제외한 파라미터 값이 채워져서 수정된 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Member expectedMember = new Member("update1", "update1", 25, "update1", "update1");
            entityManager.persist(expectedMember);
            expectedMember.setName("kate");
            expectedMember.setNickName("mooomoo");
            expectedMember.setAge(15);
            expectedMember.setAddress("busan");
            expectedMember.setDescription(null);
            transaction.commit();

            // When
            entityManager.clear();
            Member actualMember = entityManager.find(Member.class, expectedMember.getId());

            // Then
            assertThat(actualMember).isEqualTo(expectedMember);
        }

        @DisplayName("실패 : 파라미터 들의 값이 null로 수정된 경우")
        @Test
        void failedByNullParameter() {
            // Given
            transaction.begin();
            Member expectedMember = new Member("update2", "update2", 25, "update2", "update2");
            entityManager.persist(expectedMember);
            transaction.commit();

            // When
            Throwable actualResult = catchThrowable(() -> {
                transaction.begin();
                expectedMember.setName(null);
                expectedMember.setNickName(null);
                expectedMember.setAge(15);
                expectedMember.setAddress(null);
                expectedMember.setDescription(null);
                transaction.commit();
            });

            // Then
            assertThat(actualResult).isInstanceOf(PersistenceException.class);
        }
    }

    @DisplayName("Member Delete 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class DeleteTest {
        @DisplayName("성공 : 존재하는 PK 값으로 삭제한 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Member member = new Member("delete1", "delete1", 25, "delete1", null);
            entityManager.persist(member);
            transaction.commit();

            // When
            entityManager.clear();
            transaction.begin();
            Member expectedMember = entityManager.find(Member.class, member.getId());
            entityManager.remove(expectedMember);
            transaction.commit();

            // Then
            entityManager.clear();
            Member actualMember = entityManager.find(Member.class, member.getId());
            assertThat(actualMember).isNull();
        }

        @DisplayName("실패 : 존재하지 않는 값으로 삭제한 경우")
        @Test
        void failedByNullParameter() {
            // Given
            transaction.begin();
            Member member = new Member("delete1", "delete1", 25, "delete1", null);
            entityManager.persist(member);
            transaction.commit();

            // When
            entityManager.clear();
            Throwable actualResult = catchThrowable(() -> entityManager.remove(member));

            // Then
            assertThat(actualResult).isInstanceOf(IllegalArgumentException.class);
        }
    }
}