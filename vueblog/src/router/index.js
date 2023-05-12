import Vue from 'vue'
import VueRouter from 'vue-router'

// 路由懒加载
const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const Register = () => import('@/views/register/Register')
const BlogSearch = () => import('@/views/blogSearch/BlogSearch')
const BlogEdit = () => import('@/views/blogEdit/BlogEdit')
const BlogPublishSuccess = () => import('@/views/blogPublishSuccess/BlogPublishSuccess')
const BlogDetail = () => import('@/views/blogDetail/BlogDetail')
const PersonalInfoEdit = () => import('@/views/PersonalInfoEdit')
const ContentManagement = () => import('@/views/ContentManagement')
const NewPersonBlog = () => import('@/views/NewPersonBlog')
const HeartSay = () => import('@/views/HeartSay')
const Dynamic = () => import('@/views/DynamicEdit')
// const CampusScenery = () => import('@/views/campusScenery/CampusScenery')


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: { name: "Home" }
  },

  {
    path: '/',
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
  {
    path: '/user/:userId',
    name: 'UserHome',
    component: NewPersonBlog,
  },
  // {
  //   path: '/campus/scenery',
  //   name: 'CampusScenery',
  //   component: CampusScenery,
  // },
  {
    path: '/NewPersonBlog',
    name: 'NewPersonBlog',
    component: NewPersonBlog,
  },
  {
    path: '/PersonalInfoEdit',
    name: 'PersonalInfoEdit',
    component: PersonalInfoEdit
  },
  {
    path: '/ContentManagement',
    name: 'ContentManagement',
    component: ContentManagement
  },
  {
    path: '/HeartSay',
    name: 'HeartSay',
    component: HeartSay
  },
  {
    path: '/DynamicEdit',
    name: 'DynamicEdit',
    component: Dynamic
  },
]

const router = new VueRouter({
  mode: 'history',
  // mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// const originalPush = VueRouter.prototype.push;
// VueRouter.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch((err) => err);
// };

export default router
