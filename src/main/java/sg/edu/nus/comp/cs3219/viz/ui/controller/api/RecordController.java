package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.RecordLogic;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RecordController extends BaseRestController {

    private GateKeeper gateKeeper;

    private RecordLogic recordLogic;

    public RecordController(GateKeeper gateKeeper, RecordLogic recordLogic) {
        this.gateKeeper = gateKeeper;
        this.recordLogic = recordLogic;
    }

    @GetMapping("/record/author")
    public List<AuthorRecord> authorRecordVersionList(){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findAllAuthorForUser(currentUser);
    }

    @GetMapping("/record/author/{version}")
    public List<AuthorRecord> authorRecordByVersionList(@PathVariable String version){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findAuthorByVersionForUser(currentUser,version);
    }

    @DeleteMapping("/record/author/{version}")
    public ResponseEntity<?> deleteAuthor(@PathVariable String version) {
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        recordLogic.deleteAuthorByIdandVersion(currentUser,version);
        return ResponseEntity.noContent().build();

        //return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/record/review/{version}")
    public ResponseEntity<?> deleteReview(@PathVariable String version) {
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        recordLogic.deleteReviewByIdandVersion(currentUser,version);
        return ResponseEntity.noContent().build();

        //return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/record/submission/{version}")
    public ResponseEntity<?> deleteSubmission(@PathVariable String version) {
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        recordLogic.deleteSubmissionByIdandVersion(currentUser,version);
        return ResponseEntity.noContent().build();

        //return ResponseEntity.noContent().build();
    }

    @GetMapping("/record/review")
    public List<ReviewRecord> ReviewRecordVersionList(){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findAllReviewForUser(currentUser);
    }

    @GetMapping("/record/review/{version}")
    public List<ReviewRecord> ReviewRecordByVersionList(@PathVariable String version){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findReviewByVersionForUser(currentUser,version);
    }

    @GetMapping("/record/submission")
    public List<SubmissionRecord> SubmissionRecordVersionList(){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findAllSubmissionForUser(currentUser);
    }

    @GetMapping("/record/submission/{version}")
    public List<SubmissionRecord> SubmissionRecordByVersionList(@PathVariable String version){
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return recordLogic.findSubmissionByVersionForUser(currentUser,version);
    }

    @PostMapping("/record/author")
    public ResponseEntity<?> importAuthorRecord(@RequestBody List<AuthorRecord> authorRecordList) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        this.recordLogic.removeAndPersistAuthorRecordForDataSet(userInfo.getUserEmail(), authorRecordList);

        return ResponseEntity.created(new URI("/record/author")).build();
    }

    @PostMapping("/record/review")
    public ResponseEntity<?> importReviewRecord(@RequestBody List<ReviewRecord> reviewRecordList) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        this.recordLogic.removeAndPersistReviewRecordForDataSet(userInfo.getUserEmail(), reviewRecordList);

        return ResponseEntity.created(new URI("/record/review")).build();
    }

    @PostMapping("/record/submission")
    public ResponseEntity<?> importSubmissionRecord(@RequestBody List<SubmissionRecord> submissionRecords) throws URISyntaxException {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();

        this.recordLogic.removeAndPersistSubmissionRecordForDataSet(userInfo.getUserEmail(), submissionRecords);

        return ResponseEntity.created(new URI("/record/review")).build();
    }
}
