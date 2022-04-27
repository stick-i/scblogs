import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    searchKey:'1'
  },
  getters: {
  },
  mutations: {
    copySearchKey(state,value){
      state.searchKey = value
    }
  },
  actions: {
  },
  modules: {
  }
})
