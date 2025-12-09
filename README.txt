🥗 식단 관리 및 레시피 추천 시스템
데이터베이스 Team Project
TEAM 18



- 프로젝트 소개:
이 앱은 Oracle DB를 기반으로 하여, 사용자의 건강한 식습관을 위해 일일 식단 기록, 영양 목표 설정, 냉장고 재료 관리, 그리고 보유 재료 기반 레시피 추천 기능을 제공하는 웹 앱이다. Phase3 콘솔 버전을 기반으로 하여, Phase4에서는 JSP + Servlet + Oracle + JDBC 구조로 웹 환경에서 동작하도록 구현했다.



- 주요 기능
1. 회원 관리
회원가입/로그인: 아이디 중복 체크 및 유효성 검사, 세션 기반 로그인 처리.
마이페이지: 내 정보 수정 및 회원 탈퇴 기능.
(회원 탈퇴 시 참조 무결성을 위해 연관된 모든 데이터(식단, 냉장고, 목표)가 트랜잭션 처리되어 안전하게 일괄 삭제됨.)

2. 식단 관리
식단 기록: 날짜별 섭취한 음식과 영양소(칼로리, 탄단지 등) 기록.
통계 대시보드: 주간 일평균 칼로리 / 주간 단백질 섭취량 / 최근 30일 총 칼로리 / 전체 평균 영양소 비율 표시.
목표 달성도 그래프: 설정한 목표 대비 섭취량을 프로그레스 바 형식으로 표시.
검색 및 페이징: 날짜별 검색 기능과 기록이 많을 경우 페이지별로 나눠서 볼 수 있는 기능.

3. 레시피 추천
전체 조회: DB에 있는 전체 레시피를 조회.
상세 조회: 레시피를 레시피의 조리법, 영양 정보, 필요 재료 확인 가능.
스마트 추천: 현재 내가 가진 재료만으로 만들 수 있는 레시피 추천.
조건 검색: 고단백, 저칼로리, 저지방 등 기준에 따라서 레시피 필터링.

4. 재료 관리
냉장고 현황: 보유한 재료의 총 종류 수와 수량 정보 제공, 보유 중인 재료를 목록 형식으로 보여줌.
소비 및 관리: 재료 수량 차감(소비) 및 삭제 기능.

5. 영양 목표 관리
개인별 일일 목표 칼로리 및 영양소(탄/단/지) 설정 기능.
설정된 목표는 식단 관리 페이지의 달성도 그래프 계산에 실시간으로 반영됨.



- 실행 방법
0. 사전 DB 구성
먼저 Phase2와 Phase3에서 DB 구성이 완료되었으며, 실행에 필요한 환경 구성이 되어있다고 가정한다.

1. 프로젝트 불러오기
이 저장소를 Clone 하거나 Download ZIP 하여 압축을 푼다.
Eclipse 실행 > File > Import > General > Existing Projects into Workspace 선택한다.
다운로드한 폴더를 선택하여 프로젝트를 불러온다.
그 후 Package Explorer에 있는 프로젝트를 우클릭 > Java Build Path > Classpath 클릭 후 Add External Jar > ojdbc10.jar을 추가한다.
그 후 Tomcat 10.1 서버를 등록한다.

3. 데이터베이스 설정
src/main/java/com/team18/util/DBUtil.java 파일을 열어서 본인의 DB 정보로 수정한 후 저장한다.

public class DBUtil {
    private static final String DB_URL = "(자신의 접속 정보로 수정)"; // ex. jdbc:oracle:thin:@localhost:1521:orcl
    private static final String DB_USER = "(DB를 구축할 때 쓴 아이디 입력)"; // 예: TEAM18
    private static final String DB_PW = "(DB를 구축할 때 쓴 패스워드 입력)"; // 예: 1234
    // ...
}

4. 실행
src/main/webapp/login.jsp 파일을 우클릭한다.
그 후 Run As > Run on Server 클릭한다.
브라우저에서 웹 페이지가 열리는지 확인한다.



- 동시성 제어 관련 
재료를 여러 사용자가 동시에 수정할 때 발생할 수 있는 Race Condition(경쟁 상태) 를 해결하기 위해 다음을 적용하였다.

1) 재고 수량 조회 시 레코드를 잠금
"SELECT quantity FROM PANTRY_ITEM WHERE item_no = ? AND user_id = ? FOR UPDATE"

2) 트랜잭션 내에서 소비 로직 처리
- 현재 수량 조회 (잠금 상태)
- 감소할 양을 반영해 새로운 수량 계산
- 새 수량이 0 이하면 레코드 삭제
- 새 수량이 양수면 수량만 UPDATE
- 모든 작업이 끝난 후 commit()
- 중간에 오류 발생 시 rollback()

자세한 설명은 PantryDAO.consumeItem(...) 메서드와 Team18-Additional_task1.txt 문서에 정리했다.



- 개발 환경
언어: Java
웹 서버: Apache Tomcat 10.1 (Jakarta EE)
DB: Oracle 19c
IDE: Eclipse (Dynamic Web Project)
버전 관리: Git / GitHub



- 데모 영상 링크
https://youtu.be/cf1SqYIV7vg?si=MxXdHi96M2XadhLe




- 제작자
Team 18
김명진, 정동호, 정서완
