
function exec(queryString, payloadString) {
    var text = "";
    var query = JSON.parse(queryString);
    var payload = JSON.parse(payloadString);

    //java.lang.System.out.println(queryString)
    var cmdUtils = Java.type("mattermost.utils.CmdUtils");
 
    var cmd = ["cmd", "/c", "echo", "OK"];
    text += cmdUtils.exec(cmd);
    java.lang.System.out.println("--> " + text)

    cmd = ["echo", "OK"];
    text += cmdUtils.exec(cmd);
    java.lang.System.out.println("--> " + text)

    return text;
}
