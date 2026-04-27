<template>
  <div class="auth-page">
    <section class="auth-page__hero">
      <div class="auth-page__orb auth-page__orb--large"></div>
      <div class="auth-page__orb auth-page__orb--small"></div>
      <div class="auth-page__copy">
        <el-tag type="primary" effect="plain">管理员后台</el-tag>
        <p class="auth-page__eyebrow">LOTTERY OPERATIONS</p>
        <h1>登录抽奖系统后台</h1>
        <p>
          当前已真实接入密码登录、验证码登录与人员列表能力；活动、奖品、通知、抽奖模块已预留结构，后续可继续扩展。
        </p>
      </div>
    </section>

    <section class="auth-page__panel page-card">
      <div class="auth-page__panel-header">
        <div>
          <h2>欢迎回来</h2>
          <p>请选择登录方式并输入管理员身份。</p>
        </div>
        <RouterLink to="/register">没有账号？去注册</RouterLink>
      </div>

      <el-tabs v-model="activeTab" stretch>
        <el-tab-pane label="账号密码登录" name="password">
          <el-form
            ref="passwordFormRef"
            :model="passwordForm"
            :rules="passwordRules"
            label-position="top"
            @submit.prevent
          >
            <el-form-item label="用户名（邮箱或手机号）" prop="userName">
              <el-input v-model="passwordForm.userName" placeholder="请输入邮箱或手机号" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="passwordForm.password"
                type="password"
                show-password
                placeholder="请输入密码"
              />
            </el-form-item>
            <el-form-item label="身份" prop="identity">
              <el-select v-model="passwordForm.identity" placeholder="请选择身份" class="full-width">
                <el-option
                  v-for="option in IDENTITY_OPTIONS"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
                />
              </el-select>
            </el-form-item>
            <el-button :loading="passwordLoading" type="primary" class="full-width" @click="submitPasswordLogin">
              登录
            </el-button>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="邮箱验证码登录" name="code">
          <el-form
            ref="codeFormRef"
            :model="codeForm"
            :rules="codeRules"
            label-position="top"
            @submit.prevent
          >
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="codeForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="验证码" prop="verification">
              <div class="verification-row">
                <el-input v-model="codeForm.verification" placeholder="请输入验证码" />
                <el-button :disabled="countdown > 0" @click="handleSendCode">
                  {{ countdown > 0 ? `${countdown}s 后重试` : '发送验证码' }}
                </el-button>
              </div>
            </el-form-item>
            <el-form-item label="身份" prop="identity">
              <el-select v-model="codeForm.identity" placeholder="请选择身份" class="full-width">
                <el-option
                  v-for="option in IDENTITY_OPTIONS"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
                />
              </el-select>
            </el-form-item>
            <el-button :loading="codeLoading" type="primary" class="full-width" @click="submitCodeLogin">
              登录
            </el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { sendVerificationCode } from '@/api/auth'
import { useAuthStore } from '@/stores/auth'
import { IDENTITY_OPTIONS } from '@/types/api'
import { RequestError } from '@/utils/request'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const activeTab = ref<'password' | 'code'>('password')
const passwordLoading = ref(false)
const codeLoading = ref(false)
const countdown = ref(0)
const passwordFormRef = ref<FormInstance>()
const codeFormRef = ref<FormInstance>()
let countdownTimer: number | undefined

const passwordForm = ref({
  userName: '',
  password: '',
  identity: 'ADMIN' as 'ADMIN' | 'NORMAl',
})

const codeForm = ref({
  email: '',
  verification: '',
  identity: 'ADMIN' as 'ADMIN' | 'NORMAl',
})

const passwordRules: FormRules = {
  userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  identity: [{ required: true, message: '请选择身份', trigger: 'change' }],
}

const codeRules: FormRules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入合法邮箱', trigger: ['blur', 'change'] },
  ],
  verification: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
  identity: [{ required: true, message: '请选择身份', trigger: 'change' }],
}

function getRedirectTarget() {
  return typeof route.query.redirect === 'string' ? route.query.redirect : '/dashboard'
}

function startCountdown() {
  window.clearInterval(countdownTimer)
  countdown.value = 60
  countdownTimer = window.setInterval(() => {
    if (countdown.value <= 1) {
      window.clearInterval(countdownTimer)
      countdown.value = 0
      return
    }
    countdown.value -= 1
  }, 1000)
}

