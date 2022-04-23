import Vue from 'vue'
import VueRouter from 'vue-router'

// 路由懒加载
const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const Register = () => import('@/views/register/Register')
const Blogs = () => import('@/views/Blogs')
const BlogEdit = () => import('@/views/BlogEdit')
const BlogDetail = () => import('@/views/BlogDetail')
const PersonalBlog = () => import('@/views/PersonalBlog')
const PersonalMessageEdit = () => import('@/views/PersonalMessageEdit')
const A = () => import('@/views/A')


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
  {
    path: '/Personal_Blog',
    name: 'PersonalBlog',
    component: PersonalBlog
  },
  {
    path: '/PersonalMessageEdit',
    name: 'PersonalMessageEdit',
    component: PersonalMessageEdit
  },
  {
    path: '/A',
    name: 'A',
    component: A
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
