# README

1. 설치 및 환경설정 가이드
2. 테이블 생성 SQL
3. API 사용 가이드





## 1. 설치 및 환경설정 가이드



**git clone으로 git 저장소 불러오기**

```
$ git clone https://github.com/rockintuna/Tpirates_demo.git
Cloning into 'ngma'...
remote: Enumerating objects: 138, done.
remote: Counting objects: 100% (138/138), done.
remote: Compressing objects: 100% (91/91), done.
Recremote: Total 138 (delta 38), reused 130 (delta 33), pack-reused 0
Receiving objects: 100% (138/138), 1.51 MiB | 8.29 MiB/s, done.
Resolving deltas: 100% (38/38), done.
```



**IDE 프로젝트 open**

<img src="/img/image-20210430155509886.png" alt="image-20210430155509886" style="zoom:40%;" />



**Application 실행**

<img src="/img/image-20210430155706376.png" alt="image-20210430155706376" style="zoom:23%;" />



프로젝트 JDK 버전은 11입니다.

API 예제 테스트를 위한 Httpie 설치

https://httpie.io/docs#installation



### 기술 스택

- maven

- Java

- Spring Boot

- JPA

- H2 Database



## 2. 테이블 생성 SQL

Spring Data JPA를 사용하기 때문에 엔티티가 테이블에 매핑되어 테이블 생성이 자동으로 됩니다.



## 3. API 사용 가이드

프로젝트 디렉토리 아래의 Demo 데이터용 Json 파일이 위치한 경로로 이동

```bash
cd Tpirates_demo/demo
```



**Httpie를 사용하여 요청 **

- 점포 추가 API (POST /store)
    - add_store1.json 및 add_store2.json, add_store3.json 파일에는 점포 정보에 대한 데이터가 json 형식으로 저장되어 있습니다.
    - 점포의 id 값은 자동으로 생성됩니다.
    - 점포의 이름과 주인은 필수입니다.

```bash
$ http POST localhost:8080/store < add_store1.json
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 0
Date: Fri, 30 Apr 2021 07:04:31 GMT
Keep-Alive: timeout=60



$ http POST localhost:8080/store < add_store2.json
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 0
Date: Fri, 30 Apr 2021 07:04:34 GMT
Keep-Alive: timeout=60



$ http POST localhost:8080/store < add_store3.json
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 0
Date: Fri, 30 Apr 2021 07:04:38 GMT
Keep-Alive: timeout=60




```



- 점포 휴무일 등록 API (POST /store/holiday)
    - add_holidays.json 파일에는 휴무일 정보에 대한 데이터가 json 형식으로 저장되어 있습니다.

```bash
$ http POST localhost:8080/store/holiday < add_holidays.json
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 0
Date: Fri, 30 Apr 2021 07:07:21 GMT
Keep-Alive: timeout=60




```



- 점포 목록 조회 API (GET /store/list)
    - 점포의 level 오름차순으로 데이터를 조회합니다.
    - 현재 영업중인 경우 "OPEN", 영업을 종료한 경우 "CLOSE", 휴무일인 경우 "HOLIDAY"로 상태값이 저장됩니다.

```bash
$ http GET localhost:8080/store/list                        
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 30 Apr 2021 07:23:31 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "businessStatus": "OPEN",
        "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
        "level": 1,
        "name": "해적수산"
    },
    {
        "businessStatus": "OPEN",
        "description": "오징어 맛집",
        "level": 1,
        "name": "거북선"
    },
    {
        "businessStatus": "HOLIDAY",
        "description": "인천소래포구 종합어시장 갑각류센터 인어수산",
        "level": 2,
        "name": "인어수산"
    }
]


```



- 점포 상세 정보 조회 API (GET /store id)
    - 해당 점포의 상세 정보와 오늘부터 3일치의 영업시간 및 상태를 조회합니다.
    - 영업일이 아닌 요일의 경우 조회하지 않습니다.
    - 내일 및 모래의 상태는 "CLOSE" 또는 "HOLIDAY" 입니다. (휴무일이면 "HOLIDAY", 아니면 "CLOSE")

```bash
$ http GET localhost:8080/store id==1
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 30 Apr 2021 07:23:44 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "address": "인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 1 호",
    "businessDays": [
        {
            "close": "23:00",
            "day": "Friday",
            "open": "09:00",
            "status": "HOLIDAY"
        }
    ],
    "description": "인천소래포구 종합어시장 갑각류센터 인어수산",
    "id": 1,
    "level": 2,
    "name": "인어수산",
    "phone": "010-1111-2222"
}



$ http GET localhost:8080/store id==3
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 30 Apr 2021 07:23:59 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "address": "서울 마포구",
    "businessDays": [
        {
            "close": "24:00",
            "day": "Friday",
            "open": "09:00",
            "status": "OPEN"
        },
        {
            "close": "23:00",
            "day": "Saturday",
            "open": "09:00",
            "status": "CLOSE"
        },
        {
            "close": "23:00",
            "day": "Sunday",
            "open": "09:00",
            "status": "CLOSE"
        }
    ],
    "description": "오징어 맛집",
    "id": 3,
    "level": 1,
    "name": "거북선",
    "phone": "010-4321-0000"
}


```



- 점포 삭제 API (DELETE /store id)
    - 해당 id의 점포를 삭제합니다.

```bash
$ http DELETE localhost:8080/store id==1
HTTP/1.1 200 
Connection: keep-alive
Content-Length: 0
Date: Fri, 30 Apr 2021 07:26:11 GMT
Keep-Alive: timeout=60




$ http GET localhost:8080/store/list    
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Fri, 30 Apr 2021 07:27:17 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "businessStatus": "OPEN",
        "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
        "level": 1,
        "name": "해적수산"
    },
    {
        "businessStatus": "OPEN",
        "description": "오징어 맛집",
        "level": 1,
        "name": "거북선"
    }
]

$ http GET localhost:8080/store id==1   
HTTP/1.1 500 
Connection: close
Content-Type: application/json
Date: Fri, 30 Apr 2021 07:27:58 GMT
Transfer-Encoding: chunked

{
    "error": "Internal Server Error",
    "message": "",
    "path": "/store",
    "status": 500,
    "timestamp": "2021-04-30T07:27:58.937+00:00"
}



```

