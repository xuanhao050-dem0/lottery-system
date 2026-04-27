import request from '@/utils/request'
import type {
  LoginByPasswordPayload,
  LoginByVerificationCodePayload,
  SendVerificationCodePayload,
  UserLoginResult,
  UserRegisterPayload,
  UserRegisterResult,
} from '@/types/api'

export function loginByPassword(payload: LoginByPasswordPayload) {
  return request.post<UserLoginResult, LoginByPasswordPayload>('/user/login/password', payload)
}

export function loginByVerificationCode(payload: LoginByVerificationCodePayload) {
  return request.post<UserLoginResult, LoginByVerificationCodePayload>(
    '/user/login/verificationCode',
    payload,
  )
}

export function sendVerificationCode(payload: SendVerificationCodePayload) {
  return request.post<boolean, SendVerificationCodePayload>('/user/send/verification-code', payload)
}

export function registerUser(payload: UserRegisterPayload) {
  return request.post<UserRegisterResult, UserRegisterPayload>('/user/register', payload)
}
