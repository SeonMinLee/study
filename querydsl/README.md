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


