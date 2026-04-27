import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

declare module 'vue-router' {
  interface RouteMeta {
    title?: string
    requiresAuth?: boolean
    publicOnly?: boolean
  }
}

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
    meta: { title: '登录', publicOnly: true },
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/RegisterView.vue'),
    meta: { title: '注册', publicOnly: true },
  },
  {
    path: '/',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/dashboard',
      },
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/DashboardView.vue'),
        meta: { title: '控制台', requiresAuth: true },
      },
      {
        path: 'personnel',
        name: 'personnel',
        component: () => import('@/views/personnel/PersonnelListView.vue'),
        meta: { title: '人员管理', requiresAuth: true },
      },
      {
        path: 'activity',
        name: 'activity',
        component: () => import('@/views/activity/ActivityPlaceholderView.vue'),
        meta: { title: '活动管理', requiresAuth: true },
      },
      {
        path: 'prize',
        name: 'prize',
        component: () => import('@/views/prize/PrizePlaceholderView.vue'),
        meta: { title: '奖品管理', requiresAuth: true },
      },
      {
        path: 'notification',
        name: 'notification',
        component: () => import('@/views/notification/NotificationPlaceholderView.vue'),
        meta: { title: '通知管理', requiresAuth: true },
      },
      {
        path: 'lottery',
        name: 'lottery',
        component: () => import('@/views/lottery/LotteryPlaceholderView.vue'),
        meta: { title: '抽奖管理', requiresAuth: true },
      },
    ],
  },
  {
    path: '/404',
    name: 'not-found',
    component: () => import('@/views/NotFoundView.vue'),
    meta: { title: '页面未找到' },
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to) => {
  const authStore = useAuthStore()
  if (!authStore.isAuthenticated) {
    authStore.restoreSession()
  }

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    return {
      path: '/login',
      query: to.fullPath ? { redirect: to.fullPath } : undefined,
    }
  }

  if (to.meta.publicOnly && authStore.isAuthenticated) {
    const redirect = typeof to.query.redirect === 'string' ? to.query.redirect : '/dashboard'
    return redirect
  }

  return true
})

router.afterEach((to) => {
  const title = to.meta.title ? `${to.meta.title} - 抽奖系统后台` : '抽奖系统后台'
  document.title = title
})

export default router
