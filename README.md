# JCT (J Conversion Tool)

## 프로젝트 개요

이 프로젝트는 MyBatis XML 파일에서 SQL 쿼리를 추출하고, 해당 쿼리에서 Oracle 키워드를 분석하여 자동 변환 가능 여부와 안내 사항을 제공하는 도구입니다. 최종 결과는 엑셀 리포트로 생성됩니다.

## 주요 기능

### 1. XML 파일 수집

`XmlFileExplorer` 클래스는 지정된 디렉토리와 그 하위 디렉토리에서 모든 XML 파일을 재귀적으로 찾아 리스트로 반환합니다.

### 2. 쿼리 파싱

`MyBatisXmlParser` 클래스는 수집된 XML 파일에서 `select`, `insert`, `update`, `delete`, `sql` 태그를 찾아 쿼리를 추출합니다. 각 쿼리는 `ParsedQuery`
객체로 변환됩니다.

### 3. 오라클 키워드 스캔

`SqlAnalyzer` 클래스는 파싱된 쿼리에서 Oracle 키워드를 스캔합니다. 키워드는 `KeywordEnum` 열거형을 통해 정의됩니다. 스캔된 키워드는 `OracleQuery` 객체로 변환됩니다.

### 4. 리포트 생성

`ReportGenerator` 인터페이스와 이를 구현한 `ExcelReportGenerator` 클래스는 분석된 쿼리를 엑셀 파일로 저장합니다. 엑셀 파일에는 각 쿼리의 파일 경로, 쿼리 ID, SQL, 오라클
키워드, 자동 변환 가능 여부 및 안내 사항이 포함됩니다.

## 주요 클래스 및 메서드 설명

### Main 클래스

- `main(String[] args)`: 프로그램 실행의 시작점으로, XML 파일 수집, 쿼리 파싱, 키워드 스캔, 리포트 생성의 전체 흐름을 제어합니다.
- `collectXmlFiles(String rootDirectory)`: 루트 디렉토리에서 XML 파일을 수집합니다.
- `parseQuery(List<File> xmlFiles)`: 수집된 XML 파일에서 쿼리를 파싱합니다.
- `scanOracleKeyword(List<ParsedQuery> queries)`: 파싱된 쿼리에서 오라클 키워드를 스캔합니다.
- `generateReport(List<OracleQuery> queries)`: 스캔된 쿼리 정보를 바탕으로 리포트를 생성합니다.

### XmlFileExplorer 클래스

- `findXmlFiles(String directory)`: 디렉토리에서 XML 파일을 찾습니다.
- `findXmlFilesRecursively(File dir, List<File> xmlFiles)`: 디렉토리를 재귀적으로 탐색하여 XML 파일을 찾습니다.

### MyBatisXmlParser 클래스

- `parse(File file)`: XML 파일에서 쿼리를 추출합니다.
- `getElement(File file)`: XML 파일의 루트 엘리먼트를 반환합니다.
- `getDocumentBuilder()`: XML 문서 빌더를 생성합니다.

### SqlAnalyzer 클래스

- `analyze(ParsedQuery query)`: 쿼리에서 오라클 키워드를 분석합니다.

### KeywordEnum 클래스

- `KeywordEnum` 열거형은 SQL 쿼리에서 사용되는 오라클 키워드를 정의합니다. 각 키워드는 오라클에서 사용되는 키워드와 MySQL로 변환되는 키워드를 포함합니다. 또한, 자동 변환 가능 여부와 안내
  사항을 제공합니다.
- `oracle`: 오라클 키워드
- `mysql`: MySQL 변환 키워드
- `availAutoConversion`: 자동 변환 가능 여부
- `notice`: 안내 사항 (해당 키워드 또는 함수를 변환할 때 주의해야할 부분이나 안내하면 좋을 내용)

### 데이터 클래스

- `ParsedQuery`: 파싱된 쿼리의 정보를 담는 클래스입니다.
    - `Key`: 파일 경로와 쿼리 ID를 포함하는 내부 정적 클래스입니다.
        - `filePath`: 쿼리가 포함된 파일의 경로
        - `id`: 쿼리의 ID
    - `sql`: 파싱된 SQL 쿼리
- `OracleQuery`: 오라클 키워드가 포함된 쿼리의 정보를 담는 클래스입니다.
    - `query`: `ParsedQuery` 객체
    - `keywords`: 쿼리에 포함된 오라클 키워드 집합 (`Set<Keyword>`)

### ExcelReportGenerator 클래스

- `generateReport(List<OracleQuery> queries)`: 쿼리 정보를 엑셀 파일로 저장합니다.
- `generateFileName()`: 저장할 엑셀 파일 이름을 생성합니다.

#### 샘플 엑셀 파일 내용

| file path          | query ID | sql                    | oracle keywords | 자동 변환 가능 여부 | 안내사항 |
|--------------------|----------|------------------------|-----------------|-------------|------|
| /path/to/file1.xml | query1   | SELECT * FROM users    | SELECT, FROM    | true        |      |
| /path/to/file2.xml | query2   | INSERT INTO users (id) | INSERT, INTO    | true        |      |
| /path/to/file3.xml | query3   | UPDATE users SET name  | UPDATE, SET     | true        |      |

## 빌드 및 실행

의존 라이브러리들을 포함하여 빌드 합니다.

```
./gradlew clean shadowJar
```

## 사용 예시

```bash
java -jar jct.jar <root directory path>
```

## 의존성

- Apache Commons IO
- Lombok
- Apache POI
- Log4j
- JUnit