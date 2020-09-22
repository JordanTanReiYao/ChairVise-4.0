<template>
  <div>
    <zoom-center-transition :duration="500">      
      <el-row>
        <div v-show="show" v-if="isLogin">
          <el-col :span="6">
            <img alt="Vue logo" class="logo" src="@/assets/chair2.png"/>
          </el-col>
          <!--<el-col :span="15">
            <h2 class='title'> ChairVisE -- The Conference Data Visualisation Management System </h2>
          </el-col>-->
          <el-col :span="10">
          <h3 class="greeting">Good Day {{userNickname}}!</h3>
          <el-card :body-style="{ padding: '0px', height:'90px'  }">
            <div>
              <h2>Author Records</h2>  
              <h1 class="figure">{{authorVersionsSize}}</h1>  
              </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' , height:'90px'}">
            <div>
              <h2>Review Records</h2>  
              <h1 class="figure">{{reviewVersionsSize}}</h1>  
              </div>
        </el-card>
        <el-card :body-style="{ padding: '0px' , height:'90px'}">
            <div>
              <h2>Submission Records</h2>  
              <h1 class="figure">{{submissionVersionsSize}}</h1>  
              </div>
        </el-card>
        <el-card class="conference" :body-style="{ padding: '0px' , height:'100px'}">
            <div>
          <h2 class='conference' v-if="upcomingConferenceExists">Upcoming Conference: {{upcomingConferenceTitle}}</h2>
          <h2 class='conference' v-if="upcomingConferenceExists">Date: {{upcomingConferenceStart}}</h2>
          <h2 class='noConference' v-else>You have no upcoming conferences enjoy!</h2> 
              </div>
        </el-card>
        </el-col>
        <el-col :span="6">
            <img alt="Book" class="side" src="@/assets/book.gif"/>
          </el-col>
        </div>
        <div v-show="show" v-else>
          <el-col>
          <h3 class="logout">Good Day User!</h3>
          <el-col :span="6">
            <img alt="Vue logo" class="notlogin" src="@/assets/chair2.png"/>
          </el-col>
          <el-col :span="10">
            <h3 class="logoutmessage">Please login to use the application</h3>
          </el-col>
        <el-col :span="6">
            <img alt="Book" class="sidenotlogin" src="@/assets/book.gif"/>
          </el-col>
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
      authorVersionsSize() {
        let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => v.version.versionId))).length;
        //let reviewCount =  Array.from(new Set(this.$store.state.record.ReviewRecordList.map(v => v.version.versionId))).length;
        //let submissionCount =  Array.from(new Set(this.$store.state.record.SubmissionRecordList.map(v => v.version.versionId))).length;
        return authorCount//+reviewCount+submissionCount;
      },
      reviewVersionsSize() {
        //let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => v.version.versionId))).length;
        let reviewCount =  Array.from(new Set(this.$store.state.record.ReviewRecordList.map(v => v.version.versionId))).length;
        //let submissionCount =  Array.from(new Set(this.$store.state.record.SubmissionRecordList.map(v => v.version.versionId))).length;
        return reviewCount//+submissionCount;
      },
      submissionVersionsSize() {
        //let authorCount = Array.from(new Set(this.$store.state.record.AuthorRecordList.map(v => v.version.versionId))).length;
        //let reviewCount =  Array.from(new Set(this.$store.state.record.ReviewRecordList.map(v => v.version.versionId))).length;
        let submissionCount =  Array.from(new Set(this.$store.state.record.SubmissionRecordList.map(v => v.version.versionId))).length;
        return submissionCount;
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
    width: 290px;
    height: 330px;
    text-align: center;
    display: block;
    margin: 0 auto;
    margin-top:20px;
  }
  img.side {
    width: 300px;
    height: 350px;
    align-content: center;
    text-align: right;
    display: block;
    margin-bottom: 50px;
    margin-left:100px;
    margin-top:-60px;

   /* margin: 0 auto;*/
  
  }
  img.notlogin {
    width: 250px;
    height: 300px;
    text-align: right;
    display: block;
    margin-bottom: 10px;
    margin-left:60px;
    margin-top:-80px;
  
  }
  img.sidenotlogin {
    width: 280px;
    height: 330px;
    text-align: right;
    display: block;
    margin-left:120px;
    margin-top:-165px;
  
  }
  .el-row {    
    background: #1e9fc4;
    padding: 30px;
  }
  h2.title{
    margin-left:-60px;
    text-align: left;
  }
  h3 {
    text-align:center;
    color:black;
    font-size:29px;
    font-weight:bold;
    /*margin-left: 40px;*/
    /*font-style: oblique;*/
    /*font-family: 'Times New Roman', Times, serif;*/
  }
  h3.greeting {
    text-align:center;
    color:white;
    font-size:50px;
    font-weight:bold;
    margin-left: 75px;
    margin-right:-10px;
    margin-top:-10px;
    /*font-style: oblique;*/
    margin-bottom:18px;
    font-family: 'Times New Roman', Times, serif;
  }
  h3.logout
  {
    text-align:center;
    color:white;
    font-size:35px;
    font-weight:bold;  
  }
  h3.logoutmessage
  {
    text-align:center;
    color:white;
    margin-right: -120px;
    font-size:29px;
    font-weight:bold;  
  }
  .el-card {
    /*margin-top:10px;*/
    margin: 13px;
    background-color: whitesmoke;
    text-align: center;
    margin-left:70px;
    margin-right:-10px;
  }
  .el-card.conference {
    /*margin-top:10px;*/
    margin: 13px;
    background-color: whitesmoke;
    text-align: center;
    margin-left:70px;
    margin-right:-10px;
    /*margin-right: -75px;
    /margin-left: -40px;*/
  }
  h1.figure{
    margin-top: -20px;
    font-size: 42px;
  }
</style>
