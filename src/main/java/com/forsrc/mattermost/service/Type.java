package com.forsrc.mattermost.service;

import java.util.Map;

public enum Type {

    GITBUCKRT("gitbucket") {

        @Override
        public String getText(Map<String, String> queryParameters, Map<String, Object> payload) throws Exception {
            return new GitbucketPayloadServiceImpl().getText(queryParameters, payload);
        }

    };

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public abstract String getText(Map<String, String> queryParameters, Map<String, Object> payload) throws Exception;

    public static Type of(String name) {
        Type[] types = values();
        for (Type t : types) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
