import axios from 'axios'
/*import {deepCopy} from "@/common/utility"*/
import createPersistedState from 'vuex-persistedstate'


export default {
  plugins: [createPersistedState()],
  state: {
    AuthorRecordList: [],
    ReviewRecordList: [],
    SubmissionRecordList:[]  ,
    AuthorRecordVersionList:[],
    ReviewRecordVersionList: [],
    SubmissionRecordVersionList:[]
  },
  mutations: {
    
    setAuthorRecordList(state, payload) {
      state.AuthorRecordList = payload;
    },
    setReviewRecordList(state,payload){
      state.ReviewRecordList = payload;
    },
    setAuthorRecordVersionList(state,payload){
      state.AuthorRecordVersionList = payload;
    },
    setReviewRecordVersionList(state,payload){
      state.ReviewRecordVersionList = payload;
    },
    setSubmissionRecordList(state,payload){
      state.SubmissionRecordList = payload;
    },
    setSubmissionRecordVersionList(state,payload){
      state.SubmissionRecordVersionList=payload;
    },
    deleteFromAuthorRecordVersionList(state, payload) {
      const index = state.AuthorRecordVersionList.findIndex(Author => Author.Version.VersionId === payload);
      state.AuthorRecordVersionList.splice(index, 1)
  },
  deleteFromReviewRecordVersionList(state, payload) {
    const index = state.ReviewRecordVersionList.findIndex(Review => Review.Version.VersionId === payload);
    state.ReviewRecordVersionList.splice(index, 1)
  },
  deleteFromSubmissionRecordVersionList(state, payload) {
    const index = state.SubmissionRecordVersionList.findIndex(Submission => Submission.Version.VersionId === payload);
    state.SubmissionRecordVersionList.splice(index, 1)
  }
  },
  actions: {
    async getAuthorRecordList({commit}) {
        axios.get('/api/record/author')
            .then(response => {
              commit('setAuthorRecordList', response.data)
            })
      },
      async getAuthorRecordVersionList({commit},version) {
     
        axios.get(`/api/record/author/${version}`)
            .then(response => {
              commit('setAuthorRecordVersionList', response.data)
            })
      },
      async getReviewRecordList({commit}) {
        commit('setPresentationListLoading', true);
        axios.get('/api/record/review')
            .then(response => {
              commit('setReviewRecordList', response.data)
            })
      },
      async getReviewRecordVersionList({commit},version) {
        axios.get(`/api/record/review/${version}`)
            .then(response => {
              commit('setReviewRecordVersionList', response.data)
            })
            
      },
      async getSubmissionRecordList({commit}) {
       
        axios.get('/api/record/submission')
            .then(response => {
              commit('setSubmissionRecordList', response.data)
            })
            
      },
      async getSubmissionRecordVersionList({commit},version) {
        axios.get(`/api/record/submission/${version}`)
            .then(response => {
              commit('setSubmissionRecordVersionList', response.data)
            })
            
      },
      async deleteAuthorRecord({commit}, payload) {
        await axios.delete(`/api/record/author/${payload}`)
          .then(() => {
          commit('deleteFromAuthorRecordVersionList', payload);
          //commit('resetConferenceForm')
            })
    }, 
    async deleteReviewRecord({commit}, payload) {
      await axios.delete(`/api/record/review/${payload}`)
        .then(() => {
        commit('deleteFromReviewRecordVersionList', payload);
        //commit('resetConferenceForm')
          })
          
    },
    async deleteSubmissionRecord({commit}, payload) {
      await axios.delete(`/api/record/submission/${payload}`)
        .then(() => {
        commit('deleteFromSubmissionRecordVersionList', payload);
        //commit('resetConferenceForm')
          })
    }

    }
};