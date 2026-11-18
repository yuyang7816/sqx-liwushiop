<template>
	<swiper class="image-container" previous-margin="110rpx" next-margin="110rpx" circular :autoplay="autoplay"
		@change="swiperChange">
		<swiper-item :class="currentIndex == index ? 'swiper-item' : 'swiper-item-side'" 
		 v-for="(item, index) in imgList" :key="index">
			<image @click="clickImg(item)" :class="currentIndex == index ? 'item-img' : 'item-img-side'"
				:src="item.cardImg" lazy-load :style="dontFirstAnimation ? 'animation: none;' : ''" mode="aspectFill">
			</image>
		</swiper-item>
	</swiper>
</template>

<script>
	export default {
		name: "cht-swiper",
		props: {
			imgList: {
				type: Array,
				default () {
					return []
				}
			},
			autoplay: {
				type: Boolean,
				default () {
					return true
				}
			}
		},
		data() {
			return {
				currentIndex: 0,
				dontFirstAnimation: true
			}
		},
		methods: {
			swiperChange(e) {
				this.dontFirstAnimation = false
				this.currentIndex = e.detail.current
				this.$emit('swiperChange', this.currentIndex)
			},
			clickImg(item) {
				// 点击图片方法：传递selected到父组件，可通过 @selected="" 来设置自定义方法
				this.$emit('selected', item, this.currentIndex)
			}
		}
	}
</script>

<style scoped>
	.image-container {
		width: 750rpx;
		height: 350rpx;
	}

	.item-img {
		width: 630rpx;
		height: 300rpx;
		border-radius: 24rpx;
		animation: to-big .3s;
	}

	.swiper-item {
		width: 630rpx;
		height: 300rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.item-img-side {
		width: 480rpx;
		height: 260rpx;
		border-radius: 24rpx;
		animation: to-mini .3s;
	}

	.swiper-item-side {
		width: 480rpx;
		height: 260rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	@keyframes to-mini {
		from {
			height: 300rpx;
		}

		to {
			height: 260rpx;
		}
	}

	@keyframes to-big {
		from {
			height: 260rpx;
		}

		to {
			height: 300rpx;
		}
	}
</style>