async function handleSendCode() {
  if (!codeFormRef.value) {
    return
  }

  try {
    await codeFormRef.value.validateField('email')
    await sendVerificationCode({ email: codeForm.value.email })
    startCountdown()
    ElMessage.success('验证码已发送，请注意查收邮箱')
  } catch (error) {
    const message = error instanceof RequestError
      ? error.status === 401
        ? '验证码服务暂不可用，请检查后端鉴权配置'
        : error.message
      : '发送验证码失败，请稍后重试'
    ElMessage.error(message)
  }
}

async function submitPasswordLogin() {
  if (!passwordFormRef.value) {
    return
  }

  try {
    passwordLoading.value = true
    await passwordFormRef.value.validate()
    await authStore.loginByPassword(passwordForm.value)
    ElMessage.success('登录成功')
    await router.replace(getRedirectTarget())
  } catch (error) {
    const message = error instanceof Error ? error.message : '登录失败，请稍后重试'
    ElMessage.error(message)
  } finally {
    passwordLoading.value = false
  }
}

async function submitCodeLogin() {
  if (!codeFormRef.value) {
    return
  }

  try {
    codeLoading.value = true
    await codeFormRef.value.validate()
    await authStore.loginByVerificationCode(codeForm.value)
    ElMessage.success('登录成功')
    await router.replace(getRedirectTarget())
  } catch (error) {
    const message = error instanceof Error ? error.message : '登录失败，请稍后重试'
    ElMessage.error(message)
  } finally {
    codeLoading.value = false
  }
}

onBeforeUnmount(() => {
  window.clearInterval(countdownTimer)
})
</script>

<style scoped>
.auth-page {
  position: relative;
  display: grid;
  min-height: 100vh;
  grid-template-columns: 1.12fr 0.88fr;
  background:
    radial-gradient(circle at top left, rgba(125, 211, 252, 0.16), transparent 28%),
    linear-gradient(135deg, #071019 0%, #050b11 46%, #03070b 100%);
}

.auth-page__hero {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  padding: 48px;
}

.auth-page__copy {
  position: relative;
  z-index: 1;
  max-width: 560px;
}

.auth-page__eyebrow {
  margin: 18px 0 12px;
  color: var(--accent-strong);
  font-size: 11px;
  letter-spacing: 0.28em;
}

.auth-page__copy h1 {
  margin: 0 0 18px;
  font-family: var(--font-display);
  font-size: clamp(2.5rem, 2rem + 1vw, 4rem);
  line-height: 1.16;
  color: var(--text-primary);
}

.auth-page__copy p:last-child {
  margin: 0;
  color: var(--text-secondary);
  font-size: 16px;
  line-height: 1.8;
}

.auth-page__orb {
  position: absolute;
  border-radius: 50%;
  filter: blur(10px);
}

.auth-page__orb--large {
  width: 380px;
  height: 380px;
  top: 10%;
  left: 12%;
  background: radial-gradient(circle, rgba(92, 174, 214, 0.22), transparent 68%);
}

.auth-page__orb--small {
  width: 220px;
  height: 220px;
  right: 12%;
  bottom: 18%;
  background: radial-gradient(circle, rgba(196, 241, 255, 0.16), transparent 68%);
}

.auth-page__panel {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 24px;
  padding: 42px;
  border-radius: 32px;
}

.auth-page__panel-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 24px;
  align-items: flex-start;
  flex-wrap: wrap;
}

.auth-page__panel-header h2 {
  margin: 0 0 8px;
  font-family: var(--font-display);
  font-size: 30px;
  font-weight: 600;
}

.auth-page__panel-header p {
  margin: 0;
  color: var(--text-secondary);
}

.full-width {
  width: 100%;
}

.verification-row {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 12px;
  width: 100%;
}

@media (max-width: 960px) {
  .auth-page {
    grid-template-columns: 1fr;
  }

  .auth-page__hero {
    min-height: 280px;
    padding: 36px 24px 0;
  }

  .auth-page__panel {
    margin: 20px;
    padding: 28px 24px;
  }
}

@media (max-width: 640px) {
  .auth-page__hero {
    display: none;
  }

  .auth-page__panel {
    min-height: 100vh;
    margin: 0;
    border-radius: 0;
  }

  .verification-row {
    grid-template-columns: 1fr;
  }
}
</style>
