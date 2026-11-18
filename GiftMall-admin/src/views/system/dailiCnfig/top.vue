<template>
    <div class="_div">
        111 
    </div> 
</template>

<script>
/**
 * 页面例子
 */
import {
    defineComponent,
    onBeforeUnmount,
    ref,
    reactive,
    getCurrentInstance,
    onActivated,
    onMounted,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { Delete } from '@element-plus/icons-vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';

export default defineComponent({
    components: {
        SvgIcon,
        Delete,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
           
        

        });
        /**
         * 数据初始化
         */

        function initData() {
            let params = route.params;
            if (!params.sign) return;
            // dataContainer.form = {
            //     id: params.sign,
            // };
        }
        initData();

        function prev() {
            router.back()
        }
        
        
        /** 获取用户详情数据 */
        function dataSelect() {
            let userId = route.query.userId
            http.get('adminerrand/findUserMessage', { 
                params: {
                    userId:userId
                }
            }, {
            }).then((res) => {
                console.log('============',res)
                
            })
        }
        console.log('========1111====',route.query.userId)
        dataSelect();
       
           
        return {
            dataContainer,
            initData,
            prev,
            
            dataSelect,
            
        };
    },
});
</script>

<style lang="scss" scoped>
._div{
    padding: 20px;
}
.main-view {
    display: flex;
    flex-direction: column;
    width: 100%;

    .container {
        // background-color: white;
        width: 100%;
        // min-height: 800px;
        height: fit-content;
        border-radius: 5px;
        padding: 30px;
        box-sizing: border-box;

        >* {
            margin: 0 0 30px 0;

            &:last-child {
                margin: 0;
            }
        }
    }
}
.detailtabel h2 {
		margin-top: 0;
	}

	.detailtabel .table_main table {
		width: 100%;
	}

	.detailtabel .table_main {
		border: 1px solid #e8e8e8;
	}

	.detailtabel table tr {
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table tr th {
		background-color: #fafafa;
		padding: 16px 24px;
		border-right: 1px solid #e8e8e8;
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table tr td {
		padding: 16px 24px;
		border-right: 1px solid #e8e8e8;
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table th {
		color: rgba(0, 0, 0, .85);
		font-weight: 400;
		font-size: 14px;
		line-height: 1.5;
	}

	.border-rt {
		border-right: none !important;
	}

	.border-bt {
		border-bottom: none !important;
	}
	.el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}
	
	.avatar-uploader {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		border-color: var(--nf-brand);
		width: 148px;
		height: 148px;
	}
	
	.el-upload:hover {
		border-color: var(--nf-brand);
	}
	
	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 148px;
		height: 148px;
		line-height: 148px;
		text-align: center;
	}
	
	.avatar {
		width: 148px;
		height: 148px;
		display: block;
	}
	
	
</style>
