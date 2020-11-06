<template>
  <el-main>
    <el-alert
      title="You need to login-in to view the page"
      type="error"
      v-if="!isLogin && !isAppLoading">
      &nbsp;<el-button type="warning" plain size="mini" @click="navigateToHomePage">Return to the Home Page</el-button>
    </el-alert>

    <div v-if="isLogin">
      <mapping-tool v-if="isReadyForMapping" ref="mapTool"></mapping-tool>

      <el-card v-else>
        <div slot="header" class="clearfix">
          <span>Upload Data</span>
        </div>
      
      <div class="section">
        <h2> Record Information </h2>
        <el-divider></el-divider>
        
        <div class="form-card">
          <label class="label"> Conference Type </label>
          <br/>
          <el-radio-group v-model="formatType" size="medium">
            <el-radio-button :label="1">EasyChair</el-radio-button>
            <el-radio-button :label="2">SoftConf</el-radio-button>
            <el-radio-button :label="3">Others</el-radio-button>
          </el-radio-group>
        </div>

        <div class="form-card">
          <label class="label"> Table Type </label>
          <br/>
          <el-radio-group v-model="tableType" size="medium">
            <el-radio-button v-for="(schema, idx) in dbSchemas" 
              :label="idx" 
              :key="schema.name"> 
              {{ schema.name }}
            </el-radio-button>
          </el-radio-group>
        </div>
      </div>

      <div class="section" v-if="isFormatTypeOthers">
        <h2>
          Mapping Information

          <el-tooltip placement="top">
            <div slot="content">
              Optional
            </div>
            <el-button type="text" icon="el-icon-info" circle></el-button>
          </el-tooltip>
        </h2>
        <el-divider></el-divider>

        <div class="form-card">
          <el-switch
            v-model="hasHeader"
            active-text="Has Header"
            inactive-text="No Header">
          </el-switch>
        </div>

        <!-- <div class="form-card" >
          <el-switch
            v-model="hasPredefined"
            active-text="Predefined Mapping"
            inactive-text="No Predefined Mapping">
          </el-switch>
        </div>  -->
      </div>
      
      <div class="section" v-if="isReadyForChoosing">
        <h2> 
          Version Information

          <el-tooltip placement="top">
            <div slot="content">
              If the input version is an existing version, current record will be replaced based on record type.
              <br/>
              If the input version is a new version, current record will be created based on record type.
            </div>
            <el-button type="text" icon="el-icon-question" circle></el-button>
          </el-tooltip>
        </h2>        
        <el-divider></el-divider>
        
        <el-row class="form-card">
          <el-col>
            <label class="label">
              Version
            </label>
            <br/>
            <el-autocomplete
              class="inline-input"
              v-model="versionId"
              :fetch-suggestions="querySearch"
              placeholder="Input Version"
            ></el-autocomplete>
          </el-col>
        </el-row>
        <div class="form-card">
          <el-upload v-if="isReadyForUpload" drag action=""
                    :auto-upload="false"
                    :show-file-list="false"
                    :multiple="false"
                    :on-change="fileUploadHandler">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">Drop .csv file here or <em>click to upload</em></div>
          </el-upload>
        </div>
      </div>

      </el-card>
    </div>
  </el-main>
</template>

