

function toText(queryString, payloadString) {
    var text = "";
    var query = JSON.parse(queryString);
    var payload = JSON.parse(payloadString);

    //java.lang.System.out.println(queryString)

    var pr = payload.pull_request;

    var project = payload.project;
    if (project) {
        text += "\n#### " + "[project](" + project.url + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| project             | " + project.name                   + " | -     |" + "\n";
        text += "| status              | " + payload.action                 + " | -     |" + "\n";
        text += "| creator             | " + "@" + project.creator.login    + " | -     |" + "\n";
        text += "| url                 | " + project.url                    + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name   + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + project.body;
    }

    var issue = payload.issue;
    if (issue) {
        text += "\n#### " + "[issue](" + issue.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| title               | " + issue.title                    + " | -     |" + "\n";
        text += "| user                | " + "@" + issue.user.login         + " | -     |" + "\n";
        text += "| url                 | " + issue.html_url                 + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name   + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + issue.body;
    }

    var review = payload.review;
    if (review) {
        text += "\n#### " + "[Review](" + review.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                  | other |" + "\n";
        text += "|:--------------------|:--------------------------------------|:------|" + "\n";
        text += "| user                | " +  "@" + review.user.login      + " | -     |" + "\n";
        text += "| url                 | " + review.html_url               + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name  + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + review.body;
        return text;

    }

    var comment = payload.comment;
    if (comment) {
        text += "\n#### " + "[Comment](" + comment.html_url + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| user                | " + "@" + comment.user.login       + " | -     |" + "\n";
        text += "| url                 | " + comment.html_url               + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name   + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + comment.body;
        return text;
    }

    var pusher = payload.pusher;
    if (pusher) {
        text += "\n#### " + "[Push](" + payload.compare + ") #### \n";
        text += "\n";

        text += "| name                | text                                   | other |" + "\n";
        text += "|:--------------------|:---------------------------------------|:------|" + "\n";
        text += "| pusher              | " + "@" + pusher.name              + " | -     |" + "\n";
        text += "| compare             | " + payload.compare                + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name   + " | -     |" + "\n";
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
        text += "| title               | " + pr.title                      + " | -     |" + "\n";
        text += "| status              | " + payload.action                + " | -     |" + "\n";
        text += "| number              | " + pr.number                     + " | -     |" + "\n";
        text += "| url                 | " + pr.html_url                   + " | -     |" + "\n";
        text += "| created_at          | " + pr.created_at                 + " | -     |" + "\n";
        text += "| updated_at          | " + pr.updated_at                 + " | -     |" + "\n";
        //text += "| body                | " + pr.body                       + " | -     |" + "\n";
        //text += "| review_comments_url | " + pr.review_comments_url        + " | -     |" + "\n";
        text += "| sender              | " + "@"+ payload.sender.login     + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name  + " | -     |" + "\n";
        text += "** message: ** \n";
        text += "- [x] " + pr.body;
    }
    return text;
}
