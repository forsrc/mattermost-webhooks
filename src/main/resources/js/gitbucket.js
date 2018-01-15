

function toText(queryString, payloadString) {
    var text = "";
    var query = JSON.parse(queryString);
    var payload = JSON.parse(payloadString);

    //java.lang.System.out.println(queryString)

    var pr = payload.pull_request;


    var review = payload.review;
    if (review) {
        text += "\n#### " + "[PR review](" + review.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                  | other |" + "\n";
        text += "|:--------------------|:--------------------------------------|:------|" + "\n";
        text += "| user                | " +  "@" + review.user.login      + " | -     |" + "\n";
        text += "| url                 | " + review.html_url               + " | -     |" + "\n";
        //text += "| body                | " + review.body                   + " | -     |" + "\n";
        text += "| url                 | " + review.html_url               + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + review.body;

    }

    var comment = payload.comment;
    if (review) {
        text += "\n#### " + "[PR comment](" + comment.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| user                | " + "@" + comment.user.login       + " | -     |" + "\n";
        text += "| url                 | " + comment.html_url               + " | -     |" + "\n";
        //text += "| body                | " + comment.body                   + " | -     |" + "\n";
        text += "| url                 | " + comment.html_url               + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name   + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + pr.body;
    }

    var pusher = payload.pusher;
    if (pusher) {
        text += "\n#### " + "[Push](" + payload.compare + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| pusher              | " + "@" + pusher.name              + " | -     |" + "\n";
        text += "| compare             | " + payload.compare                + " | -     |" + "\n";
        var commits = payload.commits;
        var commit = null;
        var commitText = "";
        text += "** commits: ** \n";
        for (var key in commits) {
            commit = commits[key];
            text += "- [x] " + commit.message + " ⇒  @" + commit.committer.name + "\n";
        }
        
    }

    if (pr) {
        text += "\n#### " + "[PR](" + pr.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                  | other |" + "\n";
        text += "|:--------------------|:--------------------------------------|:------|" + "\n";
        text += "| status              | " + payload.action                + " | -     |" + "\n";
        text += "| number              | " + pr.number                     + " | -     |" + "\n";
        text += "| title               | " + pr.title                      + " | -     |" + "\n";
        text += "| url                 | " + pr.html_url                   + " | -     |" + "\n";
        text += "| created_at          | " + pr.created_at                 + " | -     |" + "\n";
        text += "| updated_at          | " + pr.updated_at                 + " | -     |" + "\n";
        //text += "| body                | " + pr.body                       + " | -     |" + "\n";
        text += "| review_comments_url | " + pr.review_comments_url        + " | -     |" + "\n";
        text += "| sender              | " + "@"+ payload.sender.login     + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name  + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + pr.body;
    }
    return text;
}
