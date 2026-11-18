/**
 * https://vitejs.dev/config/
 * https://cn.vitejs.dev/config/#server-proxy
 */
import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import vitePluginAliOss from 'vite-plugin-ali-oss';
import ossOptionConfig from './oss.config';
import AutoImport from 'unplugin-auto-import/vite';
import Components from 'unplugin-vue-components/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';
import { resolve } from 'path';
const pathResolve = (dir) => {
    return resolve(__dirname, '.', dir);
};
// const path = require('path')
// const devEnv = require('./dev.env')

export default defineConfig(({ mode }) => {
    const isProd = mode !== 'development';
    const env = loadEnv(mode, process.cwd(), ''); // 自定义的环境变量
    let base = env.VITE_APP_biuldBase; // 打包的静态资源的burl base
    let outDir = 'dist';
    let plugins = [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ];
    /** 如果使用了ali oss来储存文件 */
    if (!!ossOptionConfig.url) {
        base = ossOptionConfig.url + base;
        plugins.push(
            vitePluginAliOss({
                region: ossOptionConfig.region,
                accessKeyId: ossOptionConfig.accessKeyId,
                accessKeySecret: ossOptionConfig.accessKeySecret,
                bucket: ossOptionConfig.bucket,
                overwrite: true,
            }),
        );
    }
    return {
        base: base, // must be URL when build
        plugins: plugins,
        build: {
            /** 指定输出路径 */
            outDir: outDir,
            reportCompressedSize: false,
            sourcemap: isProd ? false : true, // 这个生产环境一定要关闭，不然打包的产物会很大
        },
        resolve: {
            alias: {
                '@': pathResolve('./src'),
            },
        },
        server: {
            // Paths
            // assetsSubDirectory: 'static',
            // assetsPublicPath: '/',
            // // 代理列表, 是否开启代理通过[./dev.env.js]配置
            // proxyTable: devEnv.OPEN_PROXY === false ? {} : {
            //     '/sqx_fast': {
            //         target: 'http://192.168.0.122:9192/sqx_fast/',
            //         // target: 'https://lwscadmin.xianmxkj.com/sqx_fast/', 
            //         // target: 'http://127.0.0.1/sqx_fast/',
            //         changeOrigin: true,
            //         pathRewrite: {
            //             '^/sqx_fast': ''
            //         }
            //     }
            // },
            port: 8087,
            host: true,
            open: true,
            proxy: {
                '/sqx_fast': {
                    // target: 'http://192.168.0.123:9191/',
                    target: 'https://lwscadmin.xianmxkj.com/sqx_fast/',
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/sqx_fast/, ''),
                },
            },
        },
    };
});
