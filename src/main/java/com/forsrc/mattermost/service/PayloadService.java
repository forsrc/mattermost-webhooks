package com.forsrc.mattermost.service;

import java.util.Map;

public interface PayloadService {

    public String getText(Map<String, String> queryParameters, Map<String, Object> payload) throws Exception;
}
