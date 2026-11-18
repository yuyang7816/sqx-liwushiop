<template>
	<view style="padding-bottom: 200rpx;">
		<view class="bgColor">
			<view class="search" @click="goNavTo('/my/search/index')">
				<up-icon name="search" color="#D3D3D3" size="28"></up-icon>
				<up-input placeholder="输入关键字搜索商品" border="none" v-model="keyWord" @change="change"></up-input>
			</view>
			<view class="swiper_box">
				<swiper class="screen-swiper" :circular="true" :autoplay="true" interval="2500" duration="800">
					<swiper-item v-for="(item,index) in swiperList" :key="index" @tap="goNav(item)">
						<image :src="item.imageUrl" class="screen-swiper" mode="aspectFill"></image>
					</swiper-item>
				</swiper>
			</view>
		</view>

		<view class="gridbox" v-if="baseList.length!=0">
			<view v-for="(item,index) in baseList" :key="index" @tap="goNav(item)" class="gridboxItem">
				<image :src="item.imageUrl"></image>
				<view class="gridboxItem_tit">{{item.name}}</view>
			</view>
			<view class="gridboxItem" style="width: 0;"></view>
			<view class="gridboxItem" style="width: 0;"></view>
			<view class="gridboxItem" style="width: 0;"></view>
		</view>

		<!-- 卡面选择 -->
		<view class="cardbox">
			<view class="cardbox_head">
				<view class="cardbox_headTitle">个性卡面 自由选择</view>
				<view class="cardbox_headTit" @click="GoCard">更多
					<image src="/static/image/my/huiright.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="cardbox_Image">
				<view v-for="(item,index) in cardlist" :key="index" class="cardbox_ImageIte"
					@click="GoCardDetail(item)">
					<image :src="item.cardImg" mode="aspectFill"></image>
				</view>
			</view>
		</view>

		<!-- 特价好礼 -->
		<view class="SpecialOffer">
			<view class="SpecialOffer_title">特价好礼</view>
			<view class="SpecialOfferbox">
				<scroll-view class="Offerbox_Item_H " scroll-x="true" scroll-left="0" @scrolltolower="scrolltolower">
					<view v-for="(item,index) in offerlist" :key="index" class="Offerbox_Item_Hbox"
						@click="goDetail(item)">
						<image :src="item.coverImg?item.coverImg:'/static/logo.png'" mode="aspectFill"></image>
						<view class="Offerbox_Item_HboxInfo">
							<view class="Offerbox_Item_HboxInfo_title">{{item.name}}</view>
							<view class="Offerbox_Item_HboxInfo_price">
								<view class="zkj">折扣价</view>
								<view class="price"><text>¥</text>{{item.price}}</view>
							</view>
							<view class="Offerbox_Item_HboxInfo_oldprice">¥{{item.originalPrice}}</view>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>
		<!-- 列表推荐 -->

		<view class="listbox">
			<view class="listbox_dropdown">
				<view class="listbox_dropdownTitle" :class="isTj?'text-bold ':'text-df'" @click="bindTj">好礼推荐</view>
				<up-dropdown active-color="#333333" inactive-color="#333333" menuIcon="arrow-down-fill"
					menu-icon-size="12" @open="openDropdown" ref="uDropdownRef">
					<up-dropdown-item v-model="valueScene" title="送礼场景" :options="options2"
						@change="changeScene"></up-dropdown-item>
					<up-dropdown-item v-model="valueObject" title="送礼对象" :options="options3"
						@change="changeObject"></up-dropdown-item>
					<!-- <up-dropdown-item v-model="value2" title="送礼预算" :options="options2"></up-dropdown-item> -->
					<up-dropdown-item title="送礼预算">
						<view class="slot-content">
							<view class="slot-contentTitle">价格区间</view>
							<view class="slot-contentbox">
								<view class="contentbox_view">
									<up-input placeholder="最低价" border="none" v-model="minPrice"
										inputAlign="center"></up-input>
								</view>
								<view class="contentbox_line">—</view>
								<view class="contentbox_view">
									<up-input placeholder="最高价" border="none" v-model="maxPrice"
										inputAlign="center"></up-input>
								</view>
							</view>
							<view class="slot-contentBtn">
								<view class="chongzhi" @click="closeDropdown">重置</view>
								<view class="surebtn" @click="SureDropdown">确定</view>
							</view>

						</view>
					</up-dropdown-item>
				</up-dropdown>
			</view>

			<view class="listbox_Itembox">
				<view v-for="(item,index) in list" :key="index" class="Itembox" @click="goDetail(item)">
					<image :src="item.coverImg?item.coverImg:'/static/logo.png'" mode="aspectFill">
					</image>
					<view class="Itembox_info">
						<view class="Itembox_infoTitle">{{item.name}}</view>
						<view class="Itembox_infocont">
							<view class="Itembox_infocontPrice">¥{{item.price}}</view>
							<view class="Itembox_infocontAdd">
								<up-icon name="plus-circle-fill" color="#F6317A" size="28"></up-icon>
							</view>
						</view>
						<view class="Itembox_info_num">
							<view class="Itembox_info_numOldprice">¥{{item.originalPrice}}</view>
							<view class="Itembox_info_numSale">已售{{item.sales}}</view>
						</view>
					</view>
				</view>
			</view>
			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	const keyWord = ref('');
	const swiperList = reactive([]);
	const baseList = reactive([]);
	const isTj = ref(true);


	const options1 = reactive([{
			label: '默认排序',
			value: 1,
		},
		{
			label: '距离优先',
			value: 2,
		}
	]);
	const options2 = reactive([{
		label: '全部',
		value: '',
	}]);
	const options3 = reactive([{
		label: '全部',
		value: '',
	}]);

	const uDropdownRef = ref(null);
	const cardlist = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	//加载状态
	const status = ref('loadmore');
	const Offerpage = ref(1);
	const Offerpages = ref(1);
	const Offerlimit = ref(10);
	const offerlist = reactive([]);
	const valueScene = ref('');
	const valueObject = ref('');
	const minPrice = ref('');
	const maxPrice = ref('');


	onLoad(e => {
		// #ifdef MP-WEIXIN
		if (e.scene) {
			const scene = decodeURIComponent(e.scene);
			uni.setStorageSync('inviterCode', scene.split(',')[0]);
			uni.setStorageSync('shareId', scene.split(',')[1]);
		}
		// #endif

		// 获取邀请码保存到本地
		if (e.inviterCode) {
			uni.setStorageSync('inviterCode', e.inviterCode);
		}
		if (e.shareId) {
			uni.setStorageSync('shareId', e.shareId);
			if (uni.getStorageSync('userId')) {
				ClaimGift();
			}
		}

	});
	onShow(() => {
		getBanner(); //轮播图
		getBrid(); //金刚区
		getCardlist(); //卡片模块
		Offerpage.value = 1
		getOfferlist(); //特价好礼
		getGiftlist(); // 获取送礼场景列表
		getGiftPeoplelist(); //获取送礼对象列表
		getlist(); //商品列表
	});
	// 领取礼物
	const ClaimGift = () => {
		proxy.$http.postJson('/app/order/obtain', {
			userId: uni.getStorageSync('userId'), //接收人id
			shareId: uni.getStorageSync('shareId'), //分享id
		}).then(res => {
			if (res.code == 0) {
				uni.removeStorageSync('shareId')
			}
		})
	};
	// 跳转商品详情
	const goDetail = (item) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Commodity/detail?id=' + item.id
		})
	};

	// 获取轮播图
	const getBanner = () => {
		proxy.$http.postJson('/app/banner/page', {
			classify: 1, // 分类1banner图 2金刚区分类	
			state: 1, //状态1正常 2隐藏
		}).then(res => {
			if (res.code == 0) {
				swiperList.splice(0, swiperList.length, ...res.data.records)
			}
		})
	};
	// 获取顶部金刚区
	const getBrid = () => {
		proxy.$http.postJson('/app/banner/page', {
			classify: 2, // 分类1banner图 2金刚区分类	
			state: 1, //状态1正常 2隐藏
		}).then(res => {
			if (res.code == 0) {
				baseList.splice(0, baseList.length, ...res.data.records)
			}
		})
	};
	// 跳转卡面详情
	const GoCardDetail = (item) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/gift/index?cardCategoryId=' + item.cardCategoryId
		})
	};
	// 跳转全部卡面
	const GoCard = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/card/index'
		})
	};
	// 获取卡片列表
	const getCardlist = () => {
		let data = {
			page: 1,
			limit: 8,
		}
		proxy.$http.getT('/app/card/listCard', data).then(res => {
			if (res.code == 0) {
				cardlist.splice(0, cardlist.length, ...res.data.records)
			}
		})
	};
	// 特价好礼分页
	const scrolltolower = (e) => {
		console.log('触发下一页')
		if (Offerpage.value < Offerpages.value) {
			Offerpage.value += 1
			getOfferlist();
		}
	};
	// 获取特价好礼列表
	const getOfferlist = () => {
		let data = {
			page: Offerpage.value,
			limit: Offerlimit.value,
			status: 0, //商品状态(默认0正常 1下架)
			specialOffer: 0, //是否特价商品 0是 1不是
		}
		proxy.$http.getT('/app/product/listSpecialOffer', data).then(res => {
			if (res.code == 0) {
				Offerpages.value = res.data.pages
				if (page.value === 1) {
					offerlist.splice(0, offerlist.length, ...res.data.records);
				} else {
					offerlist.push(...res.data.records);
				}
			}

		})
	};
	// 获取送礼场景列表
	const getGiftlist = () => {
		proxy.$http.getT('/app/scene/list').then(res => {
			if (res.code == 0) {
				let arr = [{
					label: '送礼场景',
					value: '',
				}]
				res.data.records.forEach(d => {
					let data = {
						label: d.name,
						value: d.id,
					}
					arr.push(data)
				})
				options2.splice(0, options2.length, ...arr);
			}
		})
	};
	// 获取送礼对象列表
	const getGiftPeoplelist = () => {
		proxy.$http.getT('/app/target/list').then(res => {
			if (res.code == 0) {
				let arr = [{
					label: '送礼对象',
					value: '',
				}]
				res.data.records.forEach(d => {
					let data = {
						label: d.name,
						value: d.id,
					}
					arr.push(data)
				})
				options3.splice(0, options3.length, ...arr);
			}
		})
	};
	// 点击好礼推荐
	const bindTj = () => {
		console.log('推荐')
		isTj.value = true
		uDropdownRef.value.close();
	};
	// 打开筛选弹框
	const openDropdown = (e) => {
		console.log('open', e)
		isTj.value = false
	};

	// 场景选择
	const changeScene = (e) => {
		console.log(e)
		valueScene.value = e
		uDropdownRef.value.close();
		page.value = 1;
		getlist();
	};
	// 送礼对象选择
	const changeObject = (e) => {
		console.log(e)
		valueObject.value = e
		uDropdownRef.value.close();
		page.value = 1;
		getlist();
	};
	//重置筛选 
	const closeDropdown = () => {
		minPrice.value = '';
		maxPrice.value = '';
		uDropdownRef.value.close();
		page.value = 1;
		getlist();
	};
	//确定筛选 
	const SureDropdown = () => {
		if (!minPrice.value) {
			uni.showToast({
				title: '请输入最低价',
				icon: 'none'
			});
			return
		}
		if (!maxPrice.value) {
			uni.showToast({
				title: '请输入最高价',
				icon: 'none'
			});
			return
		}
		uDropdownRef.value.close();
		page.value = 1;
		getlist();
	};
	// 获取列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			status: 0, //商品状态(默认0正常 1下架)
			startBudget: minPrice.value, //预算起始金额
			endBudget: maxPrice.value, //预算截止金额
			sceneId: valueScene.value, //送礼场景id
			targetId: valueObject.value, //送礼对象id

		}
		proxy.$http.getT('/app/product/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
				status.value = 'nomore';
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		})
	};

	// 搜索
	const change = (e) => {
		console.log('搜索')
	};

	const goNavTo = (url) => {
		if (url) {
			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				if (url) {
					//#ifndef H5
					uni.navigateTo({
						url: '/pages/index/webView?url=' + url
					});
					//#endif
					//#ifdef H5
					window.location.href = url;
					//#endif
				}
			}
		}
	};
	const goNav = (item) => {
		if (item.name === '跳转小程序') {
			// #ifdef MP-WEIXIN
			let wxitem = item.describes.split('~');
			uni.navigateToMiniProgram({
				appId: wxitem[0],
				path: wxitem[1],
				envVersion: 'release',
				success(res) {
					// 打开成功
					console.log(res);
				}
			})
			return;
			// #endif
		}
		let url = item.url
		if (url) {
			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				if (url) {
					//#ifndef H5
					uni.navigateTo({
						url: '/pages/index/webView?url=' + url
					});
					//#endif
					//#ifdef H5
					window.location.href = url;
					//#endif
				}
			}
		}
	};
	// 跳转登录
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	onPullDownRefresh(() => {
		// let token = uni.getStorageSync('token');
		// if (!token) {
		// 	goLoginInfo();
		// 	return;
		// }
		getCardlist();
		Offerpage.value = 1
		getOfferlist();
		page.value = 1;
		getlist();
	});
	onReachBottom(() => {
		// let token = uni.getStorageSync('token');
		// if (!token) {
		// 	goLoginInfo();
		// 	return;
		// }
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getlist();
		}
	});
