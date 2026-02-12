
해당문서는 gemini-2.5-flash-lite 로 번역되어 부자연스러운 부분이 있을 수 있으니,<br>정확한 내용을 여기서 확인해주세요.<br>원본보기: [ENGLISH GUIDE](https://github.com/seyun4047/drone-platform-docs/blob/main/components/monitoring-server/monitoring-server.md)

---

# 드론 플랫폼 모니터링 서버

---
## 작동 방식
모니터링 서버는 별도의 (로컬) 서비스로 실행되며, 메인 [드론 플랫폼 서버](https://github.com/seyun4047/drone-platform-server)에서 이미 실행 중인 Redis 및 MySQL 인스턴스에 연결됩니다.

이 시스템은 Redis ZSET 기반의 하트비트 모니터링 메커니즘을 사용하여 하트비트 전송을 중지한 드론을 자동으로 감지하고 연결을 해제합니다.

---
## 무엇을 모니터링하는가
이 서비스의 주요 역할은 다음과 같은 비정상적인 드론 상태를 감지하는 것입니다.
- 지정된 기간 동안 비활성 상태인 드론
- 하트비트 신호 전송을 중지한 드론
- 상태 업데이트 전송에 실패한 드론

---
## 어떻게 도움이 되는가
이러한 경우를 식별하고 처리함으로써 모니터링 서버는 다음과 같은 이점을 제공합니다.
- 불필요한 서버 리소스 소모 방지
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
모니터링 프로세스는 아래와 같은 흐름을 따릅니다.
|모니터링 서버|
|---|
|<img width="450" alt="Untitled diagram-2026-02-11-173920" src="https://github.com/user-attachments/assets/adbbeee5-7544-46c0-a276-0a04aae3e303" />|

---

---

# PROJECT OVERVIEW
# Manufacturer-Independent Drone Platform

---
It is a **manufacturer-independent integrated drone monitoring platform!**

It is designed to manage various drones within a single environment,
enabling both **high-end professional drones and commercially available hobby camera drones**
to be used for lifesaving and disaster response.

---

## Project Structure

This platform consists of multiple independent repositories:

| Component | Description                                       | Repository                                                              |
|---------|---------------------------------------------------|-------------------------------------------------------------------------|
| Server | Core drone platform server (API, Auth, Telemetry) | [GitHub](https://github.com/seyun4047/drone-platform-server)            |
| Monitoring Server | Real-time Drone health check monitoring service   | [GitHub](https://github.com/seyun4047/drone-platform-monitoring-server) |
| Drone Data Tester | Test client for drone telemetry & data simulation | [GitHub](https://github.com/seyun4047/drone-platform-trans-tester)       |
| Drone Client | Drone Data Collection, Transmission & Analysis | [GitHub](https://github.com/seyun4047/drone-platform-client)            |
| Docs | Platform Documents | [GitHub](https://github.com/seyun4047/drone-platform-docs)|

---

## Background

Although custom drones, commercial drones, and consumer drones share similar basic control mechanisms,
their operational methods and **command-and-control structures** in real-world environments vary significantly.

In practice, drones are often utilized as tools that depend heavily on:
- Specific equipment
- Highly trained personnel

Recently, many institutions and companies have attempted to build drone systems integrated with AI technologies.  
However, these systems have clear limitations. They typically rely on tuning specific drone models or operating a single type of custom-built drone, which results in strong dependency on specialized personnel and proprietary technologies.

Such dependency is particularly critical in **life-saving and disaster response operations**.

---
## Project Goal
- A manufacturer-independent drone monitoring platform that supports lifesaving and disaster response operations.

---
## Objectives

- A drone monitoring and management system deployable regardless of drone model or manufacturer
- A system that can be immediately deployed in the field without complex control procedures
- A system that does not rely on the performance capabilities of specific drone hardware
- A system that allows non-professional drone hobbyists to contribute effectively in emergency situations

---

## Expected Impact

In life-saving and disaster response scenarios, before professional equipment or rescue teams arrive on site,  
any available drone—if operable by anyone—can be immediately deployed to:
- Assess victims
- Identify hazards
- Estimate damage

By securing this critical **golden time**, the system enables faster decision-making and more effective deployment of advanced rescue resources, ultimately leading to more sophisticated and impactful drone-assisted emergency response systems.

---

## System Architecture

### Overall System Architecture
<img height="900" alt="Untitled diagram-2026-02-11-182634" src="https://github.com/user-attachments/assets/8842dd09-471e-4a75-8804-674f9cff675a" />


---

## Core System Flows

|                                                                           Auth Logic                                                                            |                                          Control Data From Drone                                          |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
|  <img width="450" alt="Redis Token Connection Flow-2026-02-01-182619" src="https://github.com/user-attachments/assets/cf0e6a9e-eeae-4525-aaf1-198c98e61c90" />  | <img src="https://github.com/user-attachments/assets/669647c6-ee30-4bfb-baea-d02e306070ea" width="450"/>  |
|                                                   **Redis-based authentication and connection control flow.**                                                   |                    **Processing of control and telemetry data after authentication.**                     |

|                                             Token Validation                                              |                                             Monitoring Server                                             |
|:---------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/456dc993-64a0-4ac8-9138-0f5446aaad07" width="450"/>  |<img width="450" alt="Untitled diagram-2026-02-11-173920" src="https://github.com/user-attachments/assets/6eea1ba2-663d-4bf1-be1d-c729e3bda2f7" />
  |
|                          **Validation of Redis tokens for incoming drone data.**                          |                              **Periodic drone connection state monitoring.**                              |
