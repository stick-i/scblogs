import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import axios from "axios";

import mavonEditor from 'mavon-editor'

import ElementUI from 'element-ui';
import jquery from 'jquery';
// import $ from 'jquery'

// import 'bootstrap'
// import jquery from 'jquery-3.6.0.js';
import 'element-ui/lib/theme-chalk/index.css';
import 'mavon-editor/dist/css/index.css'
// import 'bootstrap/dist/css/bootstrap.min.css'
// import 'bootstrap/dist/js/bootstrap.min.js'
import "./axios"

import "./assets/css/iconfont/iconfont.css"



Vue.use(ElementUI);
Vue.use(mavonEditor);


axios.defaults.withCredentials = true;
Vue.prototype.$axios = axios;
Vue.prototype.$jquery = jquery;
// Vue.prototype.$ = jquery;
// 兄弟组件通信
Vue.prototype.bus = new Vue()



Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
