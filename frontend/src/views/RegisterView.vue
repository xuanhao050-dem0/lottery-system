<template>
  <div class="register-page">
    <section class="register-card page-card">
      <div class="register-card__header">
        <div>
          <p class="register-card__eyebrow">ACCOUNT CREATION</p>
          <h1>注册账号</h1>
          <p>当前注册接口支持创建管理员或普通用户账号。</p>
        </div>
        <RouterLink to="/login">返回登录</RouterLink>
      </div>

      <el-alert
        title="后端当前注册接口也承担新增用户能力，后续如补充专用人员创建接口，可在人员管理中扩展弹窗。"
        type="info"
        :closable="false"
        show-icon
      />

      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @submit.prevent>
        <el-row :gutter="16">
          <el-col :xs="24" :md="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-form-item label="手机号" prop="phoneNumber">
              <el-input v-model="form.phoneNumber" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :md="12">
            <el-form-item label="身份" prop="identity">
              <el-select v-model="form.identity" placeholder="请选择身份" class="full-width">
                <el-option
                  v-for="option in IDENTITY_OPTIONS"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24">
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="form.password"
                type="password"
                show-password
                placeholder="管理员必须填写 6 位以上密码；普通用户按后端当前约定可留空"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <div class="register-card__actions">
          <el-button @click="router.push('/login')">取消</el-button>
          <el-button :loading="submitting" type="primary" @click="submitForm">提交注册</el-button>
        </div>
      </el-form>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import type { FormInstance, FormItemRule, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { RouterLink, useRouter } from 'vue-router'
import { registerUser } from '@/api/auth'
import { IDENTITY_OPTIONS } from '@/types/api'

const router = useRouter()
const formRef = ref<FormInstance>()
const submitting = ref(false)

const form = ref({
  name: '',
  email: '',
  phoneNumber: '',
  password: '',
  identity: 'ADMIN' as 'ADMIN' | 'NORMAl',
})

const passwordValidator: NonNullable<FormItemRule['validator']> = (_rule, value, callback) => {
  if (form.value.identity === 'ADMIN' && !value) {
    callback(new Error('管理员必须填写密码'))
    return
  }

  if (typeof value === 'string' && value && value.length < 6) {
    callback(new Error('密码长度不能少于 6 位'))
    return
  }

  callback()
}

const rules = computed<FormRules>(() => ({
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入合法邮箱', trigger: ['blur', 'change'] },
  ],
  phoneNumber: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '请输入 11 位手机号', trigger: ['blur', 'change'] },
  ],
  identity: [{ required: true, message: '请选择身份', trigger: 'change' }],
  password: [{ validator: passwordValidator, trigger: ['blur', 'change'] }],
}))

async function submitForm() {
  if (!formRef.value) {
    return
  }

  try {
    submitting.value = true
    await formRef.value.validate()
    const result = await registerUser(form.value)
    ElMessage.success(`注册成功，用户 ID：${result.userId}`)
    await router.push('/login')
  } catch (error) {
    const message = error instanceof Error ? error.message : '注册失败，请稍后重试'
    ElMessage.error(message)
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 32px 16px;
  background:
    radial-gradient(circle at top right, rgba(125, 211, 252, 0.16), transparent 26%),
    linear-gradient(180deg, #071019 0%, #050b11 56%, #03070b 100%);
}

.register-card {
  width: min(920px, 100%);
  padding: 34px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.register-card__header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  align-items: flex-start;
  flex-wrap: wrap;
}

.register-card__eyebrow {
  margin: 0 0 10px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.register-card__header h1 {
  margin: 0 0 8px;
  font-family: var(--font-display);
  font-size: 32px;
  font-weight: 600;
}

.register-card__header p {
  margin: 0;
  color: var(--text-secondary);
}

.register-card__actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.full-width {
  width: 100%;
}

@media (max-width: 720px) {
  .register-card {
    padding: 24px;
  }

  .register-card__actions {
    flex-direction: column;
  }
}
</style>
