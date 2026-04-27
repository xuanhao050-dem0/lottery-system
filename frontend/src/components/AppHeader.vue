<template>
  <header class="app-header page-card">
    <div class="app-header__main">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
          {{ item.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>
      <div class="app-header__title-row">
        <div>
          <p class="app-header__eyebrow">后台主流程</p>
          <h1 class="app-header__title">{{ currentTitle }}</h1>
        </div>
        <p class="app-header__summary">统一深色视觉已覆盖导航、头部与业务主界面。</p>
      </div>
    </div>

    <div class="app-header__actions">
      <div v-if="authStore.identity" class="app-header__identity">
        <span class="app-header__identity-label">当前身份</span>
        <el-tag type="primary" effect="plain">
          {{ formatIdentity(authStore.identity) }}
        </el-tag>
      </div>
      <el-button type="danger" plain @click="handleLogout">退出登录</el-button>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { formatIdentity } from '@/types/api'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const breadcrumbs = computed(() =>
  route.matched
    .filter((record) => record.meta.title)
    .map((record) => ({
      path: record.path,
      title: record.meta.title as string,
    })),
)

const currentTitle = computed(() => {
  const last = breadcrumbs.value[breadcrumbs.value.length - 1]
  return last?.title || '抽奖系统后台'
})

function handleLogout() {
  authStore.logout()
  router.replace('/login')
}
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;
  padding: 18px 22px;
}

.app-header__main {
  min-width: 0;
  flex: 1;
}

.app-header__title-row {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  margin-top: 14px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.app-header__eyebrow {
  margin: 0 0 8px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.app-header__title {
  margin: 0;
  font-family: var(--font-display);
  font-size: clamp(1.6rem, 1.2rem + 0.65vw, 2.1rem);
  font-weight: 600;
  color: var(--text-primary);
}

.app-header__summary {
  margin: 0;
  max-width: 360px;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
  text-align: right;
}

.app-header__actions {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 12px;
  flex-wrap: wrap;
}

.app-header__identity {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.03);
}

.app-header__identity-label {
  color: var(--text-muted);
  font-size: 12px;
}

@media (max-width: 960px) {
  .app-header {
    align-items: stretch;
    flex-direction: column;
  }

  .app-header__summary {
    max-width: none;
    text-align: left;
  }

  .app-header__actions {
    justify-content: flex-start;
  }
}
</style>
