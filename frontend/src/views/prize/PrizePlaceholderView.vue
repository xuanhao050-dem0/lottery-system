<template>
  <ModulePlaceholder
    title="奖品管理"
    description="奖品列表、分配能力和图片上传入口已预留，等待后端奖品接口与上传接口接入。"
    action-label="上传奖品"
    :panels="panels"
  />
</template>

<script setup lang="ts">
import ModulePlaceholder from '@/components/ModulePlaceholder.vue'

const panels = [
  { title: '奖品表格', description: '预留奖品名称、库存、图片等字段展示位。' },
  { title: '分配能力', description: '预留奖品分配和绑定活动的操作区。' },
  { title: '上传入口', description: '预留上传图片与导入奖品入口，当前保持禁用。' },
]
</script>
