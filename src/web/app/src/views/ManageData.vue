<template>
  <div>
    <div><h2>Select the criteria below to specify dataset to display</h2>
<div id="vue-instance">
    <el-row type="flex" :gutter="16" align="middle" justify="center"> 
    <select v-model="recordType" @change="changeRecord($event)" dir="ltr">
    <option :value="null" selected disabled style="text-align: middle;" dir="ltr">Record Type</option>
    <option v-for="record in recordTypeData" :value="record.Type" :key="record.id" style="text-align: middle;" dir="ltr">{{record.Type}}</option>
  </select>
  <br><br> 
  
  <select v-model="version" @change="changeVersion($event)" dir="ltr">
    <option :value="null" select disabled style="text-align: middle;" dir="ltr">Record Version</option>
    <option v-for="record in versionList" :value="record.version" :key="record.id" style="text-align: middle;" dir="ltr">{{record.version}}</option>
  </select>
  <br><br>
  <select v-model="conference" @change="changeConference($event)" dir="ltr">
    <option :value="null" selected disabled style="text-align: middle;" dir="ltr">Conference</option>
    <option v-for="record in conferenceTypeData" :value="record.Type" :key="record.id" style="text-align: middle;" dir="ltr">{{record.Type}}</option>
  </select>
  
    </el-row>
        <el-row type="flex" :gutter="16" align="middle" justify="center">
    <notifications class="my-style" group="foo" position="top center"/>    
    <el-button type="primary" class="button" @click="getThem">Retrieve Data</el-button>
    <el-button type="primary" class="button" @click="deleteRecord()">Delete Data</el-button>
      </el-row>

</div>

<div class="ddtable" style="overflow-x:auto;" v-if="displayAuthor">
  <table style="width:1000px"  class="dtable">
    <thead>
      <tr style="padding-left:5px; overflow: hidden">
        <th >id</th>
        <th >version</th>
        <th >type</th>
        <th >submissionId</th>
        <th >firstName</th>
        <th >lastName</th>
        <th >email</th>
        <th >country</th>
        <th >organisation</th>
        <th >webPage</th>
        <th>personId</th>
        <th >isCorresponding</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="record in computedUsers"  v-bind:key="record.id" style="padding-left:5px; overflow: hidden">
        
        <td>{{record.id}}</td>
        <td>{{record.version}}</td>
        <td>{{record.type}}</td>
        <td >{{record.submissionId}}</td>
        <td >{{record.firstName}}</td>
        <td >{{record.lastName}}</td>
        <td >{{record.email}}</td>
        <td >{{record.country}}</td>
        <td >{{record.organisation}}</td>
        <td >{{record.webPage}}</td>
        <td >{{record.personId}}</td>
        <td >{{record.isCorresponding}}</td>
      </tr>
    </tbody>
  </table>	
       <ul>
    <li v-for="n in numOfPages" v-bind:key="n"><a href="" @click.prevent="setPage(n)">{{n}}</a></li>
  </ul>
  </div>
<div class="ddtable" style="overflow-x:auto;" v-if="displayReview">
  <table style="width:1000px"  class="dtable">
    <thead>
      <tr style="padding-left:5px; overflow: hidden">
        <th >id</th>
        <th >version</th>
        <th >type</th>
        <th >submissionId</th>
        <th >reviewId</th>
        <th >numReviewAssignment</th>
        <th >reviewerName</th>
        <th >expertiseLevel</th>
        <th >confidenceLevel</th>
        <th >reviewComment</th>
        <th>overallEvaluationScore</th>
        <th >reviewSubmissionTime</th>
        <th >hasRecommendedForBestPaper</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="record in computedUsers"  v-bind:key="record.id" style="padding-left:5px; overflow: hidden">
        
        <td>{{record.id}}</td>
        <td>{{record.version}}</td>
        <td>{{record.type}}</td>
        <td >{{record.submissionId}}</td>
        <td >{{record.reviewId}}</td>
        <td >{{record.numReviewAssignment}}</td>
        <td >{{record.reviewerName}}</td>
        <td >{{record.expertiseLevel}}</td>
        <td >{{record.confidenceLevel}}</td>
        <td >{{record.reviewComment}}</td>
        <td >{{record.overallEvaluationScore}}</td>
        <td >{{record.reviewSubmissionTime}}</td>
        <td >{{record.hasRecommendedForBestPaper}}</td>
      </tr>
    </tbody>
  </table>	
       <ul>
    <li v-for="n in numOfPages" v-bind:key="n"><a href="" @click.prevent="setPage(n)">{{n}}</a></li>
  </ul>
  </div>
