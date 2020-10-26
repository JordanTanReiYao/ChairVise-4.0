package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.util.List;

public interface ReviewRecordRepository extends JpaRepository<ReviewRecord, Long> {

    //List<ReviewRecord> findByDataSetEquals(String dataSet);
    List<ReviewRecord> findByVersionEquals(Version version);
    
    List<ReviewRecord> findByVersion_IdDataSet(String dataSetVersion);

    List<ReviewRecord> findByVersion_IdDataSetEqualsAndVersion_IdVersionEquals(String user, String version);

    //void deleteAllByDataSetEquals(String dataSet);
    void deleteAllByVersionEquals(Version version);
}
