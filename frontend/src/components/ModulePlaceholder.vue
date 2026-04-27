<template>
  <section class="module-placeholder page-card">
    <div class="module-placeholder__header">
      <div>
        <p class="module-placeholder__eyebrow">MODULE PLACEHOLDER</p>
        <h2 class="page-title">{{ title }}</h2>
        <p class="page-subtitle">{{ description }}</p>
      </div>
      <el-button :disabled="true" type="primary">{{ actionLabel }}</el-button>
    </div>

    <el-alert title="后端接口待接入" type="info" :closable="false" show-icon />

    <div class="module-placeholder__grid">
      <article v-for="panel in panels" :key="panel.title" class="module-placeholder__panel">
        <div class="module-placeholder__panel-kicker">待扩展能力</div>
        <h3>{{ panel.title }}</h3>
        <p>{{ panel.description }}</p>
      </article>
    </div>
  </section>
</template>

<script setup lang="ts">
interface PlaceholderPanel {
  title: string
  description: string
}

defineProps<{
  title: string
  description: string
  actionLabel: string
  panels: PlaceholderPanel[]
}>()
</script>

<style scoped>
.module-placeholder {
  display: flex;
  flex-direction: column;
  gap: 22px;
  padding: 28px;
}

.module-placeholder__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  flex-wrap: wrap;
}

.module-placeholder__eyebrow {
  margin: 0 0 10px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.module-placeholder__grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}

.module-placeholder__panel {
  padding: 22px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.03);
}

.module-placeholder__panel-kicker {
  margin-bottom: 14px;
  color: var(--text-muted);
  font-size: 11px;
  letter-spacing: 0.2em;
}

.module-placeholder__panel h3 {
  margin: 0 0 10px;
  font-family: var(--font-display);
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
}

.module-placeholder__panel p {
  margin: 0;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
}
</style>
