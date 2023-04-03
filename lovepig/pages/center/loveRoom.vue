<template>
	<view>
		<u-toast ref="uToast" />
		<view class="n-p" v-for="(item, index) in list" :key="index" hover-class="hover-class" @click="onClick(item)">
			<view style="position: relative">
				<view class="p-left"><u-icon :name="item.icon" size="45" color="#757575"></u-icon></view>
			</view>
			<view class="p-right">
				<view class="p-right-main">
					<view class="p-right-main-name">{{ item.name }}</view>
				</view>

				<view class="lfet-a">
					<view style="position: relative"></view>
					<u-icon name="arrow-right" size="30" class="p-right-icon"></u-icon>
				</view>
			</view>
		</view>
		    <better-calendar
		      ref="betterCalendarRef"
		      :showCalendar="showCalendar"
		      @confirm="selectDate"
		      @cancel="showCalendar = false"
		    ></better-calendar>
	</view>
</template>

<script>
import { mapGetters, mapState, mapMutations } from 'vuex';
export default {
	data() {
		const INIT_DAY_TYPE = "solar";
		  const INIT_DAY_TIMESTAMP = new Date().getTime();
		  const INIT_DAY = this.$u.timeFormat(new Date().getTime(), 'yyyy-mm-dd');
		return {
			avatar: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1730713693,2130926401&fm=26&gp=0.jpg',
			list: [
				{
					name: '修改小窝图',
					id: 'changeRoomWallpaper',
					icon: 'star-fill',
					iconBackground: '#398c0c'
				}
			],
			showCalendar: false,
			    date: {
			      type: INIT_DAY_TYPE, // 类型
			      timestamp: INIT_DAY_TIMESTAMP, // 时间戳
			      solarDate: INIT_DAY, // 公历日期 - 文本（2000-1-1）
			      lunarDate: "", // 农历日期 - 文本（己卯年冬月廿五(2000-1-1)）
			      lunarDateNumber: "", // 农历生日的数字写法（1999-12-25）
			    },
		};
	},
	created() {},
	computed: {
		...mapGetters(['getLoveStartId', 'getIsLogin']),
		...mapState(['loginUser'])
	},
	methods: {
		...mapMutations(['modifyToken', 'modifyLoginUser', 'modifyLoveStartInfo']),
		openBetterCalendar() {
		    this.showCalendar = true;
		  },
		  async selectDate(e) {
			  console.info(e)
		    this.date = await {
		      type: e.type,
		      timestamp: Date.parse(e.solarDate),
		      solarDate: e.solarDate,
		      lunarDate: e.lunarDate,
		      lunarDateNumber: e.lunarDateNumber,
		      isLeap: e.isLeap, // 一年里是否有闰月
		    };
			console.info(this.date)
		    this.showCalendar = false;
		  },
		onClick(item) {
			let that = this;
			console.info(item);
			switch (item.id) {
				case 'changeRoomWallpaper':
					that.changeRoomWallpaper();
					break;
				case 'photoAlbum':
					console.info('photoAlbum');
					this.openBetterCalendar();
					break;
			}
		},
		changeRoomWallpaper() {
			let that = this;
			uni.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: function(res) {
					console.log(res.tempFilePaths);
					that.uploadBanner(res.tempFilePaths);
				}
			});
		},
		tapPhotoAlbum() {
			let that = this;
			uni.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: function(res) {
					console.log(res.tempFilePaths);
					that.uploadBanner(res.tempFilePaths);
				}
			});
		},
		uploadBanner(tempFilePaths) {
			let that = this;
			uni.uploadFile({
				url: that.$pig.constData.uploadUrl,
				filePath: tempFilePaths[0],
				name: 'file',
				formData: {
					loveId: that.getLoveStartId,
					entityId: that.getLoveStartId,
					entityType: 2
				},
				success: uploadFileRes => {
					console.log(uploadFileRes.data);
					//查询恋爱空间信息
					that.$u.api.queryLoveStart({
							loveId: that.getLoveStartId
					}).then(res => {
						// console.info(res);
						that.modifyLoveStartInfo(res);
					});
					that.$refs.uToast.show({
						title: '修改成功',
						type: 'success'
					});
				}
			});
		}
	}
};
</script>

<style>
page {
	background-color: #f7f7f7;
}
.personal {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 30rpx 0 40rpx 30rpx;
}

.personal-main {
	display: flex;
	align-items: center;
}

.personal-info {
	display: flex;
	flex-direction: column;
}

.u-avatar {
	margin-right: 30rpx;
}

.navbar-right {
	padding: 0 40rpx;
}

.navbar-right-icon {
	margin-left: 50rpx;
}

.hover-class {
	background-color: #efefef;
}

.n-p {
	display: flex;
	align-items: center;
	background-color: #ffffff;
	margin-bottom: 20rpx;
}

.p-right-icon {
	padding: 0 40rpx;
}

.p-left {
	display: flex;
	align-items: center;
	justify-content: center;
	width: 60rpx;
	height: 60rpx;
	padding: 10rpx;
	margin: 20rpx;
	color: #ffffff;
	border-radius: 10rpx;
}

.p-right {
	height: 90rpx;
	flex: 1;
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.p-right-main {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.p-right-main-name {
	font-size: 36rpx;
	font-weight: 500;
}

.p-right-main-time {
	margin-right: 20rpx;
	font-size: 24rpx;
	color: #9d9d9d;
}
</style>
