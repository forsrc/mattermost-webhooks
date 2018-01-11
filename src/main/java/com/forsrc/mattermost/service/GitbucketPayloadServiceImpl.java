package com.forsrc.mattermost.service;

import java.util.Date;
import java.util.Map;

public class GitbucketPayloadServiceImpl implements PayloadService {

    @Override
    public String getText(Map<String, String> queryParameters, Map<String, Object> payload) throws Exception {
        return "test -> " + new Date();
    }

}
