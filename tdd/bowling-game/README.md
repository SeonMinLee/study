# 1. 개요
---
TDD를 설명하기 위한 예제로 많이 쓰이는 Bowling Game을 직접 구현해보는 것이 목적

# 2. 목적
- [ ] 게임 클래스
    - [ ] 게임은 roll, score 함수를 갖는다.
    - [ ] 게임은 10개의 프레임을 갖는다.
    
- [ ] 프레임 클래스
    - [ ] 프레임은 1..2개의 roll을 갖는다
    - [ ] 10번 프레임은 예외를 갖는다(1..2 roll을 갖는것이 아니라 2..3roll을 갖는다).
    - [ ] 객체지향에서 이런 예외를 어떻게 표현하나??
    
** score 함수의 알고리즘  
frame 수 만큼 loop를 돌면서 각 frame의 점수를 합산할 것이다.  
> 본문: The score function must iterate through all the frames, and calculate all their socres.

frame은 roll수 만큼 loop를 돌면서 점수를 계산할 것이다. strike, spare를 위해서 look ahead roll을 해야한다.  
> 본문: The score for a spare or a strike depends on thr fraem's successor  
