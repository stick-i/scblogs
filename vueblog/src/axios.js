import axios from "axios";
import ElementUI from 'element-ui';

// axios.defaults.baseURL = "http://127.0.0.1:80"
axios.defaults.baseURL = "http://api.scblogs.cn"
// 前置拦截
axios.interceptors.request.use(config => {
	return config
})

// 后置拦截
axios.interceptors.response.use(response => {
	let res = response.data;
	if (res.code === 200) {
		return response
	} else if (res.code === 400) {
		ElementUI.Message.error("用户未登录")
		// 清除token
		window.localStorage.removeItem("token")
		// 跳转到登录页面
		window.location.href = "/login"
		return Promise.reject(response.data.message)
	} else {
		ElementUI.Message.error(response.data.message)
		return Promise.reject(response.data.message)
	}
}, error => {
	console.log(error)
	if (error.response.data) {
		error.message = error.response.data.message
	}
	if (error.response.status === 400) {
		ElementUI.Message.error("用户未登录")
	}
	ElementUI.Message.error(error.message)
	return Promise.reject(error)
})
