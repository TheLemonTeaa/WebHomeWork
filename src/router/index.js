import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/',
    name: 'home',
    component: () => import('../views/LayoutView.vue'),
    children:[
      {
        path: '',
        component: () => import('../views/HomeView.vue')
      },
      {
        path: 'student',
        component: () => import('../views/StudentView.vue')
      },
      {
        path: 'class',
        component: () => import('../views/ClassView.vue')
      },
      {
        path: 'staff',
        component: () => import('../views/StaffView.vue')
      },
      {
        path: 'dept',
        component: () => import('../views/DeptView.vue')
      },
      {
        path:'graph',
        component: () => import('../views/GraphView.vue')
      },
    ]
  },
]

const router = new VueRouter({
  routes,
})

export default router
