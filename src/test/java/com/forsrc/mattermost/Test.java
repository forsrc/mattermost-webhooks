package com.forsrc.mattermost;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

    public static void main(String[] args) throws Exception {

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("name", "forsrc");
        Map<String, String> payload = new HashMap<>();
        payload.put("key", "value");
        String text = execJs("js/toExec.js", "exec", queryParameters, payload).toString();
        System.out.println("---------");
        System.out.println(text);
        System.out.println("---------");
        text = execJs("js/toText.js", "toText", queryParameters, payload).toString();
        System.out.println(text);
    }

    private static Object execJs(String js, String function, Map<String, String> queryParameters, Map<String, String> payload) throws IOException, NoSuchMethodException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        String json2 = IOUtils.toString(new ClassPathResource("js/json2.js").getInputStream(), "UTF-8");
        engine.eval(json2);

        String jsText = IOUtils.toString(new ClassPathResource(js).getInputStream(), "UTF-8");
        engine.eval(jsText);

        Invocable inv = (Invocable) engine;

        ObjectMapper objectMapper = new ObjectMapper();


        String query = objectMapper.writeValueAsString(queryParameters);
        String payloadString = objectMapper.writeValueAsString(payload);

        return inv.invokeFunction(function, query, payloadString);
    }
}