<script>
  import MappingTool from "@/components/MappingTool.vue";
  import Papa from "papaparse";
  import {REVIEW_DATE_DAY_FIELD, REVIEW_DATE_TIME_FIELD, REVIEW_TABLE_ID} from "@/common/const"
  import {deepCopy, anonymizeName, generatePredefinedMapping} from "@/common/utility"
  import PredefinedMappings from "@/store/data/predefinedMapping"
  import moment from "moment"

  export default {
    name: "ImportData",
    data() {
      return {
        predefinedMappings: PredefinedMappings
      };
    },
    beforeCreate() {
      this.$store.dispatch('fetchDBMetaDataEntities');
      this.$store.dispatch('getVersionList');
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin;
      },
      isAppLoading: function () {
        return this.$store.state.isPageLoading || this.$store.state.dbMetaData.entitiesStatus.isLoading;
      },
      dbSchemas: function () {
        return this.$store.state.dbMetaData.entities;
      },
      formatType: {
        get: function () {
          return this.$store.state.dataMapping.data.formatType;
        },
        set: function (newValue) {
          this.$store.commit("setFormatType", newValue);
          if (newValue != "3") {
            this.$store.commit("setHasHeader", true);
            this.$store.commit("setPredefinedSwitch", true);
          } else {
            this.$store.commit("setHasHeader", false);
            this.$store.commit("setPredefinedSwitch", false);
          }
        }
      },
      tableType: {
        get: function () {
          return this.$store.state.dataMapping.data.tableType;
        },
        set: function (newValue) {
          let dbSchema = deepCopy(this.dbSchemas[newValue]);
          if (newValue === REVIEW_TABLE_ID) {
            dbSchema.fieldMetaDataList.push(REVIEW_DATE_DAY_FIELD);
            dbSchema.fieldMetaDataList.push(REVIEW_DATE_TIME_FIELD);
          }
          this.$store.commit("setTableType", newValue);
          this.$store.commit("setDBSchema", dbSchema);
        }
      },
      versionId: {
        get: function () {
          return this.$store.state.dataMapping.data.versionId;
        },
        set: function (newValue) {
          this.$store.commit("setVersionId", newValue);
        }
      },
      hasHeader: {
        get: function () {
          return this.$store.state.dataMapping.data.hasHeader;
        },
        set: function (newValue) {
          this.$store.commit("setHasHeader", newValue);
        }
      },
      hasPredefined: {
        get: function () {
          return this.$store.state.dataMapping.data.hasPredefined;
        },
        set: function (newValue) {
          this.$store.commit("setPredefinedSwitch", newValue);
        }
      },
      predefinedMappingId: {
        get: function () {
          return this.$store.state.dataMapping.data.predefinedMappingId;
        },
        set: function (newValue) {
          this.$store.commit("setPredefinedMapping", {id: newValue, mapping: PredefinedMappings[newValue].mapping});
        }
      },
      isReadyForMapping: function () {
        return this.$store.state.dataMapping.hasFileUploaded
          && this.$store.state.dataMapping.hasFormatTypeSpecified
          && this.$store.state.dataMapping.hasTableTypeSelected
          && this.$store.state.dataMapping.hasHeaderSpecified
          && this.$store.state.dataMapping.hasPredefinedSpecified
          && this.$store.state.dataMapping.hasVersionIdSpecified;
      },
      uploaded: function () {
        return this.$store.state.dataMapping.hasFileUploaded;
      },
      isReadyForUpload: function () {
        return this.$store.state.dataMapping.hasFormatTypeSpecified
         && this.$store.state.dataMapping.hasTableTypeSelected
         && this.$store.state.dataMapping.hasHeaderSpecified
         && this.$store.state.dataMapping.hasPredefinedSwitchSpecified
         && this.$store.state.dataMapping.hasVersionIdSpecified;
      },
      isFormatTypeOthers: function() {
        return this.$store.state.dataMapping.hasFormatTypeSpecified
        && this.$store.state.dataMapping.data.formatType == "3";
      },
      isReadyForChoosing: function () {
        return this.$store.state.dataMapping.hasTableTypeSelected;
      }
    },
    methods: {
      querySearch(queryString, cb) {
        // convert to array of string
        var links = this.$store.state.presentation.versionList.map(v => v.versionId);
        // function to remove duplicate from array of string
        let reduceFunction = (links) => links.filter((v,i) => links.indexOf(v) === i );
        links = reduceFunction(links);
        links = links.map(v => { return { "value" : v} });
        var results = queryString ? links.filter(this.createFilter(queryString)) : links;
        cb(results);
      },
      createFilter(queryString) {
        return (link) => {
          return (link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      navigateToHomePage() {
        this.$router.replace("/home");
      },
      fileUploadHandler: function (file) {
        // show loading and go parsing
        this.$store.commit("setPageLoadingStatus", true);

        // if versionList is empty
        // console.log(this.$store.state.presentation.versionList);
        // filter by "AuthorRecord" "ReviewRecord" "SubmissionRecord"
        if (!this.$store.state.presentation.versionList) {
          this.$store.commit("setIsNewVersion", false);
        } else {
          // if tabletype 0 author elif 1 review elif 2 sub
          // filter by "AuthorRecord" "ReviewRecord" "SubmissionRecord"
          var verList;
          switch (this.$store.state.dataMapping.data.tableType) {
            case 0:
              verList = this.$store.state.presentation.versionList
                        .filter(v => v.recordType === "AuthorRecord")
                        .map(v => v.versionId);
              break;
            case 1:
              verList = this.$store.state.presentation.versionList
                        .filter(v => v.recordType === "ReviewRecord")
                        .map(v => v.versionId);
              break;
            case 2:
              verList = this.$store.state.presentation.versionList
                        .filter(v => v.recordType === "SubmissionRecord")
                        .map(v => v.versionId);
              break;
            default:
          }
          this.$store.commit("setIsNewVersion", 
                            !verList.includes(this.$store.state.dataMapping.data.versionId));
        }

        Papa.parse(file.raw, {
          // ignoring empty lines in csv file
          skipEmptyLines: true,
          complete: function (result) {
            // Other conference types
            if(this.$store.state.dataMapping.data.formatType=="3"){
              this.$store.commit("setUploadedFile",result.data);
              this.$store.commit("setPageLoadingStatus", false);
              mapping = generatePredefinedMapping(result.data[0], "others", "no_mapping");
              this.$store.commit("setPredefinedMapping", {id: -1, mapping});
              return;
            }

          var res=result;
          var res2=res.data;
          // var verId = this.$store.state.dataMapping.data.versionId;
          // var element;
          var mapping;
          var l;

          //author file preprocessing
          if( this.$store.state.dataMapping.data.tableType=="0" ){
              // easychair
              // generate predefinedMapping by headers instead of hard coded column numbers
              let firstNameIdx;
              let lastNameIdx;
              if(this.$store.state.dataMapping.data.formatType=="1"){
                mapping = generatePredefinedMapping(res2[0], "easychair", "author");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});
                firstNameIdx = res2[0].indexOf("first name");
                lastNameIdx = res2[0].indexOf("last name");

              // softconf
              } else if(this.$store.state.dataMapping.data.formatType=="2"){
                mapping = generatePredefinedMapping(res2[0], "softconf", "author");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});
                firstNameIdx = res2[0].indexOf("First Name");
                lastNameIdx = res2[0].indexOf("Last Name");

              }
              // anonymize author names for both formats
              for (l = 1; l < res2.length; l++) {
                res2[l][firstNameIdx] = anonymizeName(res2[l][firstNameIdx]);
                res2[l][lastNameIdx] = anonymizeName(res2[l][lastNameIdx]);
              }
          }

          //review file preprocessing
          else if( this.$store.state.dataMapping.data.tableType=="1" ){
              //easychair
              if(this.$store.state.dataMapping.data.formatType=="1"){
                mapping = generatePredefinedMapping(res2[0], "easychair", "review");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});
              //Softconf
              } else if (this.$store.state.dataMapping.data.formatType=="2"){
                res2[0].push("Review Full Name (generated by chairvise)");
                res2[0].push("Latest Modification Date (generated by chairvise)");
                res2[0].push("Latest Modification Time (generated by chairvise)");
                var firstNameIdx = res2[0].indexOf("Reviewer First Name");
                var lastNameIdx = res2[0].indexOf("Reviewer Last Name");
                var timeIdx = res2[0].indexOf("Latest Modification Time");
                var date_time, date, time, name;
                for (l = 1; l < res2.length; l++) {
                    name = res2[l][firstNameIdx] + " " + res2[l][lastNameIdx];
                    date_time = res2[l][timeIdx].split(" ");
                    date=date_time[0];
                    time=date_time[1].split(":")[0]+":"+date_time[1].split(":")[1];
                    res2[l].push(name); res2[l].push(date); res2[l].push(time);
                }
                mapping = generatePredefinedMapping(res2[0], "softconf", "review");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});
              }

              //author anonymization - JCDL
              // Easy Chair
              else if(this.$store.state.dataMapping.data.formatType=="1"){
                // var convert_string=require("convert-string");
                for(var index=1;index<res2.length;index++){
                    // var convert=convert_string.stringToBytes(res2[index][3]);
                    // var name="";
                    // for(var idx=0;idx<convert.length;idx++){
                    //     name=name.concat(String(convert[idx]+18));
                    // }
                    // res2[index][3]=name;
                    res2[index][3] = anonymizeName(res2[index][3]);
                }
              }
            }

           //ACL submission file processing
            else if( this.$store.state.dataMapping.data.tableType=="2" ){
              // easychair
              // generate predefinedMapping by headers instead of hard coded column numbers
              if(this.$store.state.dataMapping.data.formatType=="1"){
                mapping = generatePredefinedMapping(res2[0], "easychair", "submission");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});

              // softconf
              } else if(this.$store.state.dataMapping.data.formatType=="2"){
                const acceptanceIdx = res2[0].indexOf("Acceptance Status");
                const submissionDateIdx = res2[0].indexOf("Submission Date");
                for (l = 1; l < res2.length; l++) {
                  res2[l][acceptanceIdx] = res2[l][acceptanceIdx].includes("Reject") ? "reject" : "accept";
                  res2[l][submissionDateIdx] = moment(res2[l][submissionDateIdx], "D MMM YYYY HH:mm:ss").format("YYYY-M-D H:mm");
                }
                mapping = generatePredefinedMapping(res2[0], "softconf", "submission");
                this.$store.commit("setPredefinedMapping", {id: -1, mapping});

              }
            }

            this.$store.commit("setUploadedFile",res2);
            this.$store.commit("setPageLoadingStatus", false);
          }.bind(this)
        });
      }
    },
    components: {
      MappingTool
    }
  };
</script>

<style scoped>
  .upload-box {
    /*padding-top: 100px; */
  }

  .upload-box .el-select {
    margin-top: 20px;
  }

  .button-row {
    margin-top: 30px;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
    position: relative;
    left: 50%;
    margin-left: -240px;
  }

  .autocomplete-verid {
    position: relative;
  }

  .form-card {
    margin: 16px 0px;
  }

  .section {
    padding: 0px 16px 16px 16px;
  }
</style>
