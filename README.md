# 스프링부트 JPA 위클리 미션

# Github

https://github.com/SY97P/springboot-jpa.git

## 📌 과제 설명

- 페어프로그래밍

## 👩‍💻 요구 사항과 구현 내용

- [x]  미션1 : 단일 엔티티(Customer)를 이용한 CRUD를 구현
- [x]  미션2 : 영속성 컨텍스트 생명주기 실습
- [x]  미션3 : 4-2. 연관관계매핑(order, order_item, item의 연관관계 매핑 실습)

## ✅ 피드백 반영사항

## 📮 팀미팅 피드백

- 빌더 패턴 빠진 속성 방어
  - Assert.notNull() 로 방어
- nullable 생성자 파라미터
  - 컬럼에 nullable = false
- 테이블 이름 컨벤션
  - 모두 복수형
- 영속성 전이
  - 영속, 삭제 시 부모-관계 설정한 Order, OrderItem에만 의도한 동작 수행된다고 판단
  - Cascade=All + 고아객체 고려 X

## 📮 1차 피드백

### Validation 모듈

- validation message 부재
  - [message.properties](http://message.properties) 적용 가능성
- `@Email` 어노테이션
  - 어노테이션 적용사항과 프로그램 요구사항이 다를 수 있음
  - 확인하고 사용하는 편이 좋다
- 적절한 어노테이션
  - name에는 `@Pattern` 으로 정규식 적용
  - age 에는 `@Min`, `@Max`, `@Positive` 로 유효값 선언

### 커스텀 어노테이션

- 테스트 용 어노테이션은 테스트 패키지 내부에 선언해야 함.
  - main 패키지에 선언하면 테스트용 어노테이션 import 불가

    ```java
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @DataJpaTest
    @ActiveProfiles("test")
    @TestPropertySource(locations = "classpath:application-test.yaml")
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public @interface JpaTest {
    }
    ```


### 컨벤션

- private / public 위치 컨벤션
  - private, public 을 구분해서 두는 편이 좋음
  - 대부분 public 이 위, private이 아래
- lombok, jakarta 어노테이션 위치
  - 롬복 @EqualsAndHashCode 어노테이션은 동등성 비교에 모든 필드가 강제되기 때문에 의도한 대로 프로그램이 동작하지 않을 가능성 존재
- 원시값 포장 필드 변수명
  - 원시값 포장 클래스 이름과 다른 이름으로 지어야 함.

### Exception

- `RuntimeException` 을 그대로 보내기보단, 좀 더 구체적인 예외 던지기
  - JpaRepository에서 결과 없을땐 `NoSuchElementException`
  - 상황에 맞는 적절한 예외는 대부분 존재함.

### JPA

- Entity Id 가 순차적으로 증가하는 숫자값일 때 단점은?
  1. 문자열로 구성된 ID 보다 예측 쉬움 → `보안 이슈`
  2. 데이터베이스 병렬 구성 시 ID 값 `중복 혹은 충돌` 가능성
- Delete 내부 구현
  1. 일단 엔티티 null 체크
  2. 엔티티가 새로 생성되어야 하는지 체크
  3. 프록시로 타입 받아, entityManager (영속성 컨텍스트)에서 찾음
  4. 엔티티가 영속성 컨텍스트에 존재하지 않으면 아무 동작 안 함
  5. 영속성 컨텍스트에 엔티티가 있으면 영속, 준영속 구분없이 다 가져와서 제거

    ```java
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void delete(T entity) {
    
    	Assert.notNull(entity, "Entity must not be null");
    
    	if (entityInformation.isNew(entity)) {
    		return;
    	}
    
    	Class<?> type = ProxyUtils.getUserClass(entity);
    
    	T existing = (T) em.find(type, entityInformation.getId(entity));
    
    	// if the entity to be deleted doesn't exist, delete is a NOOP
    	if (existing == null) {
    		return;
    	}
    
    	em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
    ```

- 읽기 전용 트랜잭션 장점
  1. 스칼라 타입으로 조회

      ```java
      select o.id, o.name from Order p;
      ```

    - 조회 결과가 엔티티가 아닌, 스칼라 타입으로 반환되도록 쿼리 작성
    - 조회 결과가 엔티티가 아니므로, 영속성 컨텍스트가 결과를 관리하지 않음
    - 메모리 절약
  2. 읽기 전용 쿼리 힌트 사용
    - hibernate 전용 힌트 readOnly 이용해 엔티티를 읽기 전용으로 조회
    - 읽기전용이기 때문에 영속성 컨텍스트는 스냅샷 보관 X
    - 메모리 절약
  3. 읽기 전용 트랜잭션 사용

      ```java
      @Transactional(readOnly = true)
      ```

    - SpringFramework 트랜잭션 읽기 전용 모드 설정
    - SpringFramework가 hibernate 세션 플러시 모드를 manual로 설정
      - 강제로 Flush 호출하지 않는 한, flush되지 않음
      - **트랜잭션을 commit 하더라도 영속성 컨텍스트가 flush 되지 않음**
      - **엔티티 등록, 수정, 삭제 동작하지 않음**
      - 읽기 전용이므로, 영속성 컨텍스트가 변경감지를 위한 스냅샷 관리를 하지 않으므로, 메모리 성능 향상
  - Service 레이어 전체에 3번 읽기 전용 트랜잭션 선언 + 조회 이외의 트랜잭션에 메소드 레벨 `@Transcational` 선언
- `@Embedded` - `@Embeddable`
  - Entity에서  VO 필드에 `@Embedded`
  - VO 클래스에 `@Embeddable`
  - Embedded 클래스에 `@Column`으로 Entity설정해도 적용되지 않음
    - VO 클래스 내부 필드에 `@Column(nullable = false)` 이런 식으로 선언해야 함
- 양방향 연관관계 편의 메소드
  - 편의 메소드는 객체지향에 따라 Aggregate Root 엔티티에 선언하는 것이 좋음
  - 최대한 한 쪽에만 선언하고, 양방향 모두 매핑이 가능하도록 하는 것
- Dialect (방언)
  - 개념
    - DB 별로 표준 ANSI SQL 과 다르거나 추가된 문법, 함수가 있다.
    - JPA는 특정 DB에 종속하지 않고, JPQL을 이용해 직접 SQL을 작성하고 실행하는 추상화 레벨을 제공하기 때문에 별도 Dialect 설정으로 JPA가 특정 DBMS에 맞는 쿼리 생성
  - 단점
    - SpringBoot에서는 연결된 DB에 맞게 자동으로 지정되므로, 수동 설정할 필요 없음
    - 호환되지 않은 버전 Dialect 설정으로 의도치 않은 결과가 발생할 수 있음
      - 트랜잭션 동작하지 않는 현상
      - 버전 차이로 인해 DateTime 타입에서 밀리세컨드 저장이 안 되는 현상
- OSIV
  - API 응답 전송까지 영속성 컨텍스트와 DB 커넥션을 유지
  - 영속성 컨텍스트 범위가 View 레벨까지 이어짐
    - View 레벨에서 지연로딩 등 기능 이용 가능
    - View 레벨에서도 엔티티 변경 감지 동작
  - open-in-view=false
    - 영속성 컨텍스트는 View 레벨까지 유지
      - 영속성 컨텍스트 생성, 종료 → View 레벨
    - View 레벨은 수정 불가 + 서비스 레벨은 수정 가능 but MVC 전반에 영속성 컨텍스트 유지
- VO
  - Entity 내부에서 사용하는 VO 객체를 외부에서 사용해도 되는가?
    - 좋지 않은 방법이다.
    - 외부에서 VO에 대해 필요하지 않은 의존성이 생길 수 있다.
      - 패키지 구조로 쉽게 확인 가능
    - entity 내부에서 VO에 대한 실제 값을 getter로 만들어 반환해주는 방식으로 사용하는 것이 좋다.