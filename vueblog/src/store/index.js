import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    searchKey:'1',
    one1:"min",
    seven1:"min",
    month1:"min1",
    one2:"min",
    seven2:"min",
    month2:"min"
  },
  getters: {
  },
  mutations: {
    copySearchKey(state,value){
      state.searchKey = value
    },
    changeOne1(state,value){
      state.one1=value;
    },
    changeSeven1(state,value){
      state.seven1=value;
    },
    changeMonth1(state,value){
      state.month1=value;
    },
    changeOne2(state,value){
      state.one2=value;
    },
    changeSeven2(state,value){
      state.seven2=value;
    },
    changeMonth2(state,value){
      state.month2=value
    }
  },
  actions: {
  },
  modules: {
  }
})
