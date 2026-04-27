export interface BackendResult<T> {
  code: number
  date: T
  message: string
}

export type UserIdentity = 'ADMIN' | 'NORMAl'

export interface UserLoginResult {
  token: string
  identity: UserIdentity
}

export interface UserRegisterResult {
  userId: number
}

export interface LoginByPasswordPayload {
  userName: string
  password: string
  identity?: UserIdentity
}

export interface LoginByVerificationCodePayload {
  email: string
  verification: string
  identity?: UserIdentity
}

export interface SendVerificationCodePayload {
  email: string
}

export interface UserRegisterPayload {
  name: string
  email: string
  phoneNumber: string
  password: string
  identity: UserIdentity
}

export interface GetUserInfoResult {
  id: number
  userName: string
  identity: UserIdentity | string
}

export const IDENTITY_OPTIONS: Array<{ label: string; value: UserIdentity }> = [
  { label: '管理员', value: 'ADMIN' },
  { label: '普通用户', value: 'NORMAl' },
]

export function formatIdentity(identity?: string | null): string {
  switch (identity) {
    case 'ADMIN':
      return '管理员'
    case 'NORMAl':
      return '普通用户'
    default:
      return identity || '-'
  }
}
