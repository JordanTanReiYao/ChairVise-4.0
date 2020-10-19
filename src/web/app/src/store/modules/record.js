import axios from 'axios'
/*import {deepCopy} from "@/common/utility"*/
import createPersistedState from 'vuex-persistedstate'


export default {
  plugins: [createPersistedState()],
  state: {
    AuthorRecordList: [],
    ReviewRecordList: [],
    SubmissionRecordList:[]  
  },
  mutations: {
    
    setAuthorRecordList(state,payload){
      state.AuthorRecordList = payload;
    },
    setAuthorRecordVersionList(state,payload){
      state.AuthorRecordVersionList = payload;
    },
    deleteFromAuthorRecordList(state, payload) {
      const index = state.AuthorRecordList
                        .findIndex(authorrecord => authorrecord.version === payload);
      state.AuthorRecordList.splice(index, 1)
    },
    setReviewRecordList(state,payload){
      state.ReviewRecordList = payload;
    },
    setReviewRecordVersionList(state,payload){
      state.ReviewRecordVersionList = payload;
    },
    deleteFromReviewRecordList(state, payload) {
      const index = state.ReviewRecordVersionList
                        .findIndex(reviewrecord => reviewrecord.version === payload);
      state.ReviewRecordVersionList.splice(index, 1)
    },
    setSubmissionRecordList(state,payload){
      state.SubmissionRecordList = payload;
    },
    setSubmissionRecordVersionList(state,payload){
      state.SubmissionRecordVersionList=payload;
    },
    deleteFromSubmissionRecordList(state, payload) {
      const index = state.SubmissionRecordVersionList
                        .findIndex(submissionrecord => submissionrecord.version === payload);
      state.SubmissionRecordVersionList.splice(index, 1)
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
      async deleteAuthorRecordVersionList({commit}, version) {
        commit('setPresentationListLoading', true);
        await axios.delete(`/api/record/author/${version}`) //await axios.delete('/api/record/author/' + version)
        .then(() => {
          commit('deleteFromAuthorRecordList', version);
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
      async deleteReviewRecordVersionList({commit}, version) {
        commit('setPresentationListLoading', true);
        await axios.delete(`/api/record/review/${version}`) //await axios.delete('/api/record/review/' + version)
        .then(() => {
          commit('deleteFromReviewRecordList', version);
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
      },
      async deleteSubmissionRecordVersionList({commit}, version) {
        commit('setPresentationListLoading', true);
        await axios.delete(`/api/record/submission/${version}`) //await axios.delete('/api/record/submission/' + version)
        .then(() => {
          commit('deleteFromSubmissionRecordList', version);
        })
        .catch(e => {
          commit('setPresentationListApiError', e.toString());
        })
        .finally(() => {
          commit('setPresentationListLoading', false);
        })
    }
  }
}