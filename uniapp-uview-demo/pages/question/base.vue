<template>
	<view class="container">
	  <view class="bg"></view>
	  <view class="form">
	    <view class="header">
	      <view class="title">人工智能回答你需要的问题</view>
	    </view>
	    <u-transition :show="true" mode="fade-left">
	      <view class="panel">
	        <view class="head">
	          <view class="title">填写您的问题</view>
	          <view class="tips" @tap="show = true">切换自有KEY</view>
	        </view>
	        <view class="textarea">
	          <u--textarea
	            height="140"
	            v-model="problem"
	            placeholder="支持长按粘贴您的问题"
	            confirm-type="send"
	          >
	          </u--textarea>
	        </view>
	        <view class="btn-group">
	          <u-button
	            iconColor="#ffffff"
	            color="#0071ff"
	            icon="edit-pen"
	            text="获取问题答案"
	            @tap="getAnswer"
	          ></u-button>
	        </view>
	      </view>
	    </u-transition>
	    <mp-html
	      :content="item.desc"
	      v-for="item in ad('5')"
	      :key="item.id"
	    ></mp-html>
	    <u-transition :show="true" mode="fade-left">
	      <view class="panel">
	        <view class="head">
	          <view class="title">答案</view>
	          <view class="tips"></view>
	        </view>
	        <view class="textarea">
	          <u--textarea
	            height="140"
	            :value="answer"
	            placeholder="支持可复制您的答案"
	            confirm-type="send"
	          >
	          </u--textarea>
	        </view>
	        <view class="btn-group">
	          <view class="btn get" style="width: 100%">
	            <u-button
	              iconColor="#ffffff"
	              color="#0071ff"
	              icon="edit-pen"
	              text="复制答案"
	              @tap="copy"
	            >
	            </u-button>
	          </view>
	        </view>
	      </view>
	    </u-transition>
	  </view>
	  <view class="content" style="padding: 20rpx">
	    <mp-html
	      :content="item.desc"
	      v-for="item in ad('1')"
	      :key="item.id"
	    ></mp-html>
	  </view>
	  <change-key v-model="show" />
	  <tabbar ref="tabBar" />
	</view>
</template>

<script>
</script>
<style>
</style>