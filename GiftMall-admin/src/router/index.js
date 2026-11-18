/**
 * 路由列表部分
 * 所有路由必须先手动写好，然后由后端菜单接口来进行匹配并且指定是否显示
 */
import { createWebHistory, createRouter } from 'vue-router';
/**
 * 为路由配置meta相当于为该路由配置了基本信息，配置的属性如下
 * title 用作标签显示字样的字段
 * hasTag 可以生成标签
 * isCache 该标签页面是否缓存
 * hidden 该标签页面是否在左边目录上显示
 * isLink 表示直接跳转新页面
 * iconName 菜单icon图标
 * fixed 标签是否固定
 * layoutName layout的名称，用作分组
 * redirectName 重定向的目标
 * path 路由地址，唯一键
 * fullPath
 * showTagIcon 标签显示的时候是否显示图标
 * content 菜单显示的详情
 * number 菜单显示的数字
 */
export const constantRoutes = [
    /** 登录注册相关页面 */
    {
        path: '/login',
        component: () => import('@/views/login/index.vue'),
    },
    /** 404页面 */
    {
        path: '/404',
        component: () => import('@/views/system/error/404.vue'),
        name: '404',
    },
    /** 401页面 */
    {
        path: '/401',
        component: () => import('@/views/system/error/401.vue'),
        name: '401',
    },
    /** 定义首页重定向地址 */
    {
        path: '',
        redirect: '/home',
    },
    // {
    //             path: '/home',
    //             name: 'main-redirect',
    //             component: () => import('@/views/system/redirect/index.vue'),
    //             meta: {
    //                 layoutName: 'main',
    //                 redirectName: 'main-redirect',
    //             },
    //         },
    /** 其他业务相关页面 */
    {
        path: '/',
        component: () => import('@/layout/main/index.vue'),
        children: [
            /**
             * 重定向页面
             * 用来刷新标签页
             *  */
            // 系统管理
            {
                path: 'user',
                component: () => import('@/views/exampleViews/userList/index.vue'),
                name: 'user',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'管理员管理',
                },
            },
            {
                path: 'role',
                component: () => import('@/views/exampleViews/roleList/index.vue'),
                name: 'role',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'角色管理',
                },
            },
            {
                path: 'menu',
                component: () => import('@/views/exampleViews/btPermission/index.vue'),
                name: 'menu',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'菜单管理',
                },
            },
            
            {
                path: 'show-list/add',
                component: () => import('@/views/exampleViews/showList/add.vue'),
                name: 'show-list-add',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                },
            },
            {
                path: 'show-list/update/:sign',
                component: () => import('@/views/exampleViews/showList/add.vue'),
                name: 'show-list-update',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    
                },
            },
             {
                path: 'userList',
                component: () => import('@/views/exampleViews/showList/index.vue'),
                name: 'userList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'用户列表',
                },
            },
            {
                path: 'userDetail',
                component: () => import('@/views/exampleViews/showList/info.vue'),
                name: 'userDetail',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'用户详情',
                },
            },
            {
                path: 'userDetail1',
                component: () => import('@/views/exampleViews/showList/info1.vue'),
                name: 'userDetail1',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'用户详情',
                },
            },
            {
                path: 'user-list',
                component: () => import('@/views/exampleViews/userList/index.vue'),
                name: 'user-list',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                },
            },
            // {
            //     path: 'role-list',
            //     component: () => import('@/views/exampleViews/roleList/index.vue'),
            //     name: 'role-list',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         hasTag: true,
            //     },
            // },
            {
                path: 'bannerList',
                component: () => import('@/views/system/newTagPage/index.vue'),
                name: 'bannerList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '首页装修',
                    hasTag: true,
                },
            },
            {
                path: 'materialsList',
                component: () => import('@/views/system/newTagPage/materialsList.vue'),
                name: 'materialsList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '帮助中心',
                    hasTag: true,
                },
            },
            {
                path: 'message',
                component: () => import('@/views/system/newTagPage/message.vue'),
                name: 'message',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '消息中心',
                    hasTag: true,
                },
            },
            {
                path: 'messageZx',
                component: () => import('@/views/system/newTagPage/messageZx.vue'),
                name: 'messageZx',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '注销管理',
                    hasTag: true,
                },
            },
            // {
            //     path: 'shopManage',
            //     component: () => import('@/views/system/newTagPage/shopManage.vue'),
            //     name: 'shopManage',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '动态管理',
            //         hasTag: true,
            //     },
            // },
            {
                path: 'locality',
                component: () => import('@/views/system/newTagPage/locality.vue'),
                name: 'locality',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '卡片管理',
                    hasTag: true,
                },
            },
            {
                path: 'chatRecord',
                component: () => import('@/views/system/newTagPage/chatRecord.vue'),
                name: 'chatRecord',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '聊天记录',
                    hasTag: true,
                },
            },
            {
                path: 'comment',
                component: () => import('@/views/system/newTagPage/comment.vue'),
                name: 'comment',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '聊天会话',
                    hasTag: true,
                },
            },
            {
                path: 'complaint',
                component: () => import('@/views/system/newTagPage/complaint.vue'),
                name: 'complaint',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '投诉中心',
                    hasTag: true,
                },
            },
            {
                path: 'financeList',
                component: () => import('@/views/exampleViews/financeList/financeList.vue'),
                name: 'financeList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '财务中心',
                    hasTag: true,
                },
            },
            {
                path: 'allocationList',
                component: () => import('@/views/exampleViews/allocationList/allocationList.vue'),
                name: 'allocationList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '系统配置',
                    hasTag: true,
                },
            },
            {
                path: 'icon-list',
                component: () => import('@/views/system/iconList/index.vue'),
                name: 'icon-list',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                },
            },
            {
                path: 'mission',
                component: () => import('@/views/system/newTagPage/mission.vue'),
                name: 'mission',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '订单中心',
                    hasTag: true,
                },
            },
            
            // {
            //     path: 'autonym',
            //     component: () => import('@/views/system/newTagPage/autonym.vue'),
            //     name: 'autonym',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '认证中心',
            //         hasTag: true,
            //     },
            // },
            {
                path: 'business',
                component: () => import('@/views/system/newTagPage/business.vue'),
                name: 'business',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '投诉中心',
                    hasTag: true,
                },
            },
            {
                path: 'Filter',
                component: () => import('@/views/system/newTagPage/packageTime.vue'),
                name: 'Filter',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '筛选配置',
                    hasTag: true,
                },
            },
            {
                path: 'invoice',
                component: () => import('@/views/system/newTagPage/invoice.vue'),
                name: 'invoice',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '发票管理',
                    hasTag: true,
                },
            },
            {
                path: 'blessing',
                component: () => import('@/views/system/newTagPage/rewardSystem.vue'),
                name: 'blessing',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '祝福语管理',
                    hasTag: true,
                },
            },
            {
                path: 'citys',
                component: () => import('@/views/system/newTagPage/citys.vue'),
                name: 'citys',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '城市管理',
                    hasTag: true,
                },
            },
            {
                path: 'couponYhq',
                component: () => import('@/views/system/campus/couponYhq.vue'),
                name: 'couponYhq',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '优惠券管理',
                    hasTag: true,
                },
            },
            {
                path: 'couponuser',
                component: () => import('@/views/system/campus/couponuser.vue'),
                name: 'couponuser',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '优惠券领取记录',
                    hasTag: true,
                },
            },
            {
                path: 'IntegralGoods',
                component: () => import('@/views/system/campus/IntegralGoods.vue'),
                name: 'IntegralGoods',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '充值配置',
                    hasTag: true,
                },
            },
            {
                path: 'emergencyHelp',
                component: () => import('@/views/system/otherTools/emergencyHelp.vue'),
                name: 'emergencyHelp',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '紧急求助',
                    hasTag: true,
                },
            },
            {
                path: 'materialLink',
                component: () => import('@/views/system/otherTools/materialLink.vue'),
                name: 'materialLink',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '指南管理',
                    hasTag: true,
                },
            },
            {
                path: 'recruitList',
                component: () => import('@/views/system/otherTools/recruitList.vue'),
                name: 'recruitList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '推广申请',
                    hasTag: true,
                },
            },
            {
                path: 'feedback',
                component: () => import('@/views/system/otherTools/feedback.vue'),
                name: 'feedback',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '反馈中心',
                    hasTag: true,
                },
            },
            {
                path: 'search',
                component: () => import('@/views/system/otherTools/search.vue'),
                name: 'search',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '搜索记录',
                    hasTag: true,
                },
            },
             {
                path: 'commentList',
                component: () => import('@/views/system/otherTools/commentList.vue'),
                name: 'commentList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '评论记录',
                    hasTag: true,
                },
            },
             {
                path: 'shopAdmin',
                component: () => import('@/views/system/groupPurchase/shopAdmin.vue'),
                name: 'shopAdmin',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '商品管理',
                    hasTag: true,
                },
            },
            {
                path: 'shopPublish',
                component: () => import('@/views/system/groupPurchase/shopPublish.vue'),
                name: 'shopPublish',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '添加商品',
                    hasTag: false,
                },
            },
             {
                path: '/shopAmend',
                component: () => import('@/views/system/groupPurchase/shopAmend.vue'),
                name: 'shopAmend',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '修改商品',
                    hasTag: false,
                },
            },
            {
                path: 'classifyAdmin',
                component: () => import('@/views/system/groupPurchase/classifyAdmin.vue'),
                name: 'classifyAdmin',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '商品分类',
                    hasTag: true,
                },
            },
            {
                path: 'specification',
                component: () => import('@/views/system/groupPurchase/specification.vue'),
                name: 'specification',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '商品规格',
                    hasTag: true,
                },
            },
             {
                path: 'orderCenter',
                component: () => import('@/views/system/orderCenter/orderCenter.vue'),
                name: 'orderCenter',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '订单中心',
                    hasTag: true,
                },
            },
            
            // {
            //     path: 'riderScheduling',
            //     component: () => import('@/views/system/riderScheduling/riderScheduling.vue'),
            //     name: 'riderScheduling',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '地图调度',
            //         hasTag: true,
            //     },
            // },
            //  // 地图
            //  {
            //     path: 'riderScheduling',
            //     component: () => import('@/views/system/iframe/index.vue'),
            //     name: 'riderScheduling',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         hasTag: true,
            //         title:'高德地图',
            //     },
            // },
            // {
            //     path: 'prohibitedAreas',
            //     component: () => import('@/views/system/riderScheduling/prohibitedAreas.vue'),
            //     name: 'prohibitedAreas',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '区域管理',
            //         hasTag: true,
            //     },
            // },
            // {
            //     path: 'dituguihua',
            //     component: () => import('@/views/system/riderScheduling/dituguihua.vue'),
            //     name: 'dituguihua',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '线路规划',
            //         hasTag: true,
            //     },
            // },
            {
                path: 'fitmentList',
                component: () => import('@/views/system/fitmentList/fitmentList.vue'),
                name: 'fitmentList',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '升级配置',
                    hasTag: true,
                },
            },
            





            // {
            //     path: 'dailicitys',
            //     component: () => import('@/views/system/dailiCnfig/dailicitys.vue'),
            //     name: 'dailicitys',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '代理城市管理',
            //         hasTag: true,
            //     },
            // },
            // {
            //     path: 'community',
            //     component: () => import('@/views/system/dailiCnfig/community.vue'),
            //     name: 'community',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '代理管理员管理',
            //         hasTag: true,
            //     },
            // },
            // {
            //     path: 'top',
            //     component: () => import('@/views/system/dailiCnfig/top.vue'),
            //     name: 'top',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         title: '代理商排行榜',
            //         hasTag: true,
            //     },
            // },
            
            
            
            
            
            
            
            
            
            
            
            

            



            // 可视化数据
            {
                path: 'big-screen/show_1',
                component: () => import('@/views/bigScreen/show_1/index.vue'),
                name: 'main-big-screen',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'可视化数据',
                    
                },
            },
            // // 地图
            //  {
            //     path: 'iframe/:sign(.*)',
            //     component: () => import('@/views/system/iframe/index.vue'),
            //     name: 'iframe',
            //     meta: {
            //         layoutName: 'main',
            //         redirectName: 'main-redirect',
            //         hasTag: true,
            //         title:'高德地图',
            //     },
            // },
           







            {
                path: 'home',
                name: 'home',
                component: () => import('@/views/system/redirect/index.vue'),
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                },
            },
            {
                path: '401',
                name: 'main-401',
                component: () => import('@/views/system/error/401.vue'),
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title: '401',
                },
            },
            {
                path: '404',
                name: 'main-404',
                component: () => import('@/views/system/error/404.vue'),
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title: '404',
                },
            },
           
            {
                path: 'iframe/:sign(.*)',
                component: () => import('@/views/system/iframe/index.vue'),
                name: 'iframe',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                },
            },
            /** 一些页面例子 */
            {
                path: 'index',
                component: () => import('@/views/system/main/index.vue'),
                name: 'main-index',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag:
                    true,
                },
            },
            {
                path: '/svg',
                component: () => import('@/views/system/iconList/index.vue'),
                name: 'svg',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    hasTag: true,
                    title:'svg图标列表',
                },
            },
            {
                path: 'mine/info',
                component: () => import('@/views/system/mine/index.vue'),
                name: 'main-mine-info',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '个人中心',
                    hasTag: true,
                },
            },
            {
                path: 'mine/info-update',
                component: () => import('@/views/system/mine/update.vue'),
                name: 'main-mine-info-update',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '修改个人信息',
                    hasTag: true,
                },
            },
            {
                path: 'mine/info-password',
                component: () => import('@/views/system/mine/password.vue'),
                name: 'main-mine-info-password',
                meta: {
                    layoutName: 'main',
                    redirectName: 'main-redirect',
                    title: '修改密码',
                    hasTag: true,
                },
            },
        ],
    },
    // 可视化数据
    {
        path: '/big-screen',
        component: () => import('@/layout/bigScreen/index.vue'),
        children: [
            /**
             * 重定向页面
             * 用来刷新标签页
             *  */
            {
                path: 'redirect/:path(.*)',
                name: 'big-screen-redirect',
                component: () => import('@/views/system/redirect/index.vue'),
                meta: {
                    layoutName: 'big-screen',
                    redirectName: 'big-screen-redirect',
                },
            },
            {
                path: 'show_1',
                component: () => import('@/views/bigScreen/show_1/index.vue'),
                name: 'big-screen-show_1',
                meta: {
                    layoutName: 'big-screen',
                    redirectName: 'big-screen-redirect',
                    hasTag: true,
                },
            },
            {
                path: 'show_2',
                component: () => import('@/views/bigScreen/show_2/index.vue'),
                name: 'big-screen-show_2',
                meta: {
                    layoutName: 'big-screen',
                    redirectName: 'big-screen-redirect',
                    hasTag: true,
                },
            },
            {
                path: 'show_3',
                component: () => import('@/views/bigScreen/show_3/index.vue'),
                name: 'big-screen-show_3',
                meta: {
                    layoutName: 'big-screen',
                    redirectName: 'big-screen-redirect',
                    hasTag: true,
                },
            },
            {
                path: 'show_4',
                component: () => import('@/views/bigScreen/show_4/index.vue'),
                name: 'big-screen-show_4',
                meta: {
                    layoutName: 'big-screen',
                    redirectName: 'big-screen-redirect',
                    hasTag: true,
                },
            },
        ],
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_APP_routeBasePath),
    routes: constantRoutes,
});

export default router;
