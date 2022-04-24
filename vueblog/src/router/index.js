import Vue from 'vue'
import VueRouter from 'vue-router'

// 路由懒加载
const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const Register = () => import('@/views/register/Register')
const Blogs = () => import('@/views/Blogs')
const BlogEdit = () => import('@/views/BlogEdit')
const BlogDetail = () => import('@/views/BlogDetail')
const Personal_Blog = () => import('@/views/Personal_Blog')



Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"Home"}
  },

  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // {
  //   path: '/P_Register',
  //   name: 'P_Register',
  //   component: P_Register
  // },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
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
  {
    path: '/Personal_Blog',
    name: 'PersonalBlog',
    component: Personal_Blog
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
