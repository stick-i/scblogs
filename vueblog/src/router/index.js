import Vue from 'vue'
import VueRouter from 'vue-router'
import CampusScenery from "@/views/campusScenery/CampusScenery";

// 路由懒加载
const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const Register = () => import('@/views/register/Register')
const BlogSearch = () => import('@/views/blogSearch/BlogSearch')
const BlogEdit = () => import('@/views/blogEdit/BlogEdit')
const BlogPublishSuccess = () => import('@/views/blogPublishSuccess/BlogPublishSuccess')
const BlogDetail = () => import('@/views/blogDetail/BlogDetail')
const PersonalBlog = () => import('@/views/PersonalBlog')
const PersonalMessageEdit = () => import('@/views/PersonalMessageEdit')
const ContentManagement = () => import('@/views/ContentManagement')
const NewPersonalBlog = () => import('@/views/NewPersonBlog')
// const CampusScenery = () => import('@/views/campusScenery/CampusScenery')


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
    component: Home,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/blog/search',
    name: 'BlogSearch',
    component: BlogSearch,
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
  },
  {
    path: '/blog/publish',
    name: 'BlogPublishSuccess',
    component: BlogPublishSuccess,
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail,
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
  },
  // {
  //   path: '/campus/scenery',
  //   name: 'CampusScenery',
  //   component: CampusScenery,
  // },
  {
    path: '/Personal_Blog',
    name: 'PersonalBlog',
    component: PersonalBlog,
  },
  {
    path: '/NewPersonalBlog',
    name: 'NewPersonalBlog',
    component: NewPersonalBlog,
  },
  {
    path: '/PersonalMessageEdit',
    name: 'PersonalMessageEdit',
    component: PersonalMessageEdit
  },
  {
    path: '/ContentManagement',
    name: 'ContentManagement',
    component: ContentManagement
  },
]

const router = new VueRouter({
  mode: 'history',
  // mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

export default router
