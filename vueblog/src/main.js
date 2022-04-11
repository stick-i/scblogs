import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import axios from "axios";

import mavonEditor from 'mavon-editor'

import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';
import 'mavon-editor/dist/css/index.css'



Vue.use(ElementUI);
Vue.use(mavonEditor);


Vue.prototype.$axios = axios;


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
