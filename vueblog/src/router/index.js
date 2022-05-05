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
const PersonalBlog = () => import('@/views/PersonalBlog')
const PersonalMessageEdit = () => import('@/views/PersonalMessageEdit')
const ContentManagement = () => import('@/views/ContentManagement')


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"PersonalBlog"}
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
    meta: {
      title: '登录'
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
    meta: {
      title: '注册'
    }
  },
  {
    path: '/blog/search',
    name: 'BlogSearch',
    component: BlogSearch,
    meta: {
      title: '博客搜索'
    }
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      title: '创作博客'
    }
  },
  {
    path: '/blog/publish',
    name: 'BlogPublishSuccess',
    component: BlogPublishSuccess,
    meta: {
      title: '发布成功'
    }
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
    path: '/Personal_Blog',
    name: 'PersonalBlog',
    component: PersonalBlog,
    meta: {
      title: '个人中心'
    }
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
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

export default router
