import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import axios from 'axios'
import type { InternalAxiosRequestConfig } from 'axios'
import request, { client, extractResponseData, normalizeError } from '@/utils/request'

describe('request utilities', () => {
  const originalLocation = window.location

  beforeEach(() => {
    vi.restoreAllMocks()
    window.localStorage.clear()
    Object.defineProperty(window, 'location', {
      configurable: true,
      value: {
        pathname: '/dashboard',
        search: '',
        hash: '',
        assign: vi.fn(),
      },
    })
  })

  afterEach(() => {
    Object.defineProperty(window, 'location', {
      configurable: true,
      value: originalLocation,
    })
  })

  it('extracts payload from the backend date field', () => {
    const result = extractResponseData({
      code: 200,
      date: { token: 'abc' },
      message: '成功',
    })

    expect(result).toEqual({ token: 'abc' })
  })

  it('injects token header from local storage', async () => {
    window.localStorage.setItem(
      'lottery-system-auth',
      JSON.stringify({ token: 'mock-token', identity: 'ADMIN' }),
    )

    let capturedConfig: InternalAxiosRequestConfig | undefined
    client.defaults.adapter = async (config) => {
      capturedConfig = config
      return {
        data: { code: 200, date: true, message: '成功' },
        status: 200,
        statusText: 'OK',
        headers: {},
        config,
      }
    }

    await request.get<boolean>('/user/getUserList')

    expect(capturedConfig?.headers.token).toBe('mock-token')
  })

  it('normalizes 401 and redirects to login', async () => {
    window.localStorage.setItem(
      'lottery-system-auth',
      JSON.stringify({ token: 'expired-token', identity: 'ADMIN' }),
    )

    const assign = vi.fn()
    Object.defineProperty(window, 'location', {
      configurable: true,
      value: {
        pathname: '/personnel',
        search: '?identity=ADMIN',
        hash: '',
        assign,
      },
    })

    client.defaults.adapter = async (config) => {
      throw new axios.AxiosError('Unauthorized', '401', config, undefined, {
        status: 401,
        statusText: 'Unauthorized',
        headers: {},
        config,
        data: { code: 401, message: '未登录' },
      })
    }

    await expect(request.get('/user/getUserList')).rejects.toMatchObject({
      status: 401,
      message: '未登录',
    })

    expect(window.localStorage.getItem('lottery-system-auth')).toBeNull()
    expect(assign).toHaveBeenCalledWith('/login?redirect=%2Fpersonnel%3Fidentity%3DADMIN')
  })

  it('normalizes network errors', () => {
    const error = normalizeError(new Error('boom'))
    expect(error.message).toBe('boom')
  })
})
