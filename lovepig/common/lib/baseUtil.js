//基础的内容转换工具类的工具类(多是每个页面都会用到的工具类)

/**
 * 通过数据量来判断是否还有更多数据
 * 返回u-loadmore组件的状态标识
 * @param {Object} dataCount
 */
function getloadMoreTip(dataCount) {
	if (dataCount == 0) {
		return 'nomore';
	} else {
		return 'loadmore';
	}
};
function random(min, max) {
	if (max > 0 && max >= min) {
		let gab = max - min + 1;
		return Math.floor(Math.random() * gab + min);
	} else {
		return 0;
	}
}
export default {
	getloadMoreTip,
	random
}
