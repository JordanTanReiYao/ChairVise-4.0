package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sg.edu.nus.comp.cs3219.viz.common.util.Deserializer.VersionDeserializer;
import sg.edu.nus.comp.cs3219.viz.common.util.Serializer.VersionSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@JsonSerialize(using = VersionSerializer.class)
@JsonDeserialize(using = VersionDeserializer.class)
@Exportable(name = "Version", nameInDB = "version")
@Entity
public class Version {

    @EmbeddedId
    private VersionPK id;

    public Version(){}

    public Version(VersionPK pk){
        this.id = pk;
    }

    public VersionPK getId(){return id;}

    public void setId(VersionPK id){this.id = id;}

    @Embeddable
    public static class VersionPK implements Serializable{

        @Column(name = "data_set ")
        private String dataSet;
        @Column(name = "record_type")
        private String recordType;
        @Column(name = "version", length = 32)
        private String version;
        @Column(name = "format_type", length = 32)
        private String formatType;

        public VersionPK(){}

        public VersionPK(String dataSet, String recordType, String version, String formatType){
            System.out.println("from VersionPK");
            this.dataSet = dataSet;
            this.recordType = recordType;
            this.version = version;
            switch (formatType) {
                case "1":
                this.formatType = "EasyChair";
                break;
                case "2":
                this.formatType = "SoftConf";
                break;
            }
            System.out.println("dataset: " + dataSet);
            System.out.println("recordType: " + recordType);
            System.out.println("version: " + version);
            System.out.println("formatType: " + this.formatType);
        }

        public String getRecordType(){return recordType;}

        public void setRecordType(String recordType){this.recordType=recordType;}

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDataSet() {
            return dataSet;
        }

        public void setDataSet(String dataSet) {
            this.dataSet = dataSet;
        }

        public String getFormatType() {
            return formatType;
        }

        public void setFormatType(String formatType) {
            this.formatType = formatType;
        }

        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VersionPK that = (VersionPK) o;
            return this.dataSet.equals(that.dataSet) && this.recordType.equals((that.recordType)) && (this.version == that.version) && (this.formatType == that.formatType);
        }

        @Override
        public int hashCode(){
            return Objects.hash(dataSet,recordType,version,formatType);
        }
    }
}