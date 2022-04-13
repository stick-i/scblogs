import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from "@/views/home/Home";
import Login from "@/views/login/Login";

import Register from "@/views/Register";
import Blogs from "@/views/Blogs";
import BlogEdit from "@/views/BlogEdit";
import BlogDetail from "@/views/BlogDetail";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
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
