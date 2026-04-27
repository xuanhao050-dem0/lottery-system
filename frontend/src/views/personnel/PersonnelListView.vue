<template>
  <section class="page-card personnel-page">
    <div class="section-toolbar personnel-page__toolbar">
      <div>
        <p class="personnel-page__eyebrow">PERSONNEL DIRECTORY</p>
        <h1 class="page-title">人员管理</h1>
        <p class="page-subtitle">当前仅展示后端已提供的人员列表字段：id、userName、identity。</p>
      </div>

      <div class="personnel-page__filters-wrap">
        <div class="personnel-page__filters-label">筛选与同步</div>
        <div class="personnel-page__filters">
          <el-select v-model="selectedIdentity" clearable placeholder="按身份筛选" @change="fetchUsers">
            <el-option label="全部" value="" />
            <el-option
              v-for="option in IDENTITY_OPTIONS"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
          <el-button :loading="loading" type="primary" plain @click="fetchUsers">刷新</el-button>
        </div>
      </div>
    </div>

    <el-alert
      v-if="errorMessage"
      :title="errorMessage"
      type="error"
      :closable="false"
      show-icon
      class="personnel-page__alert"
    />

    <div class="personnel-page__table-shell">
      <div class="personnel-page__table-head">
        <div>
          <h2>人员列表</h2>
          <p>保持现有数据获取逻辑不变，重点提升筛选区、表格容器与空状态层级。</p>
        </div>
        <div class="personnel-page__meta">
          <span class="personnel-page__meta-label">当前结果</span>
          <strong>{{ users.length }}</strong>
        </div>
      </div>

      <el-table v-loading="loading" :data="users" border>
        <el-table-column prop="id" label="ID" min-width="120" />
        <el-table-column prop="userName" label="用户名" min-width="180" />
        <el-table-column label="身份" min-width="160">
          <template #default="scope">
            {{ formatIdentity(scope.row.identity) }}
          </template>
        </el-table-column>
      </el-table>

      <el-empty
        v-if="!loading && !errorMessage && users.length === 0"
        description="暂无符合条件的人员数据"
        class="empty-state"
      />
    </div>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { listUsers } from '@/api/user'
import { formatIdentity, IDENTITY_OPTIONS, type GetUserInfoResult, type UserIdentity } from '@/types/api'

const loading = ref(false)
const users = ref<GetUserInfoResult[]>([])
const errorMessage = ref('')
const selectedIdentity = ref<UserIdentity | ''>('')

async function fetchUsers() {
  try {
    loading.value = true
    errorMessage.value = ''
    users.value = await listUsers(selectedIdentity.value)
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : '获取人员列表失败'
    users.value = []
  } finally {
    loading.value = false
  }
}

onMounted(fetchUsers)
</script>

<style scoped>
.personnel-page {
  padding: 28px;
}

.personnel-page__toolbar {
  margin-bottom: 24px;
}

.personnel-page__eyebrow {
  margin: 0 0 10px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.personnel-page__filters-wrap {
  min-width: min(100%, 320px);
  padding: 16px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.03);
}

.personnel-page__filters-label {
  margin-bottom: 12px;
  color: var(--text-muted);
  font-size: 12px;
}

.personnel-page__filters {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.personnel-page__alert {
  margin-bottom: 18px;
}

.personnel-page__table-shell {
  padding: 20px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.03);
}

.personnel-page__table-head {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 18px;
  margin-bottom: 18px;
  flex-wrap: wrap;
}

.personnel-page__table-head h2 {
  margin: 0 0 8px;
  font-family: var(--font-display);
  font-size: 22px;
  font-weight: 600;
  color: var(--text-primary);
}

.personnel-page__table-head p {
  margin: 0;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
}

.personnel-page__meta {
  display: flex;
  align-items: baseline;
  gap: 10px;
  padding: 10px 14px;
  border-radius: 18px;
  background: rgba(125, 211, 252, 0.08);
}

.personnel-page__meta-label {
  color: var(--text-muted);
  font-size: 12px;
}

.personnel-page__meta strong {
  font-family: var(--font-display);
  font-size: 28px;
  font-weight: 600;
  color: var(--text-primary);
}

@media (max-width: 900px) {
  .personnel-page {
    padding: 24px;
  }

  .personnel-page__filters-wrap,
  .personnel-page__table-shell {
    width: 100%;
  }
}
</style>