<div class="ddtable" style="overflow-x:auto;" v-if="displaySubmission">
  <table style="width:1000px"  class="dtable">
    <thead>
      <tr style="padding-left:5px; overflow: hidden">
        <th >id</th>
        <th >version</th>
        <th >type</th>
        <th >submissionId</th>
        <th >trackId</th>
        <th >trackName</th>
        <th >title</th>
        <th >authors</th>
        <th >submissionTime</th>
        <th >lastUpdatedTime</th>
        <th >keywords</th>
        <th>submissionAbstract</th>
        <th >isAccepted</th>
        <th >version</th>
        <th >isNotified</th>
        <th >isReviewsSent</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="record in computedUsers"  v-bind:key="record.id" style="padding-left:5px; overflow: hidden">
        
        <td>{{record.id}}</td>
        <td>{{record.version}}</td>
        <td >{{record.type}}</td>
        <td>{{record.submissionId}}</td>
        <td >{{record.trackId}}</td>
        <td >{{record.trackName}}</td>
        <td >{{record.title}}</td>
        <td >{{record.authors}}</td>
        <td >{{record.submissionTime}}</td>
        <td >{{record.lastUpdatedTime}}</td>
        <td >{{record.keywords}}</td>
        <td >{{record.submissionAbstract}}</td>
        <td >{{record.isAccepted}}</td>
        <td >{{record.version}}</td>
        <td >{{record.isNotified}}</td>
        <td >{{record.isReviewsSent}}</td>
      </tr>
    </tbody>
  </table>	
       <ul>
    <li v-for="n in numOfPages" v-bind:key="n"><a href="" @click.prevent="setPage(n)">{{n}}</a></li>
  </ul>
  </div>

  </div>
  
  </div>
  
    
</template>


<script>
import Vue from 'vue';

