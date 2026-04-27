import { beforeEach, describe, expect, it } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { useAuthStore } from '@/stores/auth'

describe('auth store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    window.localStorage.clear()
  })

  it('restores session from local storage', () => {
    window.localStorage.setItem(
      'lottery-system-auth',
      JSON.stringify({ token: 'token-1', identity: 'ADMIN' }),
    )

    const store = useAuthStore()
    store.restoreSession()

    expect(store.token).toBe('token-1')
    expect(store.identity).toBe('ADMIN')
    expect(store.isAuthenticated).toBe(true)
  })

  it('clears session on logout', () => {
    const store = useAuthStore()
    store.setSession({ token: 'token-2', identity: 'NORMAl' })

    store.logout()

    expect(store.token).toBe('')
    expect(store.identity).toBe('')
    expect(window.localStorage.getItem('lottery-system-auth')).toBeNull()
  })
})
