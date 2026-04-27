<template>
  <aside class="sidebar">
    <div class="sidebar__brand">
      <div class="sidebar__logo">L</div>
      <div class="sidebar__brand-copy">
        <div class="sidebar__eyebrow">ADMIN CONSOLE</div>
        <div class="sidebar__title">抽奖系统后台</div>
        <div class="sidebar__subtitle">Lottery Control Center</div>
      </div>
    </div>

    <el-menu :default-active="route.path" class="sidebar__menu" router>
      <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path">
        <span class="sidebar__menu-label">{{ item.label }}</span>
      </el-menu-item>
    </el-menu>

    <div class="sidebar__footer">
      <span class="sidebar__footer-dot"></span>
      深色品牌主题已启用
    </div>
  </aside>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'

const route = useRoute()

const menuItems = [
  { path: '/dashboard', label: '控制台' },
  { path: '/personnel', label: '人员管理' },
  { path: '/activity', label: '活动管理' },
  { path: '/prize', label: '奖品管理' },
  { path: '/notification', label: '通知管理' },
  { path: '/lottery', label: '抽奖管理' },
]
</script>

<style scoped>
.sidebar {
  display: flex;
  height: 100%;
  flex-direction: column;
  background:
    linear-gradient(180deg, rgba(7, 14, 20, 0.98) 0%, rgba(4, 10, 15, 0.98) 100%),
    radial-gradient(circle at top, rgba(125, 211, 252, 0.1), transparent 36%);
  color: var(--text-primary);
}

.sidebar__brand {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 28px 22px 22px;
  border-bottom: 1px solid rgba(154, 186, 214, 0.08);
}

.sidebar__logo {
  display: grid;
  width: 48px;
  height: 48px;
  place-items: center;
  border-radius: 16px;
  border: 1px solid rgba(196, 241, 255, 0.32);
  background:
    linear-gradient(135deg, rgba(196, 241, 255, 0.22), rgba(73, 141, 180, 0.3)),
    rgba(255, 255, 255, 0.04);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.08);
  font-family: var(--font-display);
  font-size: 22px;
  font-weight: 700;
  color: var(--accent-strong);
}

.sidebar__brand-copy {
  min-width: 0;
}

.sidebar__eyebrow {
  color: var(--text-muted);
  font-size: 11px;
  letter-spacing: 0.24em;
}

.sidebar__title {
  margin-top: 6px;
  font-family: var(--font-display);
  font-size: 18px;
  font-weight: 600;
}

.sidebar__subtitle {
  margin-top: 4px;
  color: var(--text-secondary);
  font-size: 12px;
}

.sidebar__menu {
  flex: 1;
  padding: 18px 14px;
  border-right: none;
  background: transparent;
}

:deep(.el-menu) {
  border-right: none;
  background: transparent;
}

:deep(.el-menu-item) {
  position: relative;
  height: 48px;
  margin-bottom: 6px;
  border-radius: 14px;
  color: var(--text-secondary);
  background: transparent;
  transition: background 0.22s ease, color 0.22s ease, transform 0.22s ease;
}

:deep(.el-menu-item:hover) {
  background: rgba(255, 255, 255, 0.04);
  color: var(--text-primary);
  transform: translateX(2px);
}

:deep(.el-menu-item.is-active) {
  background: linear-gradient(90deg, rgba(125, 211, 252, 0.18), rgba(125, 211, 252, 0.04));
  color: var(--text-primary);
  box-shadow: inset 0 0 0 1px rgba(196, 241, 255, 0.08);
}

:deep(.el-menu-item.is-active)::before {
  position: absolute;
  top: 10px;
  bottom: 10px;
  left: 8px;
  width: 3px;
  border-radius: 999px;
  background: linear-gradient(180deg, #ddf7ff, #7dd3fc);
  content: '';
}

.sidebar__menu-label {
  font-weight: 600;
  letter-spacing: 0.01em;
}

.sidebar__footer {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 8px 18px 18px;
  padding: 14px 16px;
  border: 1px solid rgba(154, 186, 214, 0.1);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.03);
  color: var(--text-muted);
  font-size: 12px;
}

.sidebar__footer-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: linear-gradient(180deg, #d8fbff, #7dd3fc);
  box-shadow: 0 0 16px rgba(125, 211, 252, 0.5);
}

@media (max-width: 860px) {
  .sidebar__brand {
    justify-content: center;
    padding: 22px 12px 18px;
  }

  .sidebar__brand-copy,
  .sidebar__footer {
    display: none;
  }

  .sidebar__menu {
    padding: 18px 10px;
  }

  :deep(.el-menu-item) {
    justify-content: center;
    padding: 0;
  }

  :deep(.el-menu-item.is-active)::before {
    left: auto;
    right: 8px;
  }

  .sidebar__menu-label {
    font-size: 12px;
  }
}
</style>