import Notifications from "vue-notification";
Vue.use(Notifications);



  export default {
    name: 'ManageData',
    data: function() {
    return {
      fields:["id","version","submissionId","trackId","trackName",
      "title","authors","submissionTime","lastUpdatedTime","keywords","submissionAbstract",
      "recordType","isAccepted","isNotified","isReviewsSent"],

      
      recordTypeData: [
          { id: '1', Type: 'AuthorRecord' },
          { id: '2', Type: 'ReviewRecord' },
          { id: '3', Type: 'SubmissionRecord' }
        ],
    conferenceTypeData:[
        { id: '1', Type: 'EasyChair' },
        { id: '2', Type: 'SoftCon' }

    ],
    
        total: 624,
        loading: false,
        paginationInfo: {
          text: '<span>Total of <strong>25</strong>, <strong>25</strong> per page</span>'
        },
    authorRecordz:null,
    reviewRecordz:null,
    submissionRecordz:null,
    versionData:null,
    versionList:null,
    conference:null,
    recordType: null, 
    version:null,
    apitest:null,
    placeholder:null,
    startRow: 0,
    currentPage: 1,
    perPage: 20,
    rowsPerPage: 10,
    displayAuthor:false,
    displayReview:false,
    displaySubmission:false,
    testOnly:null,
    displayData: [],
    componentViews: ['start', 'first-video'],
    display:false,
    params: {
        data: null,
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
    authorVersionCount(){
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
    
    reviewVersionCount(){
        let reviewCount = Array.from(new Set(this.$store.state.record.ReviewRecordList.map(v => new Object({version: v.version.versionId, type: v.version.recordType}))));
    
        function unique(arr, keyProps) {
        return Object.values(arr.reduce((uniqueMap, entry) => {
            const key = keyProps.map(k => entry[k]).join('|');
            if (!(key in uniqueMap)) uniqueMap[key] = entry;
            return uniqueMap;
        }, {}));     
        }
        reviewCount=unique(reviewCount,['version','type']);
        reviewCount = reviewCount.map((x, i) => {
            x['id'] = i + 1
            return x
        })
        return reviewCount;

    },
    submissionVersionCount(){
        let submissionCount = Array.from(new Set(this.$store.state.record.SubmissionRecordList.map(v => new Object({version: v.version.versionId, type: v.version.recordType}))));
    
        function unique(arr, keyProps) {
        return Object.values(arr.reduce((uniqueMap, entry) => {
            const key = keyProps.map(k => entry[k]).join('|');
            if (!(key in uniqueMap)) uniqueMap[key] = entry;
            return uniqueMap;
        }, {}));     
        }
        submissionCount=unique(submissionCount,['version','type']);
        submissionCount = submissionCount.map((x, i) => {
            x['id'] = i + 1
            return x
        })
        return submissionCount;

    },
    reviews(){
      let reviews=Array.from(new Set(this.$store.state.record.ReviewRecordVersionList.map(v => new Object({id: v.id,
      submissionId: v.submissionId,reviewId:v.reviewId,numReviewAssignment:v.numReviewAssignment,
      reviewerName:v.reviewerName,expertiseLevel:v.expertiseLevel,confidenceLevel:v.confidenceLevel,
      reviewComment:v.reviewComment,overallEvaluationScore:v.overallEvaluationScore,
      reviewSubmissionTime:v.reviewSubmissionTime,hasRecommendedForBestPaper:v.hasRecommendedForBestPaper,
      version: v.version.versionId,
       type: v.version.recordType}))));
      return reviews;

    },
    submissions(){
      let submission=Array.from(new Set(this.$store.state.record.SubmissionRecordVersionList.map(v => new Object({id: v.id,
      submissionId: v.submissionId,trackId:v.trackId,trackName:v.trackName,title:v.title,
      authors:v.authors,submissionTime:v.submissionTime,lastUpdatedTime:v.lastUpdatedTime,
      keywords:v.keywords,submissionAbstract:v.submissionAbstract,version:v.version.versionId,
      recordType:v.version.recordType,isAccepted:v.isAccepted,isNotified:v.isNotified,
      isReviewsSent:v.isReviewsSent}))));
      return submission;

    },
    offset() {
      return ((this.currentPage-1)*this.perPage);
    },
    limit() {
      return (this.offset+this.perPage);
    },
    numOfPages() {
      return Math.ceil(this.displayData.length/this.perPage);
    },
    computedUsers() {
      
      return this.displayData.slice(this.offset,this.limit);
    }
      },
    watch:{
      computedpage() {
      if (this.offset > this.displayData.length) {
        this.currentPage = this.numOfPages;
      }
    }
    },
    methods: {
       movePages: function(amount) {
      var newStartRow = this.startRow + (amount * this.rowsPerPage);
        this.startRow = newStartRow;
      }
    ,setPage(n) {
      this.currentPage = n;
    },
    
      loadBanner() {
        this.show = true;
      },
      
      authorVersionsSize() {
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
      
      this.recordType = event.target.value;
      if (this.recordType=="AuthorRecord"){
        this.versionList=this.authorVersionCount;
      }
      if (this.recordType=="ReviewRecord"){
        this.versionList=this.reviewVersionCount;
      }
      if (this.recordType=="SubmissionRecord"){
        
        this.versionList=this.submissionVersionCount;
      }

      this.version=null;
      this.conference=null;
    },
    changeVersion (event) {
      this.version=event.target.value;
    },
    changeConference (event) {
      
      this.conference=event.target.value;
    },
    getThem(){
        if (this.version==null || this.recordType==null || this.conference==null)
        {
            
            this.$notify({
            group: "foo",
            title: "Important message",
            text: "Please specify all criteria of record to retrieve!",
        })
            return
        }
        if (this.recordType=="AuthorRecord")
        {
        this.displayReview=false;
        this.displaySubmission=false;
        this.$store.dispatch('getAuthorRecordVersionList',this.version);
        
        setTimeout(()=>{
        this.displayData=Array.from(new Set(this.$store.state.record.AuthorRecordVersionList.map(v => new Object({
            id:v.id, version: v.version.versionId, type: v.version.recordType,submissionId:v.submissionId,
            firstName:v.firstName,lastName:v.lastName,email:v.email,
            country:v.country,organisation:v.organisation,webPage:v.webPage,
            personId:v.personId,isCorresponding:v.isCorresponding}))));
            this.displayAuthor=true;},1000)
            ;}

        if (this.recordType=="ReviewRecord")
        {
        this.displayAuthor=false;
        this.displaySubmission=false;
        this.$store.dispatch('getReviewRecordVersionList',this.version);
        
        setTimeout(()=>{
        this.displayReview=true;
        this.displayData=Array.from(new Set(this.$store.state.record.ReviewRecordVersionList.map(v => new Object({id: v.id,
          submissionId: v.submissionId,reviewId:v.reviewId,numReviewAssignment:v.numReviewAssignment,
          reviewerName:v.reviewerName,expertiseLevel:v.expertiseLevel,confidenceLevel:v.confidenceLevel,
          reviewComment:v.reviewComment,overallEvaluationScore:v.overallEvaluationScore,
          reviewSubmissionTime:v.reviewSubmissionTime,hasRecommendedForBestPaper:v.hasRecommendedForBestPaper,
          version: v.version.versionId,
          type: v.version.recordType}))))},1000);}

        if (this.recordType=="SubmissionRecord")
        {
          this.displayAuthor=false;
          this.displayReview=false;
          this.$store.dispatch('getSubmissionRecordVersionList',this.version);

          setTimeout(()=>{
          this.displaySubmission=true;
          this.displayData=Array.from(new Set(this.$store.state.record.SubmissionRecordVersionList.map(v => new Object({id: v.id,
          submissionId: v.submissionId,trackId:v.trackId,trackName:v.trackName,title:v.title,
          authors:v.authors,submissionTime:v.submissionTime,lastUpdatedTime:v.lastUpdatedTime,
          keywords:v.keywords,submissionAbstract:v.submissionAbstract,version:v.version.versionId,
          type:v.version.recordType,isAccepted:v.isAccepted,isNotified:v.isNotified,
          isReviewsSent:v.isReviewsSent}))))},1000);
          }
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
      },
    onPaginationData (paginationData) {
    this.$refs.pagination.setPaginationData(paginationData)
    },
    onChangePage (page) {
      this.$refs.vuetable.changePage(page)
    },
    deleteRecord() {
        
        if (this.version==null || this.recordType==null || this.conference==null)
        {
            
            this.$notify({
            group: "foo",
            title: "Important message",
            text: "Please specify all criteria of record to delete!",
        })
            return
        }
         
        if (this.recordType=="AuthorRecord"){
        this.$store.dispatch('deleteAuthorRecord', this.version)
        }
        else if (this.recordType=="ReviewRecord"){
        this.$store.dispatch('deleteReviewRecord', this.version)
        }
        else if (this.recordType=="SubmissionRecord"){
        this.$store.dispatch('deleteSubmissionRecord', this.version)
        }

        this.$notify({
          group: "foo",
          title: "Important message",
          text: "Deleting Records!",
        })
        window.location.reload()
        
      }

    },
  mounted() { 
        
        Promise.all([
        this.$store.dispatch('getAuthorRecordList'),
        this.$store.dispatch('getReviewRecordList'),
        this.$store.dispatch('getSubmissionRecordList'),
        
        ]).finally(() => {
        
        this.loading = false})
        
        this.loadBanner();
        this.apitest=this.submissions;
        this.params.data=this.submissions;

        },

    

    
  }
  
</script>


<style scoped>
.el-button{
    height:50px;
    width:210px;
    font-weight: bold;
    font-size: 16px;
    margin:5px;
    margin-bottom:25px;
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
    border: 10px;
    margin-left:10px;
    margin-right:30px;
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
  border-radius: 10px;
  height:50px;
  width:220px;
  background-color:dodgerblue;
  color:white;
  font-size: 20px;
  font-weight: bold;
  text-align-last: center;
  margin:5px;
}

option {
    direction: initial;
}
ul {
  margin-left:15px;
  list-style: none;
  padding: 0;}
li {
    display: inline;
    padding-right: 5px;
    padding:5px;
  }
table,th,td,tr{
  border-radius: 0.25em;
  border: 1px solid black;
  border-collapse: collapse;
  width:auto;
}
table.dtable{
  margin-left:auto;
  margin-right:auto;
  
}
.ddtable{
  margin-left: 25px;
  margin-right: 25px;
}

td{
  max-height:15px; 
  
}
td
{
 max-width: 100px;
 overflow: hidden;
 text-overflow: ellipsis;
 white-space: nowrap;
}
tr:nth-child(even) {
  background-color: #f2f2f2;
}

.my-style {
    height:90px;
    font-size:30px;
    text-align:center;

}
.notification-title {
    text-align:center;
  }
.notification-text{
  font-size:50px;
    text-align:center;
  }

@import url(https://cdn.syncfusion.com/ej2/material.css);

</style>