

function toText(queryString, payloadString) {
    var text = "";
    var query = JSON.parse(queryString);
    var payload = JSON.parse(payloadString);

    //java.lang.System.out.println(queryString)

    var pr = payload.pull_request;

    if (pr) {
        text += "#### " + "[PR](" + pr.html_url + ") #### \n";
        text += "" + "\n";

        text += "| name                | text                                  | other |" + "\n";
        text += "|:--------------------|:--------------------------------------|:------|" + "\n";
        text += "| status              | " + payload.action                + " | -     |" + "\n";
        text += "| number              | " + pr.number                     + " | -     |" + "\n";
        text += "| title               | " + pr.title                      + " | -     |" + "\n";
        text += "| url                 | " + pr.html_url                   + " | -     |" + "\n";
        text += "| created_at          | " + pr.created_at                 + " | -     |" + "\n";
        text += "| updated_at          | " + pr.updated_at                 + " | -     |" + "\n";
        text += "| body                | " + pr.body                       + " | -     |" + "\n";
        text += "| review_comments_url | " + pr.review_comments_url        + " | -     |" + "\n";
        text += "| user                | " + "@"+ pr.user.login            + " | -     |" + "\n";
        text += "| repository          | " + payload.repository.full_name  + " | -     |" + "\n";

    }


    return text;
}
