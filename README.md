# 개요
매장 예약 서비스 구현

Use : Spring, Jpa, Mysql, Swagger, Jwt, Mailgun

Goal : 사용자와 매장 관리자를 위한 매장 예약 서비스 Backend 구성

참고 : Mailgun SecretKey 노출 위험으로 설정 파일은 공유 안함

## 공통(사용자, 매장관리자)
- [x] 이메일을 통해서 인증번호를 통한 회원 가입 기능 구현 > Mailgun 사용
- [x] 로그인 토큰 발행 > Jwt

## 사용자(Customer)
### 예약 관련
- [x] 예약 생성
- [x] 예약 상세 정보 조회
- [x] 예약 정보 수정
- [x] 예약 취소
### 체크인
- [x] 체크인 생성(예약 ID기반)
### 리뷰 관련
- [x] 리뷰 생성
- [x] 리뷰 상세 정보 조회
- [x] 리뷰 수정
- [x] 리뷰 삭제
- [x] 특정 매장의 리뷰 목록 조회

## 매장 관리자(Manager)
### 매장 관리
- [x] 상점등록
- [x] 매장 목록 조회 > Pageable로 구현
- [x] 매장 상세 정보 조회
- [x] 상점 정보 수정
- [x] 상점 삭제

# 마무리
기능 별 모듈 별도 설정 하는 부분과 DB 모델 구성하는 부분은 아직 익숙하지 않았다.

진행하면서 쿼리문도 코드 내에서 사용해야 될 일이 있었는데 더 좋은 쿼리를 위해 별도 공부를 해야겠다는 생각을 했다.

각 기능별 테스트 코드 작성 하는 부분이 아직 익숙하지 않지만 테스트 코드를 통해 잘못 된 부분을 수정 하는 것도 많은 공부가 됐다.
