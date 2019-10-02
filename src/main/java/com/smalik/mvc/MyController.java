package com.smalik.mvc;

import com.smalik.config.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {

    @Autowired
    private MyBean bean;

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public Map<String, Object> getEnvironment() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("environment", getEnvironmentPropertiesMap());
        map.put("placeholder", bean);
        return map;
    }

    private Map<String, String> getEnvironmentPropertiesMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("messageA", environment.getProperty("message.a", "java-default"));
        map.put("messageB", environment.getProperty("message.b", "java-default"));
        map.put("messageC", environment.getProperty("message.c", "java-default"));
        map.put("messageD", environment.getProperty("message.d", "java-default"));
        map.put("messageE", environment.getProperty("message.e", "java-default"));
        map.put("messageF", environment.getProperty("message.f", "java-default"));
        return map;
    }
}
