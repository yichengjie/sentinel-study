### Environment中获取配置
1. 方式一：直接getProperties获取
   ```
   String bootstrapServers = env.getProperty("hello.kafka.bootstrap-servers");
   ```
2. 方式二：将属性直接绑定到对象上
   ```
   BindResult<TestKafkaProperties> kafkaProperties = Binder.get(env).bind("hello.kafka", TestKafkaProperties.class);
   ```
3. 具体Properties类编写
   ```java
   @Data
   @ConfigurationProperties(prefix = "hello.kafka")
   public class TestKafkaProperties implements Serializable {

       private String bootstrapServers ;

       private List<TestProducerProperties> producers = new ArrayList<>();

       @Data
       public static class TestProducerProperties implements Serializable {

           private String username ;

           private String password ;

           private Integer retries = 0;

           private Integer batchSize = 16384;

           private Integer linger = 1;
       }
   }
   ```