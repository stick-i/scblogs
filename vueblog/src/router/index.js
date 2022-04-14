import Vue from 'vue'
import VueRouter from 'vue-router'

import Register from "@/views/Register";
import Login from "@/views/Login";
import Blogs from "@/views/Blogs";
import BlogEdit from "@/views/BlogEdit";
import BlogDetail from "@/views/BlogDetail";
import P_Register from "@/views/P_Register";
import P_Register2 from "@/views/P_Register2";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"P_Register2"}
  },
  {
    path: '/P_Register2',
    name: 'P_Register2',
    component: P_Register2
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/P_Register',
    name: 'P_Register',
    component: P_Register
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },{
    path: '/blog/add',
    name: 'BlogEdit',
    component: BlogEdit
  },
  {
    path: '/blog/add',
    name: 'BlogEdit',
    component: BlogEdit
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
