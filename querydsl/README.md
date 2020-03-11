# Querydsl
Querydsl 공부 정리

### Q타입 사용법
```java
QMember qMember = new QMember("m"); // 별칭 직접 지정
QMember qMember = QMember.member; // 기본 인스턴스 사용
```

### 검색 조건
```java
member.username.eq("jayden")        // username = 'jayden'
member.username.nq("jayden")        // username != 'jayden'
member.username.eq("jayden").not()  // username != 'jayden'

member.username.isNotNull()         // username is not null

member.age.in(10, 20)               // age in (10, 20)
member.age.notIn(10, 20)            // age not in (10, 20)
member.age.between(10, 30)          // between 10, 30

member.age.goe(30)                  // age >= 30
member.age.gt(30)                   // age > 30
member.age.loe(30)                  // age <= 30
member.age.lt(30)                   // age < 30

member.username.like("jayden%")     // like 검색
member.username.contains("jayden")  // link '%jayden%' 검색
member.username.startWith("jayden")  // link 'jayden%' 검색
...
```

### 결과 조회
- `fetch()`: 리스트 조회, 데이터 없으면 빈 리스트 반환
- `fetchOne()`: 단 건 조회
  - 결과가 없으면: `null`
  - 결과가 둘 이상이면: `com.querydsl.core.NonUniqueResultException`
- `fetchFirst()`: `limit(1).fetchOne()`
- `fetchResults()`: 페이징 정보 포함, total count 쿼리 추가 실행
- `fetchCount()`: count 쿼리로 변경해서 count 수 조회

[샘플코드](src/test/java/study/querydsl/QuerydslBasicTest.java)