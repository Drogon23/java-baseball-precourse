# 숫자 야구 게임
## 진행 방법
* 숫자 야구 게임 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 과제를 제출한다.

## 과제 제출 과정
* [과제 제출 방법](https://github.com/next-step/nextstep-docs/tree/master/precourse)

## 구현해야 하는 기능
- 컴퓨터가 세자리 숫자를 랜덤으로 생성한다.
- 사용자로부터 숫자를 입력받고 3자리 숫자인지 확인한다.
  - 숫자를 잘못 입력할 경우 에러메세지 출력 후 게임 재개
- 사용자의 숫자와 컴퓨터의 숫자를 비교해 알맞은 결과를 출력한다.
  - 해당 위치에 숫자가 맞을 경우 스트라이크
  - 숫자가 모두 불일치할 경우 낫싱
  - 숫자는 맞는데 해당 위치가 아닐 경우 
  - 위치와 숫자가 모두 맞는 경우 게임 종료
- 게임 종료 후 게임을 재개, 또는 완전 종료 중 선택한다.
