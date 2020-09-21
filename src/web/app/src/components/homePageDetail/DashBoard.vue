<template>
  <div>
    <zoom-center-transition :duration="500">      
      <el-row>
        <div v-show="show" v-if="isLogin">
            <h3> Files Uploaded: {{versionsSize}}</h3>
            <h3> Upcoming Conference: {{upcomingConferenceTitle}} at {{upcomingConferenceStart}}</h3>
        </div>
      </el-row>
    </zoom-center-transition>
  </div>
</template>

<script>
  import {ZoomCenterTransition} from 'vue2-transitions'
  export default {
    name: 'DashBoard',
    data: () => ({
      show: false,
    }),
    props: {
      msg: String
    },
    mounted() {
      this.dashBoard();
    },
    methods: {
      dashBoard() {
        this.show = true;
      }, 
      updateVersion() {
        var value = this.presentationFormVersion;
        if (value === undefined) {
            value = this.versions[0];
        }
        this.$store.commit('setPresentationFormField', {
            field: 'version',
            value
        });
      },
    },
    computed: {
      isLogin() {
        return this.$store.state.userInfo.isLogin
      },
      versions() {
        let list = Array.from(new Set(this.$store.state.presentation.versionList.map(v => v.versionId)));
        this.setDefaultValueForVersionList(list[0]);
        return list;
      },
      versionsSize() {
        return Array.from(new Set(this.$store.state.presentation.versionList.map(v => v.versionId))).length;
      },
      upcomingConferenceTitle() {
            let list = this.$store.state.conference.conferenceList;
            var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            let newList = [];
            list.forEach((element) => {
                var temp = new Date(element.date);
                var date = (new Date(temp - tzoffset)).toISOString().slice(0, -1);
                newList.push({
                    id: element.id,
                    title: element.name,
                    start: date,
                    allDay: false
                })
            });
            return newList[0].title; 
        },
        upcomingConferenceStart() {
            let list = this.$store.state.conference.conferenceList;
            var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
            let newList = [];
            list.forEach((element) => {
                var temp = new Date(element.date);
                var date = (new Date(temp - tzoffset)).toISOString().slice(0, -1);
                newList.push({
                    id: element.id,
                    title: element.name,
                    start: date,
                    allDay: false
                })
            });
            return newList[0].start; 
        },
    },
    components: {
      ZoomCenterTransition
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  img {
    width: 250px;
    height: 300px;
    text-align: center;
    display: block;
    margin: 0 auto;
  }
  .el-row {    
    background: #1e9fc4;
    padding: 32px;
  }
</style>
