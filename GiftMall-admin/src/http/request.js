/*jshint esversion: 9 */
/**
 * http请求配置
 */
import axios from 'axios';
import { userDataStore } from '@/store/user';
import { messageSuccess, messageError, confirm } from '@/action/messagePrompt';
import router from '@/router';

let baseApiURL = import.meta.env.VITE_APP_baseApiURL; //api原始链接
const timeout = 13000; //api请求超时时间
console.log('baseApiURL',baseApiURL)
const types = ''
export const http = axios.create({
    //可创建多个 axios实例
    baseURL: baseApiURL, //设置公共的请求前缀
    timeout: timeout, //超时终止请求
});

http.interceptors.request.use(
    (config) => {
        // const userData = userDataStore();
        config.headers = config.headers || {};
        config.headers['token'] = localStorage.getItem('token');
        config.headers['Access-Control-Allow-Origin'] = '*';
        const token = localStorage.getItem('token'); // 从本地获取 token
		if (token) {
			config.headers['Authorization'] = `Bearer ${token}`; // 携带 token
		}
       console.log('------',localStorage.getItem('token'))
        // config.headers["token"] = cookie.get("token"); // 请求头带上token
        // switch (config.method) {
		// 	case 'post':
		// 		if(types==='json'){ //json格式提交
		// 			config.headers["Content-Type"] = 'application/json; charset=utf-8'
		// 		}else{ //form表单
		// 			config.headers["Content-Type"] = 'application/x-www-form-urlencoded; charset=utf-8'
		// 		}
		// }
        return config;
    },
    () => {
        return Promise.reject({
            msg: '请求发生错误，请稍后再试',
        });
    },
);

let modelShow = false;
http.interceptors.response.use(
    (response) => {
        return response.data
        // const data = response.data;
        // // if (!data) {
        // //     return Promise.reject({
        // //         msg: '请求发生错误',
        // //     });
        // // }
        // const res = data.code;
        // console.log(res)
        // if(res.code==0){
        //     return data;
        // }else{
        //      return data;
        //     //  return Promise.reject(data);
        // }
        // switch (status) {
        //     case 200:
        //         return data;
        //     case 401: //表示需要重新登录
        //         if (!modelShow) {
        //             modelShow = true;
        //             confirm('登录已经失效，是否重新登录？', '登录失效', {
        //                 confirmButtonText: '确定',
        //                 cancelButtonText: '取消',
        //                 type: 'warning',
        //             })
        //                 .then(() => {
        //                     router.push({
        //                         path: '/login',
        //                     });
        //                 })
        //                 .catch(() => {})
        //                 .finally(() => {
        //                     modelShow = false;
        //                 });
        //         }
        //         return Promise.reject(data);
        //     case 202: //表示失败，参数或其他原因
        //         return Promise.reject(data);
        //     case 500: //表示报错。未知原因
        //         return Promise.reject(data);
        //     default:
        //         return Promise.reject(data);
        // }
    },
    () => {
        //数据请求发生错误
        return Promise.reject({
            msg: '请求发生错误，请稍后再试',
        });
    },
);
http.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  // return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
	// return 'http://192.168.0.141:8082/sqx_fast/' + actionName
	// return 'https://newadmin.dzgqb.com/sqx_fast/' + actionName
    return "/sqx_fast/" + actionName;
}
http.adornUrlUp = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  // return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
	// return 'http://192.168.0.120:8082/sqx_fast/' + actionName
	return 'https://newadmin.dzgqb.com/sqx_fast/' + actionName
}
console.log('===================',http.adornUrl)
/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = false) => {
	var defaults = {
		t: new Date().getTime()
	};
	return openDefultParams ? merge(defaults, params) : params;
};

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = false, contentType = "json") => {
     var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
	// var defaults = {
	// 	t: new Date().getTime()
	// };
	// data = openDefultdata ? merge(defaults, data) : data;
	// types = contentType
	// return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
};
export default http;
