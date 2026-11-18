/*jshint esversion: 9 */
import { http } from './request';
import qs from 'qs';

/** 公用接口 */
const allApi = {
    login() {
        return http({
            url: "sys/login",
            method: 'post',
        });
        // return Promise.resolve({
        //     msg: '操作成功',
        //     code: 200,
        //     token: 'eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImJmMmYyMWRlLTUwNzctNDUxNy1hYmRlLTA0NGE4NzNhMzE0ZSJ9.DOnUxWppbtKyJGlvSBZqdExf5hrt0EdWalanz_op7rvyc9v-itJJZXbW0xn_yS5SZfXLYn1BRMlTdxadUmRmAg',
        // });
    },
    captchaImg(uuid) {
        return http({
            url: "captcha.jpg",
            method: 'post',
            data: {
                uuid: uuid
            },
        });
        // return Promise.resolve({
        //     msg: '操作成功',
        //     code: 200,
        //     token: 'eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImJmMmYyMWRlLTUwNzctNDUxNy1hYmRlLTA0NGE4NzNhMzE0ZSJ9.DOnUxWppbtKyJGlvSBZqdExf5hrt0EdWalanz_op7rvyc9v-itJJZXbW0xn_yS5SZfXLYn1BRMlTdxadUmRmAg',
        // });
    },
    getUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
            return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
        })
    },
    isAuth(key) {
        return (sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
    },
    /**
 * 清除登录信息
 */
clearLoginInfo () {
    localStorage.removeItem('token')
    sessionStorage.removeItem('token');
//   Vue.cookie.delete('token')
//   store.commit('resetStore')
//   router.options.isAddDynamicMenuRoutes = false
},
    treeDataTranslate(data, id = 'id', pid = 'parentId') {
        var res = []
        var temp = {}
        for (var i = 0; i < data.length; i++) {
            temp[data[i][id]] = data[i]
        }
        for (var k = 0; k < data.length; k++) {
            if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
                if (!temp[data[k][pid]]['children']) {
                    temp[data[k][pid]]['children'] = []
                }
                if (!temp[data[k][pid]]['_level']) {
                    temp[data[k][pid]]['_level'] = 1
                }
                data[k]['_level'] = temp[data[k][pid]]._level + 1
                temp[data[k][pid]]['children'].push(data[k])
            } else {
                res.push(data[k])
            }
        }
        return res
    },
    getUserInfo() {
        // http.get('sys/user/info', dataContainer.form,{
        //         headers: {
        //             'Content-Type': 'application/json'
        //         }
        //         }).then((res) => {
        //             console.log(res)
        //         })
        return Promise.resolve({
            msg: '操作成功',
            code: 200,
            data: {
                userName: 'admin',
                nickName: '管理员',
                avatar: 'https://s11.ax1x.com/2023/12/15/pihx4js.jpg',
            },
        });
    },
    getMenuList() {
        let menuList = []
        http.get('sys/menu/nav', {
        }).then((res) => {
            console.log('获取菜单权限', res)
            if (res.code == 0) {
                menuList = res.data.menuList
                permissions = res.data.permissions

            }


        })
        // let menuList = [
        //     {
        //         path: '/',
        //         name: 'main-index',
        //         title: '首页',
        //         content: '(有缓存，并且标签页固定)',
        //         isCache: true,
        //         fixed: true,
        //         iconName: 'svg:all-fill.svg',
        //     },
        //     {
        //         title: '站内链接',
        //         iconName: 'svg:aligncenter-fill.svg',
        //         childs: [
        //             {
        //                 path: '/main/iframe/https://www.dumogu.top/',
        //                 title: '一篇文字',
        //                 content: '有缓存',
        //                 iconName: 'img:logo.png',
        //                 showTagIcon: true,
        //                 isCache: true,
        //             },
        //             {
        //                 path: '/main/iframe/https://blog.dumogu.top/',
        //                 title: '毒蘑菇 - 博客',
        //                 iconName: 'img:logo.png',
        //                 showTagIcon: true,
        //                 isCache: false,
        //             },
        //             {
        //                 path: '/main/iframe/https://codess.dumogu.top/',
        //                 title: 'CODESS',
        //                 iconName: 'img:logo.png',
        //                 showTagIcon: true,
        //                 isCache: false,
        //             },
        //             {
        //                 path: `/main/iframe/${encodeURIComponent(
        //                     'https://cn.vuejs.org/guide/introduction.html',
        //                 )}`,
        //                 title: 'VUE3文档',
        //                 content: '有缓存',
        //                 iconName: 'img:vue.svg',
        //                 showTagIcon: true,
        //                 isCache: true,
        //             },
        //             {
        //                 path: `/main/iframe/${encodeURIComponent('https://www.amap.com/')}`,
        //                 title: '高德地图',
        //                 content: '有缓存',
        //                 iconName: 'img:gaode.svg',
        //                 showTagIcon: true,
        //                 isCache: true,
        //             },
        //         ],
        //     },
        //     {
        //         title: '多级菜单',
        //         iconName: 'svg:alignleft-fill.svg',
        //         childs: [
        //             {
        //                 title: '父级',
        //                 iconName: 'svg:aligncenter-fill.svg',
        //                 childs: [
        //                     {
        //                         name: 'show-list-update',
        //                         title: '父级',
        //                         iconName: 'svg:aligncenter-fill.svg',
        //                         childs: [
        //                             {
        //                                 path: '/main/new-tag-page/search-new-page1',
        //                                 title: '新标签页',
        //                                 iconName: 'svg:test-1.svg',
        //                                 showTagIcon: true,
        //                             },
        //                         ],
        //                     },
        //                 ],
        //             },
        //         ],
        //     },
        //     {
        //         name: 'icon-list',
        //         title: 'icon 列表展示',
        //         isCache: true,
        //         content: '(有缓存)',
        //         iconName: 'img:logo.png',
        //         showTagIcon: true,
        //         number: 20,
        //     },
        //     {
        //         path: '/main/new-tag-page/search-new-page12?name=asdasd',
        //         title: '新标签页，带路由参数',
        //         iconName: 'svg:test-1.svg',
        //         showTagIcon: true,
        //     },
        //     {
        //         title: '一篇文字',
        //         iconName: 'svg:friendship.svg',
        //         isLink: true,
        //         path: 'https://www.dumogu.top/',
        //     },
        // ];
        // return Promise.resolve({
        //     msg: '操作成功',
        //     code: 200,
        //     data: menuList,
        // });
    },
};

export default allApi;
