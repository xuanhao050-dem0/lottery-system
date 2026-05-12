<template>
  <section class="page-card prize-page">
    <div class="section-toolbar prize-page__toolbar">
      <div>
        <p class="prize-page__eyebrow">PRIZE MANAGEMENT</p>
        <h1 class="page-title">奖品管理</h1>
        <p class="page-subtitle">查看和管理所有奖品信息，支持分页浏览。</p>
      </div>

      <div class="prize-page__filters-wrap">
        <div class="prize-page__filters-label">操作</div>
        <div class="prize-page__filters">
          <el-select v-model="pageSize" placeholder="每页条数" @change="handlePageSizeChange">
            <el-option :value="5" label="5 条/页" />
            <el-option :value="10" label="10 条/页" />
            <el-option :value="20" label="20 条/页" />
            <el-option :value="50" label="50 条/页" />
          </el-select>
          <el-button :loading="loading" type="primary" plain @click="fetchPrizes">刷新</el-button>
        </div>
      </div>
    </div>

    <el-alert
      v-if="errorMessage"
      :title="errorMessage"
      type="error"
      :closable="false"
      show-icon
      class="prize-page__alert"
    />

    <div class="prize-page__table-shell">
      <div class="prize-page__table-head">
        <div>
          <h2>奖品列表</h2>
          <p>展示奖品名称、描述、价格和图片信息。</p>
        </div>
        <div class="prize-page__meta">
          <span class="prize-page__meta-label">总记录</span>
          <strong>{{ total }}</strong>
        </div>
      </div>

      <el-table v-loading="loading" :data="prizes" border>
        <el-table-column prop="id" label="ID" min-width="80" />
        <el-table-column label="图片" min-width="100">
          <template #default="scope">
            <el-image
              v-if="scope.row.imageUrl"
              :src="scope.row.imageUrl"
              :preview-src-list="[scope.row.imageUrl]"
              fit="cover"
              class="prize-page__img"
            />
            <span v-else class="prize-page__no-img">无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="prizeName" label="奖品名称" min-width="160" />
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column label="价格" min-width="120">
          <template #default="scope">
            <span class="prize-page__price">¥{{ scope.row.price }}</span>
          </template>
        </el-table-column>
      </el-table>

      <el-empty
        v-if="!loading && !errorMessage && prizes.length === 0"
        description="暂无奖品数据"
        class="empty-state"
      />

      <!-- 分页 -->
      <div v-if="total > 0" class="prize-page__pagination">
        <div class="prize-page__pagination-info">
          第 {{ currentPage }} / {{ totalPages }} 页，共 {{ total }} 条
        </div>
        <div class="prize-page__pagination-btns">
          <el-button :disabled="currentPage <= 1" @click="goPage(1)">首页</el-button>
          <el-button :disabled="currentPage <= 1" @click="goPage(currentPage - 1)">上一页</el-button>
          <el-button :disabled="currentPage >= totalPages" @click="goPage(currentPage + 1)">下一页</el-button>
          <el-button :disabled="currentPage >= totalPages" @click="goPage(totalPages)">尾页</el-button>
          <div class="prize-page__jump">
            <span>跳至</span>
            <el-input
              v-model="jumpPageInput"
              class="prize-page__jump-input"
              @keyup.enter="handleJump"
            />
            <span>页</span>
            <el-button type="primary" @click="handleJump">GO</el-button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { getPrizeList } from '@/api/prize'
import type { PrizeInfo } from '@/types/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const prizes = ref<PrizeInfo[]>([])
const errorMessage = ref('')
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const jumpPageInput = ref('')

const totalPages = computed(() => Math.max(1, Math.ceil(total.value / pageSize.value)))

async function fetchPrizes() {
  try {
    loading.value = true
    errorMessage.value = ''
    const result = await getPrizeList(currentPage.value, pageSize.value)
    prizes.value = result.records
    total.value = result.total
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : '获取奖品列表失败'
    prizes.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

function goPage(page: number) {
  const target = Math.max(1, Math.min(page, totalPages.value))
  if (target === currentPage.value) return
  currentPage.value = target
  fetchPrizes()
}

function handlePageSizeChange() {
  currentPage.value = 1
  fetchPrizes()
}

function handleJump() {
  const page = parseInt(jumpPageInput.value, 10)
  if (isNaN(page) || page < 1 || page > totalPages.value) {
    ElMessage.warning(`请输入 1 ~ ${totalPages.value} 之间的页码`)
    return
  }
  jumpPageInput.value = ''
  goPage(page)
}

onMounted(fetchPrizes)
</script>

<style scoped>
.prize-page {
  padding: 28px;
}

.prize-page__toolbar {
  margin-bottom: 24px;
}

.prize-page__eyebrow {
  margin: 0 0 10px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.prize-page__filters-wrap {
  min-width: min(100%, 320px);
  padding: 16px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.03);
}

.prize-page__filters-label {
  margin-bottom: 12px;
  color: var(--text-muted);
  font-size: 12px;
}

.prize-page__filters {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.prize-page__alert {
  margin-bottom: 18px;
}

.prize-page__table-shell {
  padding: 20px;
  border: 1px solid rgba(154, 186, 214, 0.12);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.03);
}

.prize-page__table-head {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 18px;
  margin-bottom: 18px;
  flex-wrap: wrap;
}

.prize-page__table-head h2 {
  margin: 0 0 8px;
  font-family: var(--font-display);
  font-size: 22px;
  font-weight: 600;
  color: var(--text-primary);
}

.prize-page__table-head p {
  margin: 0;
  color: var(--text-secondary);
  font-size: 14px;
  line-height: 1.7;
}

.prize-page__meta {
  display: flex;
  align-items: baseline;
  gap: 10px;
  padding: 10px 14px;
  border-radius: 18px;
  background: rgba(125, 211, 252, 0.08);
}

.prize-page__meta-label {
  color: var(--text-muted);
  font-size: 12px;
}

.prize-page__meta strong {
  font-family: var(--font-display);
  font-size: 28px;
  font-weight: 600;
  color: var(--text-primary);
}

.prize-page__img {
  width: 60px;
  height: 60px;
  border-radius: 8px;
}

.prize-page__no-img {
  color: var(--text-muted);
  font-size: 12px;
}

.prize-page__price {
  color: #e6a23c;
  font-weight: 600;
}

.prize-page__pagination {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 16px;
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid rgba(154, 186, 214, 0.12);
}

.prize-page__pagination-info {
  color: var(--text-secondary);
  font-size: 14px;
}

.prize-page__pagination-btns {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.prize-page__jump {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-left: 8px;
  color: var(--text-secondary);
  font-size: 14px;
}

.prize-page__jump-input {
  width: 60px;
}

@media (max-width: 900px) {
  .prize-page {
    padding: 24px;
  }

  .prize-page__filters-wrap,
  .prize-page__table-shell {
    width: 100%;
  }

  .prize-page__pagination {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
