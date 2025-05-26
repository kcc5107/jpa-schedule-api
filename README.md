# 일정 관리 앱

이 프로젝트는 JPA를 사용하여 일정을 생성하고, 조회하고, 수정하고, 삭제할 수 있는(CRUD) **RESTful API** 기반 일정 관리 앱 입니다.  
사용자들은 개인적인 일정을 생성, 조회, 수정, 삭제할 수 있으며, 각 일정에 대해 댓글을 남길 수 있습니다.  
사용자 등록 및 로그인 기능을 통해 안전하게 자신의 데이터를 관리하고, JWT 대신 쿠키/세션 기반 인증 방식을 적용하여 기본적인 인증 흐름을 학습할 수 있습니다. 

## 사용 기술
`Java`, `Spring`, `Spring Boot`, `Spirng Data JPA`, `MySQL`, `Postman`, `Lombok`, `BCrypt` 등

## 요구 사항 및 기능 설명

### 1. 일정 관리
- **생성**: 새로운 할일(일정)을 생성할 수 있습니다.
- **조회**: 생성된 모든 할일 또는 특정 할일을 조회할 수 있습니다.
- **수정**: 기존 할일의 내용을 수정할 수 있습니다.
- **삭제**: 특정 할일을 삭제할 수 있습니다.
- 필드:
  - 할 일 제목, 할 일 내용, 유저 고유 식별자, 작성일, 수정일
- 작성일/수정일: `JPA Auditing`을 활용하여 자동으로 기록
- 일정 ID는 자동 생성됨
- 작성 유저는 고유 식별자(FK)를 통해 참조 (단방향 N:1관계)


### 2. 사용자 관리
- **생성**: 새로운 사용자를 등록할 수 있습니다.
- **조회**: 등록된 모든 사용자 또는 특정 사용자 정보를 조회할 수 있습니다.
- **수정**: 사용자 정보를 수정할 수 있습니다.
- **삭제**: 사용자 계정을 삭제할 수 있습니다.
- 필드: 
  - 유저명, 이메일, 비밀번호, 작성일, 수정일
- 작성일/수정일: `JPA Auditing`을 활용하여 자동으로 기록

### 3. 로그인 (인증)
- 인증 방식: `Session`을 활용하여 로그인 기능을 구현
- 필터 활용: `Servlet Filter`를 사용하여 인증 처리를 수행
- 로그인 조건: 이메일과 비밀번호를 사용하여 로그인(일치하지 않을 시 401 상태코드 반환)
- 인증 제외: 회원가입 및 로그인 요청은 인증 필터의 적용 대상에서 제외
- 예외 처리: 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 **HTTP Status code 401 (Unauthorized)**을 반환

### 4. Validation을 활용한 예외처리 적용
- 회원가입 시 이메일 형식에 대한 정규표현식 검증 `(@Pattern)`
  - "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
- 비밀번호는 4글자 이상 10글자 이내, 유저 이름은 2글자 이상 4글자 이내

### 5. 비밀번호 암호화
- `PasswordEncoder`: `at.favre.lib:bcrypt` 라이브러리를 사용하여 구현한 `PasswordEncoder` 활용

### 6. 댓글 관리
- **생성**: 생성된 일정에 댓글을 남길 수 있습니다.
- **조회**: 특정 일정에 달린 댓글들을 조회할 수 있습니다.
- **수정**: 자신의 댓글 내용을 수정할 수 있습니다.
- **삭제**: 자신의 댓글을 삭제할 수 있습니다.
- 필드: 
   - 댓글 내용, 작성일, 수정일, 유저 고유 식별자, 일정 고유 식별자.
- 작성일/수정일: `JPA Auditing`을 활용하여 자동으로 기록

## API 명세

| 기능              | Method | URL                             | 설명                             |
|-------------------|--------|----------------------------------|----------------------------------|
| 작성자 생성        | POST   | `/authors`                       | 이름 + 이메일 등록               |
| 일정 등록          | POST   | `/authors/{authorId}/schedules`  | 특정 작성자의 일정 등록          |
| 전체 일정 조회     | GET    | `/schedules`                     | 조건 필터링(작성자명, 수정일 등) |
| 단일 일정 조회     | GET    | `/schedules/{id}`                | 일정 ID로 단건 조회              |
| 일정 수정          | PATCH  | `/schedules/{id}`                | 할일/작성자명 수정 (비밀번호 필요) |
| 일정 삭제          | DELETE | `/schedules/{id}`                | 비밀번호 확인 후 삭제            |
| 작성자 일정 조회    | GET    | `/authors/{authorId}/schedules`  | 작성자 ID 기준 일정 목록 조회    |


### 1. 작성자 생성
- POST `/authors`
- Request `Body`
```json

{
    "name": "작성자3",
    "email": "asdww@dasdvc.com"
}
```
- Response
```json
{
    "id": 3,
    "name": "작성자3",
    "email": "asdww@dasdvc.com",
    "createdAt": "2025-05-14 10:51:25",
    "updatedAt": "2025-05-14 10:51:25"
}
```

### 2. 일정 등록
- POST `/authors/{authorId}/schedules`  
- Request `PathVariable: authorId`, `Body`
- `/authors/2/schedules`
- Body
```json
{
    "toDo": "일정ㅇㅇ6",
    "password": "비밀번호6"
}
```
- Response
```json
{
    "id": 6,
    "toDo": "일정ㅇㅇ6",
    "authorId": 2,
    "createdAt": "2025-05-14 10:55:54",
    "updatedAt": "2025-05-14 10:55:54"
}
```

