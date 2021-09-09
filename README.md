# Description

## 환경 정보

- Framwork : SpringBoot 2.5.4
- Build : Gradle 7.1.1
- Language : Kotlin (v1.5.21, under JDK 11)
- Encoding : UTF-8


## 빌드 / 실행

```
cd /path/to/project/root

/gradlew clean build -xtest
./gradlew clean bootRun
```

### H2DB console
- 
- http://localhost:8080/h2-console
- prototype / prototype

## API

1. 결제 API

호출 예시 (`POST - http://localhost:8080/pay`)

```json
{
    "cardNo": "1234567812345678",
    "cardExpirationDigit": "1225",
    "cvc": "124",
    "amount": 1000,
    "vat": 91
}
```

응답 예시

```json
{
  "managementNumber": "73ed410d44d74f2298d6",
  "encryptedString": " 446PAYMENT   73ed410d44d74f2298d61234567812345678    001225124      1000        91                    SvLoLb9jEmok0cvecvi8f3iB3sJyGThEzJfvf50KsPA=                                                                                                                                                                                                                                                                                                               "
}
```

2. 환불 API

호출 예시 (`POST - http://localhost:8080/refund`)

```json
{
    "uniqueId": "ddfd57d1d97345ebb0a6",
    "cancelAmount": 100000
}
```

응답 예시

```json
{
    "status": 200,
    "message": "OK",
    "data": {
        "paymentSerialNumber": "K4auw37ONfH2iv7uIX0z",
        "datayPayload": " 416CANCEL    K4auw37ONfH2iv7uIX0z1234567890123456    000225110     100000000000909K4auw37ONfH2iv7uIX0zdXzNDNxckOrb7uz2ON0AAInkf0+ztESwzOOm+pz7Kpc=                                                                                                                                                                                                                                                                                                               "
    }
}
```

3. 데이터 조회 API

호출 예시 (`GET - http://localhost:8080/payments/6e24f9bf333e4b4e9353`)

응답 예시

```json
{
  "uniqueId": "6e24f9bf333e4b4e9353",
  "cardNo": "123456#######678",
  "cardExpiry": "1225",
  "cvc": "124",
  "paymentType": "CANCEL",
  "amount": 1000,
  "vat": 91
}
```

4. 오류 응답 형식

```json
{
  "timestamp": "2021-09-08T21:25:06.585+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/refund"
}
```

## 문제해결 전략

- 과제 문서를 여러 번 읽은 다음 더미 api를 먼저 구현하여 애플리케이션이 동작을 확인한 다음 결제, 환불, 조회 순서대로 하나씩 실제 구현을 진행했습니다. 
- 취소 처리는 다소 애매했으나 도전 과제에 부분 환불이 있기에 기본 과제에서는 전체 환불 처리를 했습니다.
- 도전 과제 중 중복 결제/같은 카드 번호 동시 결제 방지 처리를 간단한 개념 수준에서 구현했습니다.

##  테이블 설계

1. 카드사 통신 저장 (`card_payment_api`)
2. 카드사 통신 후 승인내용 저장(`card_payment_log`)
3. 카드사 통신 후 취소내용 저장(`card_refund_log`)
4. 중복 결제/동일 카드 동시 결제 방지 처리용 락 스토리지(`naive_card_transaction`)
