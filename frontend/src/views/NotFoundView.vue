<template>
  <div class="not-found-wrap">
    <div class="not-found page-card">
      <p class="not-found__eyebrow">404 / PAGE MISSING</p>
      <h1>404</h1>
      <p>未找到对应页面。</p>
      <RouterLink to="/dashboard">返回控制台</RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<style scoped>
.not-found-wrap {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background:
    radial-gradient(circle at top, rgba(125, 211, 252, 0.14), transparent 28%),
    linear-gradient(180deg, #071019 0%, #04080d 100%);
}

.not-found {
  width: min(520px, 100%);
  padding: 52px 32px;
  text-align: center;
}

.not-found__eyebrow {
  margin: 0 0 14px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.not-found h1 {
  margin: 0;
  font-family: var(--font-display);
  font-size: 72px;
  font-weight: 600;
}

.not-found p {
  margin: 16px 0 24px;
  color: var(--text-secondary);
}
</style>
