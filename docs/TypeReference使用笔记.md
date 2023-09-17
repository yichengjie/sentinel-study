1. JSON字符串反序列化对象
    ```java
    @Slf4j
    public class TypeReferenceTest{
        @Test
        public void parseObject(){
            String jsonContent = "{\"code\":\"200\",\"message\":\"error message\",\"data\":\"hello world\"}" ;
            TypeReference<RestResponse<String>> typeReference = new TypeReference<>(){} ;
            RestResponse<String> response = JSON.parseObject(jsonContent, typeReference);
            log.info("====> response : {}", response);
        }
    }
    ```
2. RestTemplate返回值泛型转换
    ```java
    @Slf4j
    public class ParameterizedTypeReferenceTest {
        private RestTemplate restTemplate = new RestTemplate();
    
        @Test
        public void exchange(){
            String url = "http://localhost:8080/user-service/hello/index" ;
            ParameterizedTypeReference<RestResponse<String>> typeReference =
                    new ParameterizedTypeReference<>() {} ;
            ResponseEntity<RestResponse<String>> exchange = 
                 restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), typeReference);
            RestResponse<String> retValue = exchange.getBody();
            log.info("ret value : {}", retValue);
        }
    }
    ```