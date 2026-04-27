import { defineStore } from 'pinia'
import { loginByPassword, loginByVerificationCode } from '@/api/auth'
import type {
  LoginByPasswordPayload,
  LoginByVerificationCodePayload,
  UserIdentity,
  UserLoginResult,
} from '@/types/api'

export const AUTH_STORAGE_KEY = 'lottery-system-auth'

interface AuthSnapshot {
  token: string
  identity: UserIdentity | ''
}

function persistAuth(snapshot: AuthSnapshot) {
  window.localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(snapshot))
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthSnapshot => ({
    token: '',
    identity: '',
  }),
  getters: {
    isAuthenticated: (state) => Boolean(state.token),
  },
  actions: {
    restoreSession() {
      const raw = window.localStorage.getItem(AUTH_STORAGE_KEY)
      if (!raw) {
        return
      }

      try {
        const snapshot = JSON.parse(raw) as Partial<AuthSnapshot>
        this.token = typeof snapshot.token === 'string' ? snapshot.token : ''
        this.identity = snapshot.identity === 'ADMIN' || snapshot.identity === 'NORMAl'
          ? snapshot.identity
          : ''
      } catch {
        this.clearSession()
      }
    },
    setSession(result: UserLoginResult) {
      this.token = result.token
      this.identity = result.identity
      persistAuth({
        token: this.token,
        identity: this.identity,
      })
    },
    clearSession() {
      this.token = ''
      this.identity = ''
      window.localStorage.removeItem(AUTH_STORAGE_KEY)
    },
    async loginByPassword(payload: LoginByPasswordPayload) {
      const result = await loginByPassword(payload)
      this.setSession(result)
      return result
    },
    async loginByVerificationCode(payload: LoginByVerificationCodePayload) {
      const result = await loginByVerificationCode(payload)
      this.setSession(result)
      return result
    },
    logout() {
      this.clearSession()
    },
  },
})