### 3. 전체 또는 특정 조건 일정 조회
- GET `/schedules`  
- Request `query parameter(required = false)`
  - userName(Lv3에서는 삭제)
  - updatedAt
- `/schedules?updatedAt=2025-05-14`
- Response
```json
[
    {
        "id": 2,
        "toDo": "id2의 일정을 수정",
        "authorId": 1,
        "createdAt": "2025-05-14 10:54:50",
        "updatedAt": "2025-05-14 10:58:17"
    },
    {
        "id": 6,
        "toDo": "일정ㅇㅇ6",
        "authorId": 2,
        "createdAt": "2025-05-14 10:55:54",
        "updatedAt": "2025-05-14 10:55:54"
    },
    {
        "id": 5,
        "toDo": "일정ㅇㅇ5",
        "authorId": 2,
        "createdAt": "2025-05-14 10:55:48",
        "updatedAt": "2025-05-14 10:55:48"
    },
    {
        "id": 4,
        "toDo": "일정ㅇㅇ4",
        "authorId": 1,
        "createdAt": "2025-05-14 10:55:31",
        "updatedAt": "2025-05-14 10:55:31"
    },
    {
        "id": 3,
        "toDo": "일정ㅇㅇ3",
        "authorId": 1,
        "createdAt": "2025-05-14 10:55:11",
        "updatedAt": "2025-05-14 10:55:11"
    }
]
```

### 4. 단일 일정 조회
- GET `/schedules/{id}`
- Request `PathVariable: id`
- `/schedules/6`
- Response
```json
{
    "id": 6,
    "toDo": "일정ㅇㅇ6",
    "authorId": 2,
    "createdAt": "2025-05-14 10:55:54",
    "updatedAt": "2025-05-14 10:55:54"
}
```

### 5. 일정 수정
- PATCH `/schedules/{id}`  
- Request `PathVariable: id`, `Body`
- `/schedules/2`
- Body
```json
{
    "toDo": "id2의 일정을 수정",
    "password": "비밀번호3"
}
```

### 6. 일정 삭제
- DELETE `/schedules/{id}`
- Request `PathVariable: id`, `Body`
- `/schedules/2`
- Body
```json
{
    "password": "비밀번호3"
}
```

### 7. 작성자의 일정 조회
- GET `/authors/{authorId}/schedules` 
- Request `PathVariable: authorId`
- `/authors/1/schedules`
- Response
```json
[
    {
        "id": 2,
        "toDo": "id2의 일정을 수정",
        "authorId": 1,
        "createdAt": "2025-05-14 10:54:50",
        "updatedAt": "2025-05-14 10:58:17"
    },
    {
        "id": 4,
        "toDo": "일정ㅇㅇ4",
        "authorId": 1,
        "createdAt": "2025-05-14 10:55:31",
        "updatedAt": "2025-05-14 10:55:31"
    },
    {
        "id": 3,
        "toDo": "일정ㅇㅇ3",
        "authorId": 1,
        "createdAt": "2025-05-14 10:55:11",
        "updatedAt": "2025-05-14 10:55:11"
    }
]
```


## ERD
![alt text](image-1.png)


## 파일 디렉토리 구조
```
📦src/main/java/com.example.springscheduleapi
 ┣ 📂Lv12
 ┃ ┣ 📂controller
 ┃ ┃ ┗ 📜ScheduleController.java
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📜PasswordCheckRequestDto.java
 ┃ ┃ ┣ 📜ScheduleRequestDto.java
 ┃ ┃ ┗ 📜ScheduleResponseDto.java
 ┃ ┣ 📂entity
 ┃ ┃ ┗ 📜Schedule.java
 ┃ ┣ 📂repository
 ┃ ┃ ┣ 📜JdbcTemplateScheduleRepository.java
 ┃ ┃ ┗ 📜ScheduleRepository.java
 ┃ ┗ 📂service
 ┃ ┃ ┣ 📜ScheduleService.java
 ┃ ┃ ┗ 📜ScheduleServiceImpl.java
 ┃ ┃
 ┣ 📂Lv3
 ┃ ┣ 📂controller
 ┃ ┃ ┣ 📜AuthorScheduleController.java
 ┃ ┃ ┗ 📜ScheduleController.java
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📜AuthorRequestDto.java
 ┃ ┃ ┣ 📜AuthorResponseDto.java
 ┃ ┃ ┣ 📜PasswordCheckRequestDto.java
 ┃ ┃ ┣ 📜ScheduleRequestDto.java
 ┃ ┃ ┗ 📜ScheduleResponseDto.java
 ┃ ┣ 📂entity
 ┃ ┃ ┣ 📜Author.java
 ┃ ┃ ┗ 📜Schedule.java
 ┃ ┣ 📂repository
 ┃ ┃ ┣ 📜AuthorRepository.java
 ┃ ┃ ┣ 📜JdbcTemplateAuthorRepository.java
 ┃ ┃ ┣ 📜JdbcTemplateScheduleRepository.java
 ┃ ┃ ┗ 📜ScheduleRepository.java
 ┃ ┗ 📂service
 ┃ ┃ ┣ 📜AuthorService.java
 ┃ ┃ ┣ 📜AuthorServiceImpl.java
 ┃ ┃ ┣ 📜ScheduleService.java
 ┃ ┃ ┗ 📜ScheduleServiceImpl.java
 ┗ 📜SpringScheduleApiApplication.java
```

## 트러블슈팅
https://github.com/kcc5107/TIL/blob/main/2025-05-13.md  
