<template>
  <ModulePlaceholder
    title="通知管理"
    description="短信、邮件通知相关接口待接入，当前保留模板列表、发送记录与测试入口骨架。"
    action-label="发送测试"
    :panels="panels"
  />
</template>

<script setup lang="ts">
import ModulePlaceholder from '@/components/ModulePlaceholder.vue'

const panels = [
  { title: '模板列表', description: '预留消息模板展示区域。' },
  { title: '发送记录', description: '预留通知历史记录与状态信息。' },
  { title: '测试发送', description: '预留手动测试通知的入口，等待后端服务接入。' },
]
</script>
