<template>
  <div class="admin-shell">
    <el-container class="admin-layout">
      <el-aside width="var(--aside-width)" class="admin-layout__aside">
        <AppSidebar />
      </el-aside>

      <el-container class="admin-layout__content-shell">
        <el-header class="admin-layout__header">
          <AppHeader />
        </el-header>
        <el-main class="admin-layout__main">
          <div class="admin-layout__content-frame">
            <RouterView />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppHeader from '@/components/AppHeader.vue'
import AppSidebar from '@/components/AppSidebar.vue'
</script>

<style scoped>
.admin-shell {
  position: relative;
  min-height: 100vh;
  padding: 18px;
}

.admin-shell::before,
.admin-shell::after {
  position: fixed;
  inset: auto;
  pointer-events: none;
  content: '';
}

.admin-shell::before {
  top: 32px;
  right: 10%;
  width: 280px;
  height: 280px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(125, 211, 252, 0.16), transparent 72%);
  filter: blur(12px);
}

.admin-shell::after {
  left: -80px;
  bottom: 18%;
  width: 240px;
  height: 240px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(54, 110, 140, 0.22), transparent 70%);
  filter: blur(16px);
}

.admin-layout {
  position: relative;
  z-index: 1;
  min-height: calc(100vh - 36px);
  border: 1px solid rgba(154, 186, 214, 0.14);
  border-radius: 32px;
  background: linear-gradient(180deg, rgba(6, 14, 21, 0.96) 0%, rgba(4, 9, 14, 0.98) 100%);
  box-shadow: 0 32px 90px rgba(0, 0, 0, 0.36);
  overflow: hidden;
}

.admin-layout__aside {
  border-right: 1px solid rgba(154, 186, 214, 0.1);
  background: linear-gradient(180deg, rgba(7, 15, 22, 0.98) 0%, rgba(5, 11, 17, 0.98) 100%);
}

.admin-layout__content-shell {
  min-width: 0;
  background:
    linear-gradient(180deg, rgba(8, 18, 27, 0.78) 0%, rgba(4, 8, 13, 0.92) 100%),
    radial-gradient(circle at top, rgba(125, 211, 252, 0.08), transparent 30%);
}

.admin-layout__header {
  height: auto;
  padding: 24px 24px 0;
}

.admin-layout__main {
  padding: 22px 24px 24px;
}

.admin-layout__content-frame {
  min-height: 100%;
}

@media (max-width: 1080px) {
  .admin-shell {
    padding: 12px;
  }

  .admin-layout {
    min-height: calc(100vh - 24px);
    border-radius: 24px;
  }

  .admin-layout__header {
    padding: 18px 18px 0;
  }

  .admin-layout__main {
    padding: 18px;
  }
}

@media (max-width: 860px) {
  .admin-shell {
    padding: 0;
  }

  .admin-layout {
    min-height: 100vh;
    border: none;
    border-radius: 0;
  }

  .admin-layout__aside {
    width: 86px !important;
  }
}
</style>
