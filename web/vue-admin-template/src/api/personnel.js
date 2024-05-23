import request from '@/utils/request';
const PersonnelPre = '/account/personnel';

// 登录
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getList(params) {
  return request({
    url: `${PersonnelPre}/list`,
    method: 'post',
    params
  })
}
