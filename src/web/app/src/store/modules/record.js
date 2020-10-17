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
      async getReviewRecordVersionList({commit},version) {
        commit('setPresentationListLoading', true);
        axios.get(`/api/record/review/${version}`)
            .then(response => {
              commit('setReviewRecordVersionList', response.data)
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
      },
      async getSubmissionRecordVersionList({commit},version) {
        commit('setPresentationListLoading', true);
        axios.get(`/api/record/submission/${version}`)
            .then(response => {
              commit('setSubmissionRecordVersionList', response.data)
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