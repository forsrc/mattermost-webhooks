package com.forsrc.mattermost.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MattermostIncomingWebhooks {

    private String channel;
    private String username;
    @JsonProperty("icon_url")
    private String iconUrl = "https://s3-eu-west-1.amazonaws.com/renanvicente/toy13.png";
    private String text;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
