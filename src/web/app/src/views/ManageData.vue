<template>
  <div>
    <div><h2>Select the criteria below to specify dataset to display</h2>
    <h2>THIS ONE IS {{reviews}}</h2>
<div id="vue-instance">
    <el-row type="flex" :gutter="16" align="middle" justify="center"> 
    <select class="form-control" @change="changeRecord($event)" dir="ltr">
    <option value="" selected disabled style="text-align: middle;" dir="ltr">Record Type</option>
    <option v-for="record in recordTypeData" :value="record.Type" :key="record.id" style="text-align: middle;" dir="ltr">{{record.Type}}</option>
  </select>
  <br><br>   
  <select class="form-control" @change="changeVersion($event)" dir="ltr">
    <option value="" selected disabled style="text-align: middle;" dir="ltr">Record Version</option>
    <option v-for="record in authorkk" :value="record.version" :key="record.id" style="text-align: middle;" dir="ltr">{{record.version}}</option>
  </select>
  <br><br>
  
  <select class="form-control" @change="changeConference($event)" dir="ltr">
    <option value="" selected disabled style="text-align: middle;" dir="ltr">Conference</option>
    <option v-for="record in conferenceTypeData" :value="record.Type" :key="record.id" style="text-align: middle;" dir="ltr">{{record.Type}}</option>
  </select>
  
    </el-row>
        <el-row type="flex" :gutter="16" align="middle" justify="center">    
    <el-button type="primary" class="button" @click="getThem">Import Data</el-button>
      </el-row>

</div>
<div v-if="display">
<h2>HWRBVFERJHVBEJ ERGFERGV {{recordType}}</h2>
</div>

  <v2-table :data="authorRecordz" v-if="displayAuthor" border=true stripe=true
  :loading="loading" 
    :total="total"
    :shown-pagination="true"
    :pagination-info="paginationInfo"
    @page-change="handlePageChange">
    <v2-table-column label="id" prop="id"></v2-table-column>
    <v2-table-column label="version" prop="version"></v2-table-column>
    <v2-table-column label="type" prop="type"></v2-table-column>
    <v2-table-column label="submissionId" prop="submissionId"></v2-table-column>  
    <v2-table-column label="firstName" prop="firstName"></v2-table-column>  
    <v2-table-column label="lastName" prop="lastName"></v2-table-column>  
    <v2-table-column label="email" prop="email"></v2-table-column>  
    <v2-table-column label="country" prop="country"></v2-table-column>  
    <v2-table-column label="organisation" prop="organisation"></v2-table-column> 
    <v2-table-column label="webPage" prop="webPage"></v2-table-column> 
    <v2-table-column label="personId" prop="personId"></v2-table-column> 
    <v2-table-column label="isCorresponding" prop="isCorresponding"></v2-table-column> 
  </v2-table>
  </div>
    
</div>
</template>


