# MD Picker
작성자: 안혜영 (hyeyeong.ahn@gmail.com)


## 구현 범위

- 구현 1 ~ 4번의 Rest API
- JUnit Test (JPA Repository, RestController)

## Environments
* Java 17
* Spring Boot 3.2.9
* JUnit 5
* H2 Database
* JPA (Hibernate)


## Documentation
Notion:   
https://incredible-brass-883.notion.site/MD-Picker-API-Documents-5f575801087a47819eaa9862776a95a0?pvs=4

## 실행 방법

1. Git Clone 

```bash
$ git clone https://github.com/anne-workit/mdpicker`
```
```bash
$ cd mdpicker
```
2. run.sh script 실행

```bash
$ ./run.sh
```

* 실행이 안될 경우 권한 변경 후 실행

```bash
$ chmod 755 run.sh
```

URL:
```
http://localhost:8080/{API URL}
```


## Test Curl
### 구현 1) 카테고리 별 최저 가격 브랜드와 상품 가격, 합계 금액 조회 API
```bash
curl --location --request GET 'http://localhost:8080/md/price/summary'
```

### 구현 2) 최저 가격에 판매하는 브랜드와 카테고리의 상품 가격, 합계 금액 조회 API 
```bash
curl --location --request GET 'http://localhost:8080/md/price/brand?order=asc'
```
* order=desc 로 설정할 경우 최고가 브랜드 조회

### 구현 3) 카테고리별 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
```bash
curl --location --request GET 'http://localhost:8080/md/price/category?id=8' 
```
* id는 1~8까지 사용 가능

### 구현 4) 브랜드 및 카테고리별 상품 가격 CRUD API
#### 1. Create
```bash
 curl --location 'http://localhost:8080/md/brand' \
--header 'Content-Type: application/json' \
--data '{
    "brandName": "TESTBRAND",
    "useYn": "Y",
    "categoryList": [
        {
            "price": 20000,
            "category": "상의",
            "categoryTypeId": 1
        },
        {
            "price": 25000,
            "category": "아우터",
            "categoryTypeId": 2
        },
        {
            "price": 30000,
            "category": "바지",
            "categoryTypeId": 3
        },
        {
            "price": 35000,
            "category": "스니커즈",
            "categoryTypeId": 4
        },
        {
            "price": 40000,
            "category": "가방",
            "categoryTypeId": 5
        },
        {
            "price": 45000,
            "category": "모자",
            "categoryTypeId": 6
        },
        {
            "price": 50000,
            "category": "양말",
            "categoryTypeId": 7
        },
        {
            "price": 55000,
            "category": "액세서리",
            "categoryTypeId": 8
        }
    ]
}'
```

#### 2. Read
```bash
curl --location 'http://localhost:8080/md/brand?id=10'
```

#### 3. Update
```bash
curl --location --request PUT 'http://localhost:8080/md/brand' \
--header 'Content-Type: application/json' \
--data '{
    "brandName": "TESTBRAND",
    "useYn": "Y",
    "brandId": 10,
    "categoryList": [
        {
            "price": 114000,
            "category": "상의",
            "categoryTypeId": 1,
            "categoryId": 73
        }
    ]
}
'
```

#### 4. Delete
```bash
curl --location --request DELETE 'http://localhost:8080/md/brand?id=10'
```



