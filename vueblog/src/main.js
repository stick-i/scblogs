import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import axios from "axios";

import mavonEditor from 'mavon-editor'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'mavon-editor/dist/css/index.css'

import "./axios"

import "./assets/css/iconfont/iconfont.css"

import global from './global.js'   //注意文件路径，实际路径以项目目录结构为准
Vue.prototype.$global = global;

Vue.use(ElementUI);
Vue.use(mavonEditor);


axios.defaults.withCredentials = true;
Vue.prototype.$axios = axios;


Vue.prototype.bus = new Vue();

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),


}).$mount('#app')
