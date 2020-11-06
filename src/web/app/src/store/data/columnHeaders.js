export default {
    others: {
        no_mapping: []
    },
    easychair: {
        author: [
            "submission #", "first name", "last name", "email", "country", "organization", "Web page", "person #", "corresponding?"
        ],
        review: [
            "submission#", "review#", "review_assignment#", "reviewer name", "field#", "overall evaluation - score (ignore)", "review_comments", "overall evaluation score", null, "recommendation for best paper", "Date of review submission", "Time of review submission"
        ],
        submission: [
            "#", "track #", "track name", "title", "authors", "submitted","last updated", "keywords", "decision", "notified", "reviews sent", "abstract"
        ]
    },
    softconf: {
        author: [
            "Submission Number", "First Name", "Last Name", "Email", "Country", "Affiliation", null, null, null
        ],
        review: [
            "Submission ID", null, null, "Review Full Name (generated by chairvise)", null, "Reviewer Confidence", "Detailed Comments", "Recommendation", null, null, "Latest Modification Date (generated by chairvise)", "Latest Modification Time (generated by chairvise)"
        ],
        submission: [
            "Submission ID", null, "Track", "Title", "Authors", "Submission Date", null, null, "Acceptance Status", null, null, "Summary"
        ]
    }
}