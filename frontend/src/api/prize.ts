import request from '@/utils/request'
import type { GetPrizeListResult } from '@/types/api'

export function getPrizeList(currentPage: number, currentPageCount: number) {
  return request.get<GetPrizeListResult>('/prize/getPrizeList', {
    params: { currentPage, currentPageCount },
  })
}
