import axios from 'axios'
/*import {deepCopy} from "@/common/utility"*/
import createPersistedState from 'vuex-persistedstate'


export default {
  plugins: [createPersistedState()],
  state: {
    AuthorRecordList: [],
    ReviewRecordList: [],
    SubmissionRecordList:[]  ,
    AuthorRecordVersionList:[2,3,4]
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
    setSubmissionRecordList(state,payload){
      state.SubmissionRecordList = payload;
    }
  },
  actions: {
    async getAuthorRecordList({commit}) {
        commit('setPresentationListLoading', true);
        axios.get('/api/record/author')
            .then(response => {
              commit('setAuthorRecordList', response.data)
            })
            .catch(e => {
              commit('setPresentationListApiError', e.toString());
            })
            .finally(() => {
              commit('setPresentationListLoading', false);
            })
      },
      async getAuthorRecordVersionList({commit},version) {
        commit('setPresentationListLoading', true);
        axios.get(`/api/record/author/${version}`)
            .then(response => {
              commit('setAuthorRecordVersionList', response.data)
            })
            .catch(e => {
              commit('setPresentationListApiError', e.toString());
            })
            .finally(() => {
              commit('setPresentationListLoading', false);
            })
      },
      async getReviewRecordList({commit}) {
        commit('setPresentationListLoading', true);
        axios.get('/api/record/review')
            .then(response => {
              commit('setReviewRecordList', response.data)
            })
            .catch(e => {
              commit('setPresentationListApiError', e.toString());
            })
            .finally(() => {
              commit('setPresentationListLoading', false);
            })
      },
      async getSubmissionRecordList({commit}) {
        commit('setPresentationListLoading', true);
        axios.get('/api/record/submission')
            .then(response => {
              commit('setSubmissionRecordList', response.data)
            })
            .catch(e => {
              commit('setPresentationListApiError', e.toString());
            })
            .finally(() => {
              commit('setPresentationListLoading', false);
            })
      }
    }
};