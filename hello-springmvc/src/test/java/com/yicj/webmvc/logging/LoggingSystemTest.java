package com.yicj.webmvc.logging;

import com.yicj.webmvc.WebMvcApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: yicj
 * @date: 2023/9/28 13:22
 */
@SpringBootTest(classes = WebMvcApplication.class)
public class LoggingSystemTest {

    private static final Map<String, List<String>> DEFAULT_GROUP_LOGGERS;
    static {
        MultiValueMap<String, String> loggers = new LinkedMultiValueMap<>();
        loggers.add("web", "org.springframework.core.codec");
        loggers.add("web", "org.springframework.http");
        loggers.add("web", "org.springframework.web");
        loggers.add("web", "org.springframework.boot.actuate.endpoint.web");
        loggers.add("web", "org.springframework.boot.web.servlet.ServletContextInitializerBeans");
        loggers.add("sql", "org.springframework.jdbc.core");
        loggers.add("sql", "org.hibernate.SQL");
        loggers.add("sql", "org.jooq.tools.LoggerListener");
        DEFAULT_GROUP_LOGGERS = Collections.unmodifiableMap(loggers);
    }

    private static final Map<LogLevel, List<String>> SPRING_BOOT_LOGGING_LOGGERS;
    static {
        MultiValueMap<LogLevel, String> loggers = new LinkedMultiValueMap<>();
        loggers.add(LogLevel.DEBUG, "sql");
        loggers.add(LogLevel.DEBUG, "web");
        loggers.add(LogLevel.DEBUG, "org.springframework.boot");
        loggers.add(LogLevel.TRACE, "org.springframework");
        loggers.add(LogLevel.TRACE, "org.apache.tomcat");
        loggers.add(LogLevel.TRACE, "org.apache.catalina");
        loggers.add(LogLevel.TRACE, "org.eclipse.jetty");
        loggers.add(LogLevel.TRACE, "org.hibernate.tool.hbm2ddl");
        SPRING_BOOT_LOGGING_LOGGERS = Collections.unmodifiableMap(loggers);
    }

    @Autowired
    private Environment environment ;

    private LoggingSystem loggingSystem;
    private LogFile logFile;
    private LoggerGroups loggerGroups ;
    private LogLevel springBootLogging = null;

    @Test
    public void hello(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        this.loggingSystem = LoggingSystem.get(classLoader);
        this.loggingSystem.beforeInitialize();
        this.logFile = LogFile.get(environment);
        this.loggerGroups = new LoggerGroups(DEFAULT_GROUP_LOGGERS);

    }
}
