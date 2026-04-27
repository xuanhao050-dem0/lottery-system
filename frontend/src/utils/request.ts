import axios, { AxiosError, AxiosHeaders, type AxiosRequestConfig } from 'axios'
import type { BackendResult } from '@/types/api'

const AUTH_STORAGE_KEY = 'lottery-system-auth'
const SUCCESS_CODE = 200

export class RequestError extends Error {
  status?: number
  code?: number

  constructor(message: string, options: { status?: number; code?: number } = {}) {
    super(message)
    this.name = 'RequestError'
    this.status = options.status
    this.code = options.code
  }
}

export function extractResponseData<T>(payload: BackendResult<T>): T {
  if (payload.code === SUCCESS_CODE) {
    return payload.date
  }

  throw new RequestError(payload.message || '请求失败，请稍后重试', {
    code: payload.code,
  })
}

function getStoredToken(): string {
  if (typeof window === 'undefined') {
    return ''
  }

  try {
    const raw = window.localStorage.getItem(AUTH_STORAGE_KEY)
    if (!raw) {
      return ''
    }

    const parsed = JSON.parse(raw) as { token?: string }
    return typeof parsed.token === 'string' ? parsed.token : ''
  } catch {
    return ''
  }
}

function clearStoredAuth() {
  if (typeof window === 'undefined') {
    return
  }

  window.localStorage.removeItem(AUTH_STORAGE_KEY)
}

function redirectToLogin() {
  if (typeof window === 'undefined') {
    return
  }

  const publicPaths = new Set(['/login', '/register'])
  if (publicPaths.has(window.location.pathname)) {
    return
  }

  const redirect = `${window.location.pathname}${window.location.search}${window.location.hash}`
  const query = redirect && redirect !== '/' ? `?redirect=${encodeURIComponent(redirect)}` : ''
  window.location.assign(`/login${query}`)
}

export function normalizeError(error: unknown): RequestError {
  if (error instanceof RequestError) {
    return error
  }

  if (axios.isAxiosError(error)) {
    const status = error.response?.status
    const payload = error.response?.data as Partial<BackendResult<unknown>> | undefined
    const code = typeof payload?.code === 'number' ? payload.code : undefined

    let message = payload?.message
    if (!message) {
      if (status === 401) {
        message = '登录状态已失效，请重新登录'
      } else if (!error.response) {
        message = '网络请求失败，请检查后端服务或代理配置'
      } else {
        message = '请求失败，请稍后重试'
      }
    }

    return new RequestError(message, { status, code })
  }

  if (error instanceof Error) {
    return new RequestError(error.message)
  }

  return new RequestError('请求失败，请稍后重试')
}

export const client = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 10000,
})

client.interceptors.request.use((config) => {
  const token = getStoredToken()

  if (token) {
    const headers = AxiosHeaders.from(config.headers)
    headers.set('token', token)
    config.headers = headers
  }

  return config
})

client.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    const normalized = normalizeError(error)

    if (normalized.status === 401) {
      clearStoredAuth()
      redirectToLogin()
    }

    return Promise.reject(normalized)
  },
)

async function get<T>(url: string, config?: AxiosRequestConfig) {
  const response = await client.get<BackendResult<T>>(url, config)
  return extractResponseData(response.data)
}

async function post<T, D = unknown>(url: string, data?: D, config?: AxiosRequestConfig<D>) {
  const response = await client.post<BackendResult<T>>(url, data, config)
  return extractResponseData(response.data)
}

export default {
  get,
  post,
}