<script>
import Vue from 'vue';
Vue.use(DropDownListPlugin);
import  {DropDownListPlugin} from '@syncfusion/ej2-vue-dropdowns';
import { DataManager,WebApiAdaptor } from '@syncfusion/ej2-data';
import VueTableDynamic from 'vue-table-dynamic';
import 'beautify-scrollbar/dist/index.css'; 
import 'v2-table/dist/index.css'; 
import V2Table from 'v2-table';
Vue.use(V2Table);



  export default {
    name: 'ManageData',
    data: function() {
    return {
      recordTypeData: [
          { id: '1', Type: 'AuthorRecord' },
          { id: '2', Type: 'ReviewRecord' },
          { id: '3', Type: 'SubmissionRecord' }
        ],
    conferenceTypeData:[
        { id: '1', Type: 'EasyChair' },
        { id: '2', Type: 'SoftCon' }

    ],
    currentPage: 1,
        total: 624,
        loading: false,
        paginationInfo: {
          text: '<span>Total of <strong>25</strong>, <strong>25</strong> per page</span>'
        },
    authorRecordz:null,
    versionData:null,
    conference:null,
    recordType: null, 
    version:null,
    displayAuthor:false,
    displayReview:false,
    displaySubmission:false,
    page: 0,
    displayData: null,
    componentViews: ['start', 'first-video'],
    display:false,
    params: {
        data: [
          ['Index', 'Data1', 'Data2', 'Data3'],
          [1, 'b3ba90', '7c95f7', '9a3853'],
          [2, 'ec0b78', 'ba045d', 'ecf03c'],
          [3, '63788d', 'a8c325', 'aab418']
        ],
        header: 'row',
        border: true
      },
      paramz: {
        data: [
          { id: '1', Type: 'AuthorRecord' },
          { id: '2', Type: 'ReviewRecord' },
          { id: '3', Type: 'SubmissionRecord' }
        ],
        header: 'row',
        border: true
      },
      testData:null
    
    };
  },
  computed: {
    authorkk(){
        let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => new Object({version: v.version.versionId, type: v.version.recordType}))));
    
        function unique(arr, keyProps) {
        return Object.values(arr.reduce((uniqueMap, entry) => {
            const key = keyProps.map(k => entry[k]).join('|');
            if (!(key in uniqueMap)) uniqueMap[key] = entry;
            return uniqueMap;
        }, {}));     
        }
        authorCount=unique(authorCount,['version','type']);
        authorCount = authorCount.map((x, i) => {
            x['id'] = i + 1
            return x
        })
        return authorCount;

    },
    reviews(){
      let reviews=Array.from(new Set(this.$store.state.record.ReviewRecordList));
      return reviews;

    }
      },
    methods: {
      loadBanner() {
        this.show = true;
      },
      authorVersionsSize() {
        //let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => new Object({version: v.version.versionId, id: v.id}))));
        let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => new Object({version: v.version.versionId, type: v.version.recordType}))));
    
        function unique(arr, keyProps) {
        return Object.values(arr.reduce((uniqueMap, entry) => {
            const key = keyProps.map(k => entry[k]).join('|');
            if (!(key in uniqueMap)) uniqueMap[key] = entry;
            return uniqueMap;
        }, {}));     
        }
        authorCount=unique(authorCount,['version','type']);
        authorCount = authorCount.map((x, i) => {
            x['id'] = i + 1
            return x
        })
        return authorCount;
      },

      changeRecord (event) {
      //this.user.address.record = event.target.value
      //this.selectedrecord = event.target.options[event.target.options.selectedIndex].text
      this.recordType=event.target.value;
    },
    changeVersion (event) {
      //this.user.address.record = event.target.value
      //this.selectedrecord = event.target.options[event.target.options.selectedIndex].text
      this.version=event.target.value;
    },
    changeConference (event) {
      //this.user.address.record = event.target.value
      //this.selectedrecord = event.target.options[event.target.options.selectedIndex].text
      this.conference=event.target.value;
    },
    getData(){
        if (this.recordType=="AuthorRecord")
        {return true;}
    },
    updateComponent(){
        this.display=true;
    	//this.page += 1;
    },
    authorRecords(){
        //var flatten = require('flat');
        //let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList));
        let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => new Object({
            id:v.id, version: v.version.versionId, type: v.version.recordType,submissionId:v.submissionId,
            firstName:v.firstName,lastName:v.lastName,email:v.email,
            country:v.country,organisation:v.organisation,webPage:v.webPage,
            personId:v.personId,isCorresponding:v.isCorresponding}))));

        //authorCount=flatten(authorCount);
        return authorCount;
      },
    getThem(){
        if (this.recordType=="AuthorRecord")
        {
        this.displayAuthor=true;
        this.$store.dispatch('getAuthorRecordVersionList',this.version);
        //authorss=this.$store.state.record.AuthorRecordVersionList;
        //this.displayData = authorMapping(authorss);
        this.authorRecordz=Array.from(new Set(this.$store.state.record.AuthorRecordVersionList.map(v => new Object({
            id:v.id, version: v.version.versionId, type: v.version.recordType,submissionId:v.submissionId,
            firstName:v.firstName,lastName:v.lastName,email:v.email,
            country:v.country,organisation:v.organisation,webPage:v.webPage,
            personId:v.personId,isCorresponding:v.isCorresponding}))));}
    },
    handlePageChange(page) {
        this.currentPage = page;
        this.loading = true;
        let start = (page - 1) * 50 + 1;

        const list = this.authorRecordz.map(item => {
          return Object.assign({}, item, {
            name: `test${start++}`
          });
        });

        setTimeout(() => {
          this.loading = false;
          this.authorRecordz = [].concat(list);
        }, 2000);  
      }

    },
  mounted() { 
        //this.loadBanner();
        Promise.all([
        //this.$store.dispatch('getConferenceList'),
        //this.$store.dispatch('getAuthorRecordList'),
        this.$store.dispatch('getReviewRecordList'),
        this.$store.dispatch('getSubmissionRecordList')
        ]).finally(() => {
        // using "finally" so even if there are errors, it stops "loading"
        this.loading = false})
        //this.testData=this.testVerionsSize();
        //this.testData=this.authorRecordByVersion();
        this.testData=this.authorVersionsSize() ;
        //this.authorRecordz=this.authorRecords();
        this.loadBanner();

        //this.displayData=this.authorRecords();
        },
    components: { VueTableDynamic }

    
  }
  
</script>


<style scoped>
.el-button{
    height:50px;
    width:210px;
    background-color: midnightblue;
}
.dropdownlist{
    background-color: aqua;
}
h2{
    margin-top: 30px;
    text-align:center;
}
.v2-table{
    font-size: 10px;
    text-emphasis: bold;
}
.form-control{
    align-content:right;
    text-align: right;
    text-emphasis: bold;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 19px;
    font-weight: bold;
    margin:10px;
    color: aliceblue;
    align-content: center;
    align-items: center;
    height:50px;
    width:210px;
    initial-letter-align: center;
    -moz-box-align: center;
    border: 100px;
    border-radius: 6px;
    border-block-style: groove;
    background-color: dodgerblue;
}
select {
    text-align-last: center;
}

option {
    direction: initial;
}

@import url(https://cdn.syncfusion.com/ej2/material.css);

</style>