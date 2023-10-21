package com.example.scoretracker.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EnvChecker implements InitializingBean {

    private final List<String> lstEnv;

    public EnvChecker() {
        this.lstEnv = List.of(
                "DB_USERNAME",
                "DB_PASSWORD",
                "DB_URL"
        );
    }

    @Override
    public void afterPropertiesSet() {
        StringBuilder envNotSet = new StringBuilder();
        for (String env : lstEnv) {
            String value = System.getenv(env);
            if (value == null) {
                envNotSet.append("\n").append(env);
            }
            System.out.println(env + "=" + value);
        }

        if (!envNotSet.isEmpty()) {
            throw new IllegalStateException("Environment variable not set" + envNotSet);
        }
    }

}
