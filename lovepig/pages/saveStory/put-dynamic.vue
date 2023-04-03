<template>
	<view class="main">
		<u-toast ref="uToast" />
		<!-- <view class="promise" v-if="promiseDate!=null">『{{promiseDate.promiseName}}』的第{{promiseDate.annual}}次记录</view> -->
		<view class="textarea-main"><textarea class="textarea" v-model="dynamicInfo.content" placeholder="你在想什么呢..."
				maxlength="-1" /></view>

		<view class="upload-main">
			<u-upload ref="uUpload" :action="uploadUrl" :file-list="fileList" max-count="9" max-size="3145728"
				:auto-upload="false" :form-data.sync="uploadForm" width="220" :header="uploadHeader"
				@on-uploaded="uploadSuccess"></u-upload>
		</view>

		<u-cell-group>
			<picker mode="date" :value="dynamicInfo.storyTime" @change="confirmStoryTime">
				<u-cell-item icon="heart-fill" title="故事时间" :value="dynamicInfo.storyTime">
				</u-cell-item>
			</picker>
			<u-cell-item icon="map-fill" title="所在位置"></u-cell-item>
			<picker mode="selector" value="0" @change="confirmSeeSelect" :range="seeSelector" range-key="label">
				<u-cell-item icon="eye-fill" title="谁可以看" :value="seeSelector[seeSelIndex].label"></u-cell-item>
			</picker>
			<u-cell-item title="开放评论" :arrow="false" icon="chat-fill">
				<u-switch active-color="#ff6773" v-model="dynamicInfo.commentState"></u-switch>
			</u-cell-item>
		</u-cell-group>
		<view class="save_button" @click="saveDynamic">发布</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		data() {
			return {
				uploadUrl: this.$pig.constData.uploadUrl,
				fileList: [],
				uploadForm: {
					loveId: 0,
					entityId: 0,
					entityType: 1
				},
				uploadHeader: {
					Authorization: ''
				},
				dynamicInfo: {
					loveId: 0,
					storyType: 1,
					seePermissions: 0,
					content: '',
					commentState: true,
					location: '',
					storyTime: '今天',
					state: 1
				},
				promiseDate: null,
				storyId: 0,
				seeSelector: [{
						value: '1',
						label: '公开'
					},
					{
						value: '2',
						label: '我俩'
					},
					{
						value: '3',
						label: '自己'
					}
				],
				seeSelIndex: 0,
				//0保存动态 1修改动态 2附加记录承诺
				saveType: 0
			};
		},
		computed: {
			...mapGetters(['getLoveStartId', 'getIsLogin', 'getToken']),
			...mapState(['loginUser'])
		},
		onLoad(e) {
			this.init(e);
		},
		onNavigationBarButtonTap() {},
		methods: {
			init(e) {
				console.log(e);
				//完成承诺默认配置
				if (e.promiseId) {
					uni.setNavigationBarTitle({
						title: `『${e.promiseName}』的第${e.annual}次记录`
					});
					this.promiseDate = e;
					//默认设置
					this.dynamicInfo.seePermissions = 2;
					this.dynamicInfo.promise = e;
					this.seeTitle = '我俩';
					this.saveType = 2;
				}
			},
			async saveDynamic() {
				this.dynamicInfo.loveId = this.getLoveStartId;
				this.uploadForm.loveId = this.getLoveStartId;
				this.uploadHeader.Authorization = this.getToken;
				let param = this.$u.deepClone(this.dynamicInfo);
				if (param.storyTime === '今天') {
					param.storyTime = this.$u.timeFormat(new Date(), 'yyyy年mm月dd日');
				}
				param.commentState = param.commentState ? 1 : 0;
				
				// console.log(param);
				// 判断是否需要保存承诺
				let saveStoryRes = 0;
				if (this.saveType === 2) {
					saveStoryRes = await this.$u.api.saveStoryAndPromise(param);
				} else {
					//只保存动态
					saveStoryRes = await this.$u.api.saveStory(param);
				}
				console.log('保存故事内容成功,故事id为:' + saveStoryRes);
				this.uploadForm.entityId = saveStoryRes;
				this.$nextTick(() => {
					//保存图片
					if (this.$refs.uUpload.lists.length > 0) {
						this.$refs.uToast.show({
							title: '正在上传图片...'
						});
						//开始上传图片
						this.$refs.uUpload.upload();
					} else {
						this.uploadSuccess();
					}
				})
			},
			confirmSeeSelect(e) {
				// console.log(e);
				this.seeSelIndex = e.target.value;
				this.dynamicInfo.seePermissions = this.seeSelector[e.target.value].value;
			},
			confirmStoryTime(e) {
				this.dynamicInfo.storyTime = this.$u.timeFormat(Date.parse(e.target.value), 'yyyy年mm月dd日');
			},
			uploadSuccess() {
				this.$refs.uToast.show({
					title: '恭喜发布动态成功',
					type: 'primary',
					isTab: true,
					url: '/pages/index/index'
				});
			}
		}
	};
</script>
<style lang="scss">
	.promise {
		font-weight: bold;
		margin: $love-margin;
		padding: $love-padding;
		background-color: $love-color;
		// background-color: $u-bg-color;
		border-radius: $love-border-radius;
		text-align: center;
		color: white;
	}

	.textarea-main {
		padding:$love-padding $love-big-padding;
	}

	.upload-main {
		// padding: 0 20rpx;
		margin-left: 20rpx;
	}

	.textarea {
		min-width: 100%;
		min-height: 300rpx;
	}

	.see-select {
		border-radius: $love-border-radius;
		height: 100rpx;
	}

	.save_button {
		width: 80%;
		height: 100rpx;
		text-align: center;
		line-height: 100rpx;
		color: white;
		font-size: 32rpx;
		// background-color: $love-color;
		background: linear-gradient(to right, #ed4264, #ffedbc);
		border-radius: 50rpx;
		margin: $love-view-margin auto;
	}
</style>
