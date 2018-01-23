

function exec(queryString, payloadString) {
    var text = "";
    var query = JSON.parse(queryString);
    var payload = JSON.parse(payloadString);

    //java.lang.System.out.println(queryString)
    var cmd = ["curl", "-X", "GET", "--user", query.user, query.url];
    //var cmd = ["cmd", "/c", "echo", "curl", "-X", "POST", "--user", query.user, query.url];

    if (query.isWindows) {
        var ClassPathResource = Java.type("org.springframework.core.io.ClassPathResource");
        var curl = new ClassPathResource("curl.exe").getFile().getAbsolutePath();
        //cmd = ["cmd", "/c", "chcp", "65001", "&&", curl, "-X", "GET", "--user", query.user, query.url];
        cmd = ["cmd", "/c", curl, "-X", "GET", "--user", query.user, query.url];
    }
    var url = query.url;
    if (url.indexOf("/build") > 0) {
        url = url.substring(0, url.indexOf("/build"));
    }
    url = url.replace("http://192.168.28.26:8888", "https://13.112.37.68");
    text += url;

    var cmdUtils = Java.type("com.forsrc.mattermost.utils.CmdUtils");
    cmdUtils.exec(cmd);
    return text;
}
