package com.dojinyou.devcourse.springbootjpa.customer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("생성(creat)에 대한 테스트")
    class 생성에_대한_테스트 {

        @Nested
        @DisplayName("정상적인 Entity가 입력될 경우")
        class 정상적인_Entity가_입력될_경우 {

            @Test
            @DisplayName("정상적으로 저장되며, 반환한 데이터와 동일하다")
            void 정상적으로_저장되며_반환한_데이터와_동일하다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                customer.setId(1L);
                customer.setFirstName("dojin");
                customer.setLastName("you");

                // When
                CustomerEntity savedCustomer = repository.save(customer);

                //Then
                assertThat(savedCustomer).isNotNull()
                                         .isEqualTo(customer);
            }
        }

        @Nested
        @DisplayName("식별자가 없는 Entity가 입력될 경우")
        class 식별자가_없는_Entity가_입력될_경우 {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void 예외를_발생시킨다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                customer.setFirstName("dojin");
                customer.setLastName("you");

                // When
                Throwable throwable = catchThrowable(() -> repository.save(customer));

                //Then
                assertThat(throwable).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("읽기(read)에 대한 테스트")
    class 읽기에_대한_테스트 {

        @Nested
        @DisplayName("존재하는 식별자가 입력될 경우")
        class 존재하는_식별자가_입력될_경우 {

            @Test
            @DisplayName("데이터를 읽고 그 정보를 반환한다.")
            void 데이터를_읽고_그_정보를_반환한다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                long testId = 1L;
                customer.setId(testId);
                customer.setFirstName("dojin");
                customer.setLastName("you");
                CustomerEntity savedEntity = repository.save(customer);

                // When
                CustomerEntity foundCustomer = repository.findById(testId).get();

                //Then
                assertThat(foundCustomer).isNotNull()
                                         .isEqualTo(savedEntity);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 식별자가 입력될 경우")
        class 존재하지_않는_식별자가_입력될_경우 {

            @Test
            @DisplayName("Optional.Empty를 반환한다.")
            void Optional_Empty를_반환한다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                long testId = 1L;
                customer.setId(testId);
                customer.setFirstName("dojin");
                customer.setLastName("you");
                repository.save(customer);

                // When
                Optional<CustomerEntity> foundEntity = repository.findById(testId + 1);

                //Then
                assertThat(foundEntity).isNotNull();
                assertThat(foundEntity.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("수정(update)에 대한 테스트")
    class 수정에_대한_테스트 {

        @Nested
        @DisplayName("정상적인 수정이 이루어진 경우")
        class 정상적인_수정이_이루어진_경우 {

            @Test
            @Transactional
            @DisplayName("새로 읽은 데이터에 수정이 반영되어 있다.")
            void 새로_읽은_데이터에_수정이_반영되어_있다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                long testId = 1L;
                customer.setId(testId);
                customer.setFirstName("dojin");
                customer.setLastName("you");
                CustomerEntity savedEntity = repository.save(customer);

                // When
                savedEntity.setFirstName("dojiri");

                //Then
                CustomerEntity foundEntity = repository.findById(testId).get();
                assertThat(foundEntity).isNotNull()
                                       .isEqualTo(savedEntity);
            }
        }
    }

    @Nested
    @DisplayName("삭제(delete)에 대한 테스트")
    class 삭제에_대한_테스트 {

        @Nested
        @DisplayName("존재하는 데이터를 삭제하는 경우")
        class 존재하는_데이터를_삭제하는_경우 {

            @Test
            @DisplayName("데이터를 읽을 수 없다.")
            void 데이터를_읽을_수_없다() {
                // Given
                CustomerEntity customer = new CustomerEntity();
                long testId = 1L;
                customer.setId(testId);
                customer.setFirstName("dojin");
                customer.setLastName("you");
                CustomerEntity savedEntity = repository.save(customer);

                // When
                repository.delete(savedEntity);

                //Then
                Optional<CustomerEntity> foundEntity = repository.findById(testId);
                assertThat(foundEntity).isNotNull();
                assertThat(foundEntity.isEmpty()).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 데이터를 삭제하는 경우")
        class 존재하지_않는_데이터를_삭제하는_경우 {

            @Test
            @DisplayName("아무런 예외가 발생하지 않는다.")
            void 아무런_예외가_발생하지_않는다() {
                // Given
                CustomerEntity notSavedCustomer = new CustomerEntity();
                long testId = 1L;
                notSavedCustomer.setId(testId);
                notSavedCustomer.setFirstName("dojin");
                notSavedCustomer.setLastName("you");

                // When
                Throwable throwable = catchThrowable(() -> repository.delete(notSavedCustomer));

                //Then
                assertThat(throwable).isNull();
            }
        }
    }
}