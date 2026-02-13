해당 문서는 gemini-2.5-flash 로 자동 번역되었습니다.<br>정확한 내용은 여기서 확인해주세요: [English Document](https://github.com/seyun4047/drone-platform-monitoring-server/blob/main/README.md)

---

# 드론 플랫폼 모니터링 서버

---
## 작동 방식
모니터링 서버는 독립적인(로컬) 서비스로 실행되며, 메인 [드론 플랫폼 서버](https://github.com/seyun4047/drone-platform-server)에서 이미 실행 중인 Redis 및 MySQL 인스턴스에 연결됩니다.

이 시스템은 Redis ZSET 기반의 심박(하트비트) 모니터링 메커니즘을 사용하여 <br>심박 전송을 중단한 드론을 자동으로 감지하고 연결을 해제합니다.

---
## 모니터링 대상
이 서비스의 주요 역할은 다음과 같은 비정상적인 드론 상태를 감지하는 것입니다:
- 지정된 기간 동안 비활성 상태였던 드론
- 심박 신호 전송을 중단한 드론
- 상태 업데이트 전송에 실패한 드론

---
## 이점
이러한 경우를 식별하고 처리함으로써 모니터링 서버는 다음을 돕습니다:
- 불필요한 서버 자원 소모 방지
- 데이터베이스 부하 감소
- 전반적인 플랫폼 안정성 유지

---

## 사용법
```bash
# 환경 변수 로드
set -a
source dev.env
set +a
```
```bash
# 빌드
./gradlew build
```
```bash
# 실행
./gradlew bootRun
```

---
## 모니터링 흐름
모니터링 프로세스는 아래 흐름을 따릅니다:
|모니터링 서버|
|---|
|<img width="450" alt="Untitled diagram-2026-02-11-173920" src="https://github.com/user-attachments/assets/adbbeee5-7544-46c0-a276-0a04aae3e303" />|

---

---

# 프로젝트 개요
# 제조사 독립형 드론 플랫폼

---
이것은 **제조사 독립형 통합 드론 모니터링 플랫폼**입니다.

다양한 드론을 단일 환경에서 관리할 수 있도록 설계되었으며,
**고성능 전문 드론과 상업적으로 이용 가능한 취미용 카메라 드론 모두**를
인명 구조 및 재난 대응에 활용할 수 있도록 합니다.

---

## 프로젝트 구조

이 플랫폼은 여러 개의 독립적인 저장소로 구성됩니다:

| Component | Description | Repository |
|---------|---------------------------------------------------|-------------------------------------------------------------------------|
| Server | 핵심 드론 플랫폼 서버 (API, 인증, 텔레메트리) | [GitHub](https://github.com/seyun4047/drone-platform-server) |
| Monitoring Server | 실시간 드론 상태 점검 모니터링 서비스 | [GitHub](https://seyun4047/drone-platform-monitoring-server) |
| Drone Data Tester | 드론 텔레메트리 및 데이터 시뮬레이션 테스트 클라이언트 | [GitHub](https://github.com/seyun4047/drone-platform-trans-tester) |
| Drone Client | 드론 데이터 수집, 전송 및 분석 | [GitHub](https://github.com/seyun4047/drone-platform-client) |
| Docs | 플랫폼 문서, API | [GitHub](https://github.com/seyun4047/drone-platform-docs)|

---

## 배경

커스텀 드론, 상업용 드론, 소비자용 드론은 기본적인 제어 메커니즘을 공유하지만,
실제 환경에서의 작동 방식과 **명령 및 제어 구조**는 상당히 다릅니다.

실제로 드론은 종종 다음과 같은 요소에 크게 의존하는 도구로 활용됩니다:
- 특정 장비
- 고도로 훈련된 인력

최근 많은 기관과 기업이 AI 기술과 통합된 드론 시스템을 구축하려 시도했습니다.
하지만 이러한 시스템에는 명확한 한계가 있습니다. 일반적으로 특정 드론 모델을 튜닝하거나 단일 유형의 맞춤형 드론을 운영하는 방식에 의존하여, 전문 인력과 독점 기술에 대한 강한 의존성을 초래합니다.

이러한 의존성은 특히 **인명 구조 및 재난 대응 작전**에서 매우 중요합니다.

---
## 프로젝트 목표
- 인명 구조 및 재난 대응 작전을 지원하는 제조사 독립형 드론 모니터링 플랫폼.

---
## 목표

- 드론 모델이나 제조사에 관계없이 배포 가능한 드론 모니터링 및 관리 시스템
- 복잡한 제어 절차 없이 현장에 즉시 배포할 수 있는 시스템
- 특정 드론 하드웨어의 성능 능력에 의존하지 않는 시스템
- 비전문 드론 취미 사용자도 비상 상황에 효과적으로 기여할 수 있는 시스템

---

## 예상 효과

인명 구조 및 재난 대응 시나리오에서, 전문 장비나 구조팀이 현장에 도착하기 전에
누구나 조작할 수 있는 모든 드론을 즉시 배치하여:
- 피해자 평가
- 위험 식별
- 피해 규모 추정

이러한 중요한 **골든 타임**을 확보함으로써 시스템은 더 빠른 의사 결정을 가능하게 하고 고급 구조 자원을 더 효과적으로 배치하여 궁극적으로 더 정교하고 영향력 있는 드론 지원 비상 대응 시스템으로 이어질 수 있습니다.

---

## 시스템 아키텍처

### 전체 시스템 아키텍처
<img height="900" alt="AWS Upload Presigned URL-2026-02-13-154944" src="https://github.com/user-attachments/assets/b3c05ffd-6a25-47c2-9473-83fec588129b" />


---

## 핵심 시스템 흐름

| Auth Logic | Control Data From Drone |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
| <img width="450" alt="Redis Token Connection Flow-2026-02-01-182619" src="https://github.com/user-attachments/assets/cf0e6a9e-eeae-4525-aaf1-198c98e61c90" /> | <img width="450" alt="Redis Token Connection Flow-2026-02-01-182708" src="https://github.com/user-attachments/assets/a344e0c5-b12a-45ab-951c-0cefcc87bf2b" />
 |
| **Redis 기반 인증 및 연결 제어 흐름.** | **인증 후 제어 및 텔레메트리 데이터 처리.** |

| Token Validation | Monitoring Server |
|:---------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/456dc993-64a0-4ac8-9138-0f5446aaad07" width="450"/> |<img width="450" alt="Untitled diagram-2026-02-11-173920" src="https://github.com/user-attachments/assets/6eea1ba2-663d-4bf1-be1d-c729e3bda2f7" />|
| **들어오는 드론 데이터에 대한 Redis 토큰 유효성 검사.** | **주기적인 드론 연결 상태 모니터링.** |

| Back-End <-> Front-End |
|:---:|
| <img height="700" alt="AWS Upload Presigned URL-2026-02-13-144904" src="https://github.com/user-attachments/assets/4e956658-5ef2-4c1d-972d-ea669aa09b67" /> |
| **백엔드 서버와 프론트엔드 대시보드 간 통신** |