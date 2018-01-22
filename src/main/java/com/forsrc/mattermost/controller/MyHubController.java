package com.forsrc.mattermost.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forsrc.mattermost.domain.MattermostIncomingWebhooks;
import com.forsrc.mattermost.service.Type;



@RestController
public class MyHubController {

    @Value("${mattermost.incoming}")
    private String mattermostIncoming;

    @Value("${mattermost.iconUrl}")
    private String iconUrl;

    @Value("${mattermost.username}")
    private String username;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    
    @Autowired
    @Qualifier("httpsRestTemplate")
    private RestTemplate httpsRestTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MyHubController.class);

    @RequestMapping("/hooks")
    public ResponseEntity<String> hoooks(@RequestParam("js") String js, @RequestParam("hooks") String hooks, @RequestParam("channel") String channel, @RequestParam("username") String username,
            @RequestParam Map<String, String> queryParameters, @RequestBody Map<String, Object> payload, HttpServletRequest request) throws Exception {

        LOGGER.info("--> url: " + request.getRequestURL());
        StringBuilder text = new StringBuilder();
        String t = request.getParameter("text");
        t = t == null ? "" : t.replaceAll("\\\\n", "\n");

        text.append(t);
        MattermostIncomingWebhooks mattermost = new MattermostIncomingWebhooks();
        mattermost.setChannel(channel);

        if (StringUtils.isEmpty(username)) {
            mattermost.setUsername(this.username);
        } else {
            mattermost.setUsername(username);
        }

        String iconUrl = queryParameters.get("icon_url");
        if (StringUtils.isEmpty(iconUrl)) {
            mattermost.setIconUrl(this.iconUrl);
        } else {
            mattermost.setIconUrl(iconUrl);
        }

        String type = request.getParameter("type");
        if (!StringUtils.isEmpty(type)) {
            try {
                text.append(Type.of(type).getText(queryParameters, payload));
            } catch (Exception e) {
                text.append("\nerror: " + e.getMessage()).append("\n");
            }
        }

        if (!StringUtils.isEmpty(js)) {
            String[] j = js.split(",");
            for (String file : j) {
                try {
                    text.append(getTextFromJs(file.trim(), queryParameters, payload));
                } catch (Exception e) {
                    text.append("error: ").append(file.trim()).append(" -> ").append(e.getMessage()).append("\n");
                }
            }
        } else {

            try {
                text.append(getTextFromJs("js/toText.js", queryParameters, payload));
            } catch (Exception e) {
                text.append("error: ").append("js/toText.js").append(" -> ").append(e.getMessage()).append("\n");
            }

        }
        mattermost.setText(text.toString());
        LOGGER.info("--> text: " + text.toString());
        String resp = "NG";
        HttpEntity<MattermostIncomingWebhooks> httpEntity = new HttpEntity<>(mattermost);
        if (hooks.startsWith("https")) {
            resp = httpsRestTemplate.postForObject(hooks, httpEntity, String.class);
        } else {
            resp = restTemplate.postForObject(hooks, httpEntity, String.class);
        }
        LOGGER.info("--> response: " + resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

        private String getTextFromJs(String js, Map<String, String> queryParameters, Map<String, Object> payload) throws ScriptException, IOException, NoSuchMethodException {

        return execJs(js, "toText", queryParameters, payload).toString();
    }

    @RequestMapping("/cmd")
    public ResponseEntity<String> cmd(@RequestParam("js") String js, @RequestParam("hooks") String hooks, @RequestParam("channel") String channel, @RequestParam("username") String username,
            @RequestParam Map<String, String> queryParameters, @RequestBody Map<String, Object> payload, HttpServletRequest request) throws Exception {

        log.info("--> hoooks: {}", request.getRequestURI());
        log.info("--> queryParameters: {}", queryParameters);
        log.info("--> payload: {}", payload);
        StringBuilder text = new StringBuilder();
        String t = request.getParameter("text");
        t = t == null ? "" : t.replaceAll("\\\\n", "\n");

        text.append(t);

        if (!StringUtils.isEmpty(js)) {
            String[] j = js.split(",");
            for (String file : j) {
                try {
                    text.append(execJs(file.trim(), "exec", queryParameters, payload));
                } catch (Exception e) {
                    text.append("error: ").append(file.trim()).append(" -> ").append(e.getMessage()).append("\n");
                }
            }
        } else {

            try {
                text.append(execJs("js/toExec.js", "exec", queryParameters, payload));
            } catch (Exception e) {
                text.append("error: ").append("js/toExec.js").append(" -> ").append(e.getMessage()).append("\n");
            }

        }
        log.info("--> text: {}", text.toString());
        MattermostIncomingWebhooks mattermost = new MattermostIncomingWebhooks();
        mattermost.setChannel(channel);
        HttpEntity<MattermostIncomingWebhooks> httpEntity = new HttpEntity<>(mattermost);
        String resp = restTemplate.postForObject(hooks, httpEntity, String.class);

        log.info("--> Response: {}", resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private Object execJs(String js, String function, Map<String, String> queryParameters, Map<String, Object> payload) throws ScriptException, IOException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        String json2 = IOUtils.toString(new ClassPathResource("js/json2.js").getInputStream(), "UTF-8");
        engine.eval(json2);

        if (js.startsWith("file://")) {
            engine.eval(Files.newBufferedReader(Paths.get(new File(js.replaceAll("file://", "")).getAbsolutePath()), StandardCharsets.UTF_8));
        } else {
            String jsText = IOUtils.toString(new ClassPathResource(js).getInputStream(), "UTF-8");
            engine.eval(jsText);
        }

        Invocable inv = (Invocable) engine;

        ObjectMapper objectMapper = new ObjectMapper();
        String query = objectMapper.writeValueAsString(queryParameters);
        String payloadString = objectMapper.writeValueAsString(payload);

        return inv.invokeFunction(function, query, payloadString);
    }
}

