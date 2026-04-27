<template>
  <section class="dashboard">
    <div class="dashboard__hero page-card">
      <div class="dashboard__hero-copy">
        <p class="dashboard__eyebrow">OPERATIONS OVERVIEW</p>
        <h1 class="page-title">抽奖系统后台控制台</h1>
        <p class="page-subtitle">
          当前后台已完成认证、布局骨架与人员管理真实接入。本轮重点将高频管理流程统一为更沉稳的深色体验，保证控制台、导航与数据页面在同一视觉语言下运行。
        </p>
      </div>

      <div class="dashboard__hero-side">
        <el-tag type="success" effect="plain">已接入真实后端</el-tag>
        <div class="dashboard__hero-panel">
          <span class="dashboard__hero-label">当前状态</span>
          <strong>后台主流程稳定可用</strong>
          <span>认证、跳转、人员数据查询保持现有逻辑不变。</span>
        </div>
      </div>
    </div>

    <div class="dashboard__grid">
      <article v-for="card in cards" :key="card.title" class="dashboard__card page-card">
        <div class="dashboard__card-top">
          <span class="dashboard__card-kicker">{{ card.kicker }}</span>
          <span class="dashboard__card-index">{{ card.index }}</span>
        </div>
        <div class="dashboard__card-title">{{ card.title }}</div>
        <div class="dashboard__card-value">{{ card.value }}</div>
        <div class="dashboard__card-desc">{{ card.description }}</div>
      </article>
    </div>

    <section class="dashboard__section page-card">
      <div class="dashboard__section-header">
        <div>
          <p class="dashboard__eyebrow">DELIVERY STAGES</p>
          <h2>阶段说明</h2>
        </div>
        <div class="dashboard__section-note">优先保证已接入页面的统一观感，再等待后续业务模块扩展。</div>
      </div>

      <el-timeline>
        <el-timeline-item timestamp="阶段 1" placement="top">
          登录、注册、验证码登录、人员列表已按当前后端接口约定设计。
        </el-timeline-item>
        <el-timeline-item timestamp="阶段 2" placement="top">
          活动、奖品、通知、抽奖模块先以占位页承载信息架构，不伪造不存在的 CRUD 能力。
        </el-timeline-item>
        <el-timeline-item timestamp="阶段 3" placement="top">
          等后端接口补齐后，在现有路由和布局骨架上继续补充业务页面。
        </el-timeline-item>
      </el-timeline>
    </section>
  </section>
</template>

<script setup lang="ts">
const cards = [
  {
    index: '01',
    kicker: 'AUTH',
    title: '认证模块',
    value: '已接入',
    description: '支持密码登录、验证码登录、注册与退出登录。',
  },
  {
    index: '02',
    kicker: 'PEOPLE',
    title: '人员模块',
    value: '已接入',
    description: '支持按身份筛选并读取后端人员列表。',
  },
  {
    index: '03',
    kicker: 'PLACEHOLDER',
    title: '业务模块',
    value: '4 个占位页',
    description: '活动、奖品、通知、抽奖页面已建立扩展骨架。',
  },
]
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.dashboard__hero {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(260px, 0.8fr);
  gap: 24px;
  padding: 30px;
}

.dashboard__hero-copy {
  position: relative;
  z-index: 1;
}

.dashboard__eyebrow {
  margin: 0 0 10px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.dashboard__hero-side {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 16px;
}

.dashboard__hero-panel {
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 8px;
  padding: 18px;
  border: 1px solid rgba(154, 186, 214, 0.16);
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.04);
  color: var(--text-secondary);
}

.dashboard__hero-label {
  color: var(--text-muted);
  font-size: 12px;
}

.dashboard__hero-panel strong {
  color: var(--text-primary);
  font-size: 18px;
}

.dashboard__grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}

.dashboard__card {
  padding: 24px;
}

.dashboard__card-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}

.dashboard__card-kicker,
.dashboard__card-index {
  font-size: 12px;
  letter-spacing: 0.18em;
}

.dashboard__card-kicker {
  color: var(--accent-strong);
}

.dashboard__card-index {
  color: var(--text-muted);
}

.dashboard__card-title {
  margin-top: 18px;
  color: var(--text-secondary);
  font-size: 14px;
}

.dashboard__card-value {
  margin-top: 12px;
  font-family: var(--font-display);
  font-size: 32px;
  font-weight: 600;
  color: var(--text-primary);
}

.dashboard__card-desc {
  margin-top: 10px;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
}

.dashboard__section {
  padding: 28px;
}

.dashboard__section-header {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  margin-bottom: 24px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.dashboard__section h2 {
  margin: 0;
  font-family: var(--font-display);
  font-size: 24px;
  font-weight: 600;
}

.dashboard__section-note {
  max-width: 360px;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
  text-align: right;
}

@media (max-width: 900px) {
  .dashboard__hero {
    grid-template-columns: 1fr;
    padding: 24px;
  }

  .dashboard__section {
    padding: 24px;
  }

  .dashboard__section-note {
    max-width: none;
    text-align: left;
  }
}
</style>
