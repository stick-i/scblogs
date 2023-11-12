// import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// import axios from "axios";

import mavonEditor from 'mavon-editor'

// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
import 'mavon-editor/dist/css/index.css'

import "./axios"

import "./assets/css/iconfont/iconfont.css"


import '@/assets/svg/iconfont.js'
import '@/assets/svg/icon.css'
import less from 'less'

Vue.use(less)
// Vue.use(ElementUI);

// Vue.use(ElementUI);
Vue.use(mavonEditor);


axios.defaults.withCredentials = true;
Vue.prototype.$axios = axios;


Vue.prototype.bus = new Vue();

Vue.config.productionTip = false

Vue.directive('debounce', {
	inserted(el, binding) {
		let timeout = null
		el.addEventListener('click', () => {
			if (timeout) {
				clearTimeout(timeout)
			}
			timeout = setTimeout(() => {
				console.log("这是防抖实现", binding)
				binding.value.fn()
			}, 1000);
		})
	}
})

new Vue({
	router,
	store,
	render: h => h(App),

}).$mount('#app')

