본 프로젝트는 Oracle DB 기반의 식단 관리 및 레시피 추천 웹 애플리케이션입니다.
Phase3 콘솔 버전을 기반으로 하여, Phase4에서는 JSP + Servlet + Oracle + JDBC 구조로 웹 환경에서 동작하도록 구현했습니다.



1.실행 및 사용방법
먼저 Phase2와 Phase3가 완료되었다고 가정한다.

그 후 DBUtil.java 파일을 다음과 같이 수정한 후 저장한다.
private static final String DB_URL = "(자신의 접속 정보로 수정 (ex. jdbc:oracle:thin:@localhost:1521:orcl))"
private static final String DB_USER = "(DB를 구축할 때 쓴 아이디 입력)";
private static final String DB_PW = "(DB를 구축할 때 쓴 패스워드 입력)";

설정이 완료되었다면 Eclipse에서 File > Import > General > Existing Projects into Workspace 를 압축 해제된 Team18-Phase4 폴더로 선택한다.

이후 Package Explorer에 있는 Team18-Phase4을 우클릭 > Java Build Path > Classpath 클릭 후 Add External Jar > ojdbc10.jar를 추가한다.

Tomcat 10.1 서버 등록 후 실행

이제 login.jsp 파일을 실행하면 된다.

========
2-주요 기능
1) 로그인 / 회원 관리 (기본)
회원가입, 로그인, 로그아웃
로그인 성공 시 loginUser 세션에 사용자 정보 저장
로그인하지 않은 사용자는 주요 기능 페이지 접근 불가(세션 체크)

2) 식단 관리
오늘 섭취 칼로리 조회
주간 단백질 섭취량(x)
최근 30일 칼로리 합(x)
평균 영양소 비율(x)
식단 기록 추가

3) 레시피 관리 / 추천
레시피 전체 조회
레시피 상세 보기
카테고리별 보기
‘내 냉장고 재료로 가능한 레시피’ 추천(x)
고단백/저칼로리/저지방 조건 검색(x)

4) 재료 관리 (내 냉장고)
현재 보유 재료 목록 조회
재료 추가 (이름 → ingredient_num 자동 변환)
재료 삭제
재료 소비(감소) 기능
-수량을 입력하면 해당만큼 감소
-감소 후 0 이하가 되면 자동 삭제

5) 영양 목표 관리
최신 영양 목표 조회
새로운 목표 저장

6) 회원 정보 관리 (마이페이지)
이름 / 나이 / 키 / 몸무게 수정
회원 탈퇴
======

3.동시성 제어

재료를 여러 사용자가 동시에 수정할 때 발생할 수 있는
Race Condition(경쟁 상태) 를 해결하기 위해 다음을 적용

1)재고 수량 조회 시 레코드를 잠금

"SELECT quantity 
FROM PANTRY_ITEM
WHERE item_no = ? AND user_id = ?
FOR UPDATE"

2)트랜잭션 내에서 소비 로직 처리

-현재 수량 조회 (잠금 상태)
-감소할 양을 반영해 새로운 수량 계산
-새 수량이 0 이하면 레코드 삭제
-새 수량이 양수면 수량만 UPDATE
-모든 작업이 끝난 후 commit()
-중간에 오류 발생 시 rollback()

자세한 구현 코드는 PantryDAO.consumeItem(...) 메서드와
Team18-Additional_task1.txt 문서에 정리했습니다.

========

실행환경
Eclipse / Tomcat 10.1.x
데이터 베이스:Oracle 19c
JDBC 드라이버: ojdbc10.jar
JSP / Servlet (jakarta.servlet)
