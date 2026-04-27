<template>
  <ModulePlaceholder
    title="活动管理"
    description="活动列表、状态筛选和新建活动入口已预留，等待后端活动管理接口接入。"
    action-label="新建活动"
    :panels="panels"
  />
</template>

<script setup lang="ts">
import ModulePlaceholder from '@/components/ModulePlaceholder.vue'

const panels = [
  { title: '活动列表', description: '预留活动列表区域，用于展示活动名称、状态和时间。' },
  { title: '状态筛选', description: '预留按活动状态筛选的交互区。' },
  { title: '新建入口', description: '新建活动按钮先保持禁用，避免伪造不存在的接口能力。' },
]
</script>