</script>

<style lang="scss">
	.input-placeholder {
		font-family: PingFang SC;
		font-weight: 400;
		font-size: 24rpx;
		color: #CCCCCC;
	}

	.bgColor {
		width: 100%;
		background: linear-gradient(180deg, #FFE3ED 0%, #F5F5F5 100%);
		padding-top: 20rpx
	}

	.search {
		margin: 0 30rpx;
		height: 68rpx;
		background: #FFFFFF;
		border-radius: 34rpx;
		display: flex;
		align-items: center;
		padding: 0 30rpx;
	}

	.swiper_box {
		margin: 20rpx 30rpx;

		.screen-swiper {
			width: 100%;
			height: 260rpx;
			border-radius: 24rpx;
		}
	}

	.gridbox {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		flex-wrap: wrap;
		padding: 30rpx 0;

		.gridboxItem {
			text-align: center;
			width: 25%;

			image {
				width: 88rpx;
				height: 88rpx;
				border-radius: 50%;
			}

			.gridboxItem_tit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #333333;
			}
		}
	}

	.cardbox {
		margin: 30rpx;

		.cardbox_head {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.cardbox_headTitle {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
			}

			.cardbox_headTit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #999999;
				display: flex;
				align-items: center;

				image {
					width: 26rpx;
					height: 26rpx;
				}
			}
		}

		.cardbox_Image {
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;
			margin-top: 30rpx;

			.cardbox_ImageIte {
				width: 333rpx;
				height: 200rpx;
				margin-bottom: 20rpx;

				image {
					width: 333rpx;
					height: 200rpx;
					border-radius: 24rpx;
				}
			}
		}
	}

	.SpecialOffer {
		margin: 30rpx 0 30rpx 30rpx;

		.SpecialOffer_title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
		}


		.SpecialOfferbox {
			margin-top: 20rpx;

			.Offerbox_Item_H {
				white-space: nowrap;
				width: 100%;

				.Offerbox_Item_Hbox {
					display: inline-block;
					width: 202rpx;
					background: #FFFFFF;
					border-radius: 16rpx;
					margin-right: 10rpx;

					image {
						width: 202rpx;
						height: 199rpx;
						border-radius: 16rpx 16rpx 0rpx 0rpx;
					}

					.Offerbox_Item_HboxInfo {
						padding: 4rpx 15rpx 10rpx;

						.Offerbox_Item_HboxInfo_title {
							font-family: Source Han Sans CN;
							font-weight: 500;
							font-size: 28rpx;
							color: #1E1E1E;
							width: 190rpx;

							 overflow: hidden;
							  text-overflow: ellipsis;
							  white-space: nowrap;
						}

						.Offerbox_Item_HboxInfo_price {
							display: flex;
							align-items: center;

							.zkj {
								width: 72rpx;
								height: 30rpx;
								background: linear-gradient(0deg, #F6317A 0%, #F85656 100%);
								border-radius: 5rpx;
								font-family: Source Han Sans CN;
								font-weight: 400;
								font-size: 20rpx;
								color: #FFFFFF;
								display: flex;
								align-items: center;
								justify-content: center;
							}


							.price {
								font-family: DIN;
								font-weight: bold;
								font-size: 32rpx;
								color: #F6317A;
								margin-left: 10rpx;

								text {
									font-size: 24rpx;
								}
							}
						}

						.Offerbox_Item_HboxInfo_oldprice {
							font-family: DIN;
							font-weight: 500;
							font-size: 24rpx;
							color: #CCCCCC;
							line-height: 36rpx;
							text-decoration-line: line-through;
						}
					}
				}
			}
		}
	}

	::v-deep .u-dropdown__menu {
		margin-left: 170rpx !important;
	}

	.listbox {

		.listbox_dropdown {
			display: flex;
			align-items: center;
			position: relative;

			.listbox_dropdownTitle {
				font-weight: normal;
				color: rgb(51, 51, 51);
				margin-left: 30rpx;
				position: absolute;
				z-index: 999;
			}

			.slot-content {
				background: #FFFFFF;
				padding: 30rpx;

				.slot-contentTitle {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 28rpx;
					color: #101010;
					text-align: center;
				}

				.slot-contentbox {
					display: flex;
					justify-content: space-between;
					align-items: center;
					margin: 40rpx 0;

					.contentbox_view {
						background: #f5f5f5;
						height: 78rpx;
						border-radius: 12rpx;
						display: flex;
						align-items: center;
						justify-content: center;

					}

					.contentbox_line {
						margin: 0 30rpx;
					}
				}

				.slot-contentBtn {
					display: flex;
					justify-content: space-between;
					align-items: center;

					.chongzhi {
						width: 333rpx;
						height: 78rpx;
						background: #dad4cf;
						border-radius: 54rpx;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 28rpx;
						color: #101010;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.surebtn {
						width: 333rpx;
						height: 78rpx;
						background: #F6317A;
						border-radius: 54rpx;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 28rpx;
						color: #FFFFFF;
						display: flex;
						align-items: center;
						justify-content: center;
					}
				}
			}
		}

		.listbox_Itembox {
			display: flex;
			flex-wrap: wrap;
			justify-content: space-between;
			margin: 0 30rpx;

			.Itembox {
				width: 333rpx;
				background: #FFFFFF;
				border-radius: 16rpx;
				margin-bottom: 20rpx;

				image {
					width: 333rpx;
					height: 331rpx;
					border-radius: 16rpx 16rpx 0rpx 0rpx;
				}

				.Itembox_info {
					padding: 20rpx;

					.Itembox_infoTitle {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 32rpx;
						color: #1A1A1A;
						
						 display: -webkit-box;
						  -webkit-box-orient: vertical;
						  -webkit-line-clamp: 1; /* 显示的行数 */
						  overflow: hidden;
						  text-overflow: ellipsis;
					}

					.Itembox_infocont {
						display: flex;
						align-items: center;
						justify-content: space-between;
						margin: 10rpx 0;

						.Itembox_infocontPrice {
							font-family: DIN;
							font-weight: bold;
							font-size: 38rpx;
							color: #F6317A;
						}

						.Itembox_infocontAdd {}
					}

					.Itembox_info_num {
						display: flex;
						align-items: center;
						justify-content: space-between;

						.Itembox_info_numOldprice {
							font-family: DIN;
							font-weight: 500;
							font-size: 28rpx;
							color: #CCCCCC;
							text-decoration-line: line-through;
						}

						.Itembox_info_numSale {
							font-family: PingFang SC;
							font-weight: 500;
							font-size: 24rpx;
							color: #CCCCCC;
						}
					}
				}
			}
		}
	}
</style>