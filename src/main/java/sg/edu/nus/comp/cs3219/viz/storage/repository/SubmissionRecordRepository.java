package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.util.List;

public interface SubmissionRecordRepository extends JpaRepository<SubmissionRecord, Long> {

    //List<SubmissionRecord> findByDataSetEquals(String dataSet);
    List<SubmissionRecord> findByVersionEquals(Version version);
    List<SubmissionRecord> findByVersion_IdDataSet(String dataSetVersion);
    List<SubmissionRecord> findByVersion_IdDataSetEqualsAndVersion_IdVersionEquals(String user, String version);
    //void deleteAllByDataSetEquals(String dataSet);

    @Transactional
    @Modifying
    void deleteAllByVersion_IdDataSetEqualsAndVersion_IdVersionEquals(String user, String version);
    
    void deleteAllByVersionEquals(Version version);
}
