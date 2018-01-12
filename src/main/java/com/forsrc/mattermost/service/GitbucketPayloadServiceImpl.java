package mattermost.service;

import java.util.Date;
import java.util.Map;

public class GitbucketPayloadServiceImpl implements PayloadService {

    @Override
    public String getText(Map<String, String> queryParameters, Map<String, Object> payload) throws Exception {
        StringBuilder text = new StringBuilder();
        Object pullRequest = payload.get("pull_request");
        if (pullRequest != null) {
            Map<String, Object> pr = (Map<String, Object>)pullRequest;
            text.append(getPullRequest(pr, queryParameters, payload));
        }
        return text.toString();
    }

    private String getPullRequest(Map<String, Object> pr, Map<String, String> queryParameters, Map<String, Object> payload) throws Exception {
        StringBuilder text = new StringBuilder();
        text.append("#### ").append("[PR](").append(pr.get("html_url")).append(") #### \n");
        text.append("").append("\n");
        text.append("| name | text | other |").append("\n");
        text.append("|:-----------|:-----------:|:-----------------------------------------------|").append("\n");
        text.append("| status | ").append(payload.get("action")).append(" | - |").append("\n");
        text.append("| number | ").append(pr.get("number")).append(" | - |").append("\n");
        text.append("| title | ").append(pr.get("number")).append(": ").append(pr.get("title")).append(" | - |").append("\n");
        text.append("| url | ").append(pr.get("html_url")).append(" | - |").append("\n");
        text.append("| created_at | ").append(pr.get("created_at")).append(" | - |").append("\n");
        text.append("| updated_at | ").append(pr.get("updated_at")).append(" | - |").append("\n");
        text.append("| body | ").append(pr.get("body")).append(" | - |").append("\n");
        text.append("| review_comments_url | ").append(pr.get("review_comments_url")).append(" | - |").append("\n");

        text.append("| user | @").append(((Map<String, Object>)pr.get("user")).get("login")).append(" | - |").append("\n");
        text.append("| repository | ").append(((Map<String, Object>)payload.get("repository")).get("full_name")).append(" | - |").append("\n");
        return text.toString();
    }
}
