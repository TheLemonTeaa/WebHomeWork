import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path:'/login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path:'/updatePassword',
    component: () => import('../views/UpdatePasswordView.vue')
  },
  {
    path: '/',
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
        path:'empGraph',
        component: () => import('../views/EmpGraphView.vue')
      },
      {
        path:'stuGraph',
        component: () => import('../views/StuGraphView.vue')
      },
      {
        path:'course',
        component: () => import('../views/CourseView.vue')
      },
      {
        path:'plan',
        component: () => import('../views/PlanView.vue')
      }
    ]
  },
]

const router = new VueRouter({
  routes,
})

export default router
