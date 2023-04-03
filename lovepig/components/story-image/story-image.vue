<template>
	<view class="image-main">
		<!-- 动态单图 -->
		<view class="image-big" v-if="images.length == 1">
			<image class="picture-image" :src="images[0].resourceUrl" mode="aspectFill" @click.stop="lookImage(0)"></image>
			</view>
		<!-- 动态2图或4图 -->
		<view class="image-double" v-if="images.length == 2 || images.length == 4">
			<view class="iamge_item" v-for="(img, index) in images" :key="index">
				<image class="picture-image" :src="img.resourceUrl" mode="aspectFill" @click.stop="lookImage(index)" />
			</view>
		</view>
		<!-- 动态多图 -->
		<view class="image-more " v-if="images.length > 2 && images.length != 4">
			<view class="iamge_item" v-for="(img, index) in images" :key="index">
				<image class="picture-image" :src="img.resourceUrl" mode="aspectFill" @click.stop="lookImage(index)" />
			</view>
		</view>
	</view>
</template>

<script>
export default {
	name: 'story-image',
	props: {
		images: {
			type: Array,
			default() {
				return [];
			}
		}
	},
	data() {
		return {};
	},
	methods: {
		lookImage(currImage) {
			// console.log('点击查看' + currImage);
			var images = [];
			this.images.forEach(item => images.push(item.resourceUrl));
			// console.log(images);
			uni.navigateTo({
				url: '/pages/commoms/showimg?imgs=' + JSON.stringify(images) + '&current=' + currImage,
				animationType: 'fade-in'
			});
		}
	}
};
</script>

<style lang="scss">
.image-main {
}
// 动态大图
.image-big {
	height: 320rpx;
	// width: 50%;
	margin: $love-margin 0;
	.picture-image {
		height: 100%;
		// width: 100%;
		border-radius: $love-border-radius;
	}
}

// 动态两个图
.image-double {
	display: flex;
	align-items: center;
	flex-wrap: wrap;
	justify-content: space-between;
	width: 100%;
	border-radius: $love-border-radius;

	.iamge_item {
		flex: 0 0 49.3%;
		border-radius: $love-border-radius;
		height: 280rpx;
		margin-bottom: $love-margin;
	}

	.picture-image {
		width: 100%;
		height: 100%;
		border-radius: $love-border-radius;
	}
}

// 动态多个图
.image-more {
	display: flex;
	align-items: center;
	flex-flow: wrap;
	justify-content: space-between;
	border-radius: $love-border-radius;

	.iamge_item {
		flex: 0 0 32.4%;
		border-radius: $love-border-radius;
		height: 220rpx;
		margin-bottom: 8rpx;
	}

	.picture-image {
		width: 100%;
		height: 100%;
		border-radius: $love-border-radius;
	}
}
</style>
