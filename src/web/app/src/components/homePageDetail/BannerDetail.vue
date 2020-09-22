<template>
  <div>
    <zoom-center-transition :duration="500">      
      <el-row>
        <div v-show="show" v-if="isLogin">
          <el-col :span="6">
            <img alt="Vue logo" class="logo" src="@/assets/chair2.png"/>
          </el-col>
          <el-col :span="15">
            <h2> ChairVisE -- The Conference Data Visualisation Management System </h2>
          </el-col>
          <el-col :span="10">
          <h3> Good day {{userNickname}}!</h3>
          <h3> Files Uploaded: {{versionsSize}}</h3>
          <h3 v-if="upcomingConferenceExists">Upcoming Conference: {{upcomingConferenceTitle}} at {{upcomingConferenceStart}}</h3>
          <h3 v-else>You have no upcoming conferences enjoy!</h3>
        </el-col>
        <el-col :span="6">
            <img alt="Book" class="side" src="@/assets/book.gif"/>
          </el-col>
        </div>
        <div v-show="show" v-else>
          <el-col :span="6">
            <img alt="Vue logo" class="logo" src="@/assets/chair2.png"/>
          </el-col>
          <el-col :span="15">
            <h2> ChairVisE -- The Conference Data Visualisation Management System </h2>
          </el-col>
          <el-col :span="10">
            <h3 class="notlogin">Please login to use the application</h3>
          </el-col>
        <el-col :span="6">
            <img alt="Book" class="notlogin" src="@/assets/book.gif"/>
          </el-col>
        </div>
      </el-row>
    </zoom-center-transition>
  </div>
</template>

<script>
  import {ZoomCenterTransition} from 'vue2-transitions'
  export default {
    name: 'BannerDetail',
    data: () => ({
      show: false,
    }),
    props: {
      msg: String
    },
    mounted() {
      this.loadBanner();
    },
    methods: {
      loadBanner() {
        this.show = true;
      }
    },
    computed: {
      userNickname() {
            return this.$store.state.userInfo.userNickname
        },
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      versions() {
        let list = Array.from(new Set(this.$store.state.presentation.versionList.map(v => v.versionId)));
        this.setDefaultValueForVersionList(list[0]);
        return (new Set(this.$store.state.presentation.versionList.map(v => v.versionId)));
        return list;
      },
      versionsSize() {
        let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => v.version.versionId))).length;
        let reviewCount =  Array.from(new Set(this.$store.state.record.ReviewRecordList.map(v => v.version.versionId))).length;
        let submissionCount =  Array.from(new Set(this.$store.state.record.SubmissionRecordList.map(v => v.version.versionId))).length;
        return authorCount+reviewCount+submissionCount;
      },
      upcomingConferenceTitle() {
            
            let list = this.$store.state.conference.conferenceList;
            var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            let newList = [];
            let now = new Date();
            var MAX_TIMESTAMP = 8640000000000000;
            let closest=Infinity;
            let closestEvent=Object;
            list.forEach(function(element) {
            let date = new Date(element.date);
            
            if (date > now && (date <= new Date(closest) || date <= closest)) {
                  closestEvent = element;
                  closest=new Date(date);
                  
            }});
            return closestEvent.name; 
            
        },
        upcomingConferenceStart() {
            let list = this.$store.state.conference.conferenceList;
            var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            
            let now = new Date();
            var MAX_TIMESTAMP = 8640000000000000;
            let closest=Infinity;
            let closestEvent=Object;
            list.forEach(function(element) {
            let date = new Date(element.date);
            
            if (date > now && (date <= new Date(closest) || date <= closest)) {
                  closestEvent = element;
                  closest=new Date(date);
                  
            }});

            let nearestDate=new Date(closest);
            nearestDate = (new Date(nearestDate - tzoffset)).toISOString().slice(0, -1);
            return nearestDate.slice(0,16).replace("T"," ");
             
        },
        upcomingConferenceExists() {
            let list = this.$store.state.conference.conferenceList;
            var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            
            let now = new Date();
            var MAX_TIMESTAMP = 8640000000000000;
            let closest=Infinity;
            let closestEvent=Object;
            let counter=0;
            list.forEach(function(element) {
            let date = new Date(element.date);
            
            if (date > now && (date <= new Date(closest) || date <= closest)) {
                  closestEvent = element;
                  closest=new Date(date);
                  counter=counter+1;
            }});

            if (counter==0){
              return false;
            }
            else{
              return true;
            }
            
             
        }
    },
    components: {
      ZoomCenterTransition
    },
    mounted() { 
        Promise.all([
        this.$store.dispatch('getConferenceList'),
        this.$store.dispatch('getAuthorRecordList'),
        this.$store.dispatch('getReviewRecordList'),
        this.$store.dispatch('getSubmissionRecordList'),
        ]).finally(() => {
        // using "finally" so even if there are errors, it stops "loading"
        this.loading = false})
        this.loadBanner();
        }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  img.logo {
    width: 250px;
    height: 300px;
    text-align: center;
    display: block;
    margin: 0 auto;
  }
  img.side {
    width: 250px;
    height: 300px;
    text-align: right;
    display: block;
    margin-bottom: 50px;
    margin-left:100px;
    margin-top:-80px;

   /* margin: 0 auto;*/
  
  }
  img.notlogin {
    width: 250px;
    height: 300px;
    text-align: right;
    display: block;
    margin-bottom: 50px;
    margin-left:180px;
    margin-top:-100px;
  
  }
  .el-row {    
    background: #1e9fc4;
    padding: 30px;
  }
  h2{
    margin-left:-60px;
    text-align: left;
  }
  h3 {
    text-align:center;
    color:white;
    font-size:29px;
    font-weight:bold;
    margin-left: 40px;
    /*font-style: oblique;*/
    font-family: 'Times New Roman', Times, serif;
  }
  h3.notlogin 
  {
    text-align:center;
    color:white;
    font-size:29px;
    font-weight:bold;
    margin-left: 110px;
    margin-top: 70px;
    /*font-style: oblique;*/
    font-family: 'Times New Roman', Times, serif;
  }
</style>
