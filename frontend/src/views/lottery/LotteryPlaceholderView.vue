<template>
  <ModulePlaceholder
    title="抽奖管理"
    description="抽奖配置、执行操作与结果展示区域已预留，等待后端抽奖动作与结果接口接入。"
    action-label="开始抽奖"
    :panels="panels"
  />
</template>

<script setup lang="ts">
import ModulePlaceholder from '@/components/ModulePlaceholder.vue'

const panels = [
  { title: '抽奖配置', description: '预留活动、奖品、参与范围等配置项。' },
  { title: '执行按钮', description: '开始抽奖按钮先保持禁用，避免前端伪造业务动作。' },
  { title: '结果面板', description: '预留中奖名单和抽奖结果展示区域。' },
]
</script>
