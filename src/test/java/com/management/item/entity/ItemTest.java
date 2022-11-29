package com.management.item.entity;

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
class ItemTest {
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

    @DisplayName("Item Create 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class CreateTest {
        @DisplayName("성공 : 파라미터 값이 채워져서 생성된 경우")
        @Test
        void success() {
            // Given
            Car expectedCar = new Car("item create", 1000, 100, 4);
            transaction.begin();

            // When
            entityManager.persist(expectedCar);
            transaction.commit();
            entityManager.clear();

            // Then
            Car actualCar = entityManager.find(Car.class, expectedCar.getId());
            assertThat(actualCar).isEqualTo(expectedCar);
        }

        @DisplayName("실패 : 파라미터의 값이 null로 생성된 경우")
        @Test
        void failedByNullParameter() {
            // Given
            Car expectedCar = new Car(null, 1000, 100, 4);
            transaction.begin();

            // When
            Throwable actualResult = catchThrowable(() -> entityManager.persist(expectedCar));
            transaction.commit();

            // Then
            assertThat(actualResult).isInstanceOf(PersistenceException.class);
        }
    }

    @DisplayName("Item Read 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class ReadTest {
        @DisplayName("성공 : 존재하는 PK 값으로 조회한 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Car expectedCar = new Car("item read", 200, 10, 10);
            entityManager.persist(expectedCar);
            transaction.commit();

            // When
            entityManager.clear();
            Car actualCar = entityManager.find(Car.class, expectedCar.getId());

            // Then
            assertThat(actualCar).isEqualTo(expectedCar);
        }

        @DisplayName("실패 : 존재하지 않는 PK 값으로 조회한 경우")
        @Test
        void failedByNotExist() {
            // Given
            transaction.begin();
            Car expectedCar = new Car("item read fail", 3000, 30, 10);
            entityManager.persist(expectedCar);
            transaction.commit();

            // When
            entityManager.clear();
            Car actualCar = entityManager.find(Car.class, 0L);

            // Then
            assertThat(actualCar).isNull();
        }
    }

    @DisplayName("Item Update 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class UpdateTest {
        @DisplayName("성공 : 파라미터 값이 채워져서 수정된 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Car expectedCar = new Car("item update", 3000, 30, 10);
            entityManager.persist(expectedCar);
            expectedCar.setName("success");
            transaction.commit();

            // When
            entityManager.clear();
            Car actualCar = entityManager.find(Car.class, expectedCar.getId());

            // Then
            assertThat(actualCar).isEqualTo(expectedCar);
        }

        @DisplayName("실패 : 파라미터의 값이 null로 수정된 경우")
        @Test
        void failedByNullParameter() {
            // Given
            transaction.begin();
            Car expectedCar = new Car("item update fail", 200, 3, 40);
            entityManager.persist(expectedCar);
            transaction.commit();

            // When
            Throwable actualResult = catchThrowable(() -> {
                transaction.begin();
                expectedCar.setName(null);
                transaction.commit();
            });

            // Then
            assertThat(actualResult).isInstanceOf(PersistenceException.class);
        }
    }

    @DisplayName("Item Delete 테스트")
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    class DeleteTest {
        @DisplayName("성공 : 존재하는 PK 값으로 삭제한 경우")
        @Test
        void success() {
            // Given
            transaction.begin();
            Car car = new Car("item delete", 45000, 45, 45);
            entityManager.persist(car);
            transaction.commit();

            // When
            entityManager.clear();
            transaction.begin();
            Car expectedCar = entityManager.find(Car.class, car.getId());
            entityManager.remove(expectedCar);
            transaction.commit();

            // Then
            entityManager.clear();
            Car actualCar = entityManager.find(Car.class, car.getId());
            assertThat(actualCar).isNull();
        }

        @DisplayName("실패 : 존재하지 않는 값으로 삭제한 경우")
        @Test
        void failedByNullParameter() {
            // Given
            transaction.begin();
            Car car = new Car("item delete fail", 5000, 5, 5);
            entityManager.persist(car);
            transaction.commit();

            // When
            entityManager.clear();
            Throwable actualResult = catchThrowable(() -> entityManager.remove(car));

            // Then
            assertThat(actualResult).isInstanceOf(IllegalArgumentException.class);
        }
    }
}