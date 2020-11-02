package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.util.List;

public interface AuthorRecordRepository extends JpaRepository<AuthorRecord, Long> {

    //List<AuthorRecord> findByDataSetEquals(String dataSet);

    List<AuthorRecord> findByVersionEquals(Version version);
    List<AuthorRecord> findByVersion_IdDataSet(String dataSetVersion);
    List <AuthorRecord> findByVersion_IdDataSetEqualsAndVersion_IdVersionEquals(String user, String version);
    List<AuthorRecord> findByVersion_IdVersionEquals(String version);
    //void deleteAllByDataSetEquals(String dataSet);
    @Transactional
    @Modifying
    void deleteAllByVersion_IdDataSetEqualsAndVersion_IdVersionEquals(String user, String version);
    //@Transactional
    //@Modifying
    //void deleteAllByVersion_IdVersionEquals(String version);
    void deleteAllByVersionEquals(Version version);

}
