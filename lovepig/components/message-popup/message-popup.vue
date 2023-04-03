<template>
	<view>
		<u-toast ref="uToast" />
		<u-popup v-model="showPopup" mode="bottom" border-radius="20" height="auto" :mask-close-able="false" :focus="true" :closeable="true" :safe-area-inset-bottom="false">
			<view class="create-title">思念</view>
			<view class="message-content">{{ message.messageContent ? message.messageContent : 'ta今天什么都还没说！' }}</view>
			<view class="message-input">
				<textarea
					v-model="missData.messageContent"
					placeholder="想ta的一天"
					maxlength="200"
					height="160"
					confirm-type="send"
					@confirm="sendMiss"
					cursor-spacing="42"
				></textarea>
			</view>
			<view class="send-btn">
				<navigator class="base_button look-all" url="/pages/message/index">查看全部</navigator>
				<button class="base_button" @click="sendMiss">想你</button>
			</view>
		</u-popup>
	</view>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
	name: 'message-popup',
	props: {
		message: {
			type: Object,
			default: {
				messageContent: '',
				userBase: {
					nickname: ''
				}
			}
		}
	},
	data() {
		return {
			showPopup: false,
			missData: {
				loveId: '',
				messageContent: '',
				messageType: 1,
				state: 1
			}
		};
	},
	computed: {
		...mapGetters(['getLoveStartId'])
	},
	created() {
		console.info('发送思念信息!');
		this.missData.loveId = this.getLoveStartId;
	},
	methods: {
		showMessagePopup() {
			this.showPopup = true;
		},
		//保存思念信息
		sendMiss: function() {
			if (this.missData.messageContent.trim() === '') {
				this.$refs.uToast.show({
					title: '你想说啥?',
					type: 'warning'
				});
				return;
			}
			if (!this.$pig.user.isLogin()) {
				return;
			}
			// console.info('发送思念信息!');
			//查询思念留言
			this.$u.api.saveMessage(this.missData).then(res => {
				this.$refs.uToast.show({
					title: '思念脑电波发送成功!',
					type: 'error '
				});
				//清空内容
				this.missData.messageContent = '';
				this.showPopup = false;
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.create-title {
	text-align: center;
	font-weight: bold;
	font-size: 36rpx;
	line-height: 100rpx;
}
.message-content {
	background-color: rgba($love-color, 0.1);
	color: $love-color;
	padding: $love-padding;
	margin: $love-view-margin $love-margin;
	border-radius: $love-border-radius;
}

.message-input {
	border-radius: $love-border-radius;
	margin: $love-view-margin $love-margin;
	padding: $love-padding;
	background-color: #f3f4f5;
	textarea {
		width: 100%;
		// min-height: 150rpx;
	}
}

.send-btn {
	display: flex;
	margin: 0 $love-margin;

	.look-all {
		width: 30%;
		background: #f3f4f5 !important;
		margin-right: $love-big-margin;
		color: black;
	}
}
</style>
