package com.forsrc.mattermost;

import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws Exception {
		
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        String json2 = IOUtils.toString(new ClassPathResource("js/json2.js").getInputStream(), "UTF-8");
        engine.eval(json2);

   
        String jsText = IOUtils.toString(new ClassPathResource("js/toExec.js").getInputStream(), "UTF-8");
        engine.eval(jsText);
        

        Invocable inv = (Invocable) engine;

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("name", "forsrc");
        Map<String, String> payload = new HashMap<>();
        queryParameters.put("key", "value");

        String query = objectMapper.writeValueAsString(queryParameters);
        String payloadString = objectMapper.writeValueAsString(payload);

        String text = inv.invokeFunction("exec", query, payloadString).toString();
        System.out.println("-----------------");
        System.out.println(text);
	}
}
