import request from '@/utils/request'
import type { GetUserInfoResult, UserIdentity } from '@/types/api'

export function listUsers(identity?: UserIdentity | '') {
  return request.get<GetUserInfoResult[]>('/user/getUserList', {
    params: identity ? { identity } : undefined,
  })
}
