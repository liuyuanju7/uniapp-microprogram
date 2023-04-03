//配置请求地址
// 如果没有通过拦截器配置域名的话，可以在这里写上完整的URL(加上域名部分)

//恋爱空间
let queryLoveStartUrl = '/loveStart';
let createLoveStartUrl = '/loveStart/createLoveStart';

//userlogin
let wechatLoginUrl = '/userLogin/wechatLogin';
let wechatRegisterUrl = '/userLogin/wechatRegistered';
let getLoginUserInfoUrl = '/userLogin/getLoginUserInfo';

// story 故事
let queryStoryDetailUrl = '/story/';
let queryStoryPageUrl = '/story/page';
let saveStoryUrl = '/story/save';
let saveStoryAndPromiseUrl = '/story/saveAndPromise';
let storyUploadBannerUrl = '/story/uploadBanner';

// message 留言
let queryMessagePageUrl = '/message/page';
let saveMessagePageUrl = '/message/save';

//resources 资源
let queryResourceListUrl = '/resources/getResourceList';
let uploadResource = '/resources/upload';

//相册
let albumListUrl = '/album/albumList';
let albumInfoUrl = '/album/albumInfo';
let albumContentUrl = '/album/albumContent';
let createAlbumUrl = '/album/createAlbum';
let changeAlbumUrl = '/album/changeAlbum';
let ChangeUploadInfoUrl = '/album/ChangeUploadInfo';


//promise 承诺
let queryPromisePageUrl = '/promise/promisePage';
let queryPromiseListUrl = '/promise/promiseList';
let savePromiseUrl = '/promise/savePromise';
let editPromiseUrl = '/promise/editPromise';
let promiseDetailUrl = '/promise/promiseDetail';


// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	//恋爱空间
	let queryLoveStart = (params = {}) => vm.$u.get(queryLoveStartUrl, params);
	let createLoveStart = (params = {}) => vm.$u.post(createLoveStartUrl, params);
	let acceptLove = (params = {}) => vm.$u.post('/loveStart/acceptLove', params);

	let wechatLogin = (params = {}) => vm.$u.post(wechatLoginUrl, params);
	let wechatRegister = (params = {}) => vm.$u.post(wechatRegisterUrl, params);
	let getLoginUserInfo = (params = {}) => vm.$u.get(getLoginUserInfoUrl + `?token=${params.token}`, params);

	//故事
	let queryStoryList = (params = {}) => vm.$u.get(queryStoryPageUrl, params);
	let queryStoryDetail = (params = {}) => vm.$u.get(queryStoryDetailUrl + params.id);
	let saveStory = (params = {}) => vm.$u.post(saveStoryUrl, params);
	let saveStoryAndPromise = (params = {}) => vm.$u.post(saveStoryAndPromiseUrl, params);

	// message 留言
	let queryMessageList = (params = {}) => vm.$u.get(queryMessagePageUrl, params);
	let saveMessage = (params = {}) => vm.$u.post(saveMessagePageUrl, params);


	//resources 资源
	let queryResourceList = (params = {}) => vm.$u.get(queryResourceListUrl, params);

	//相册
	let queryAlbumList = (params = {}) => vm.$u.get(albumListUrl, params);
	let queryAlbumInfo = (params = {}) => vm.$u.get(albumInfoUrl, params);
	let queryAlbumContent = (params = {}) => vm.$u.get(albumContentUrl, params);
	let createAlbum = (params = {}) => vm.$u.post(createAlbumUrl, params);
	let changeAlbum = (params = {}) => vm.$u.post(changeAlbumUrl, params);
	let ChangeUploadInfo = (params = {}) => vm.$u.post(ChangeUploadInfoUrl, params);

	//promise 承诺
	let queryPromisePage = (params = {}) => vm.$u.get(queryPromisePageUrl, params);
	let queryPromiseList = (params = {}) => vm.$u.get(queryPromiseListUrl, params);
	let savePromise = (params = {}) => vm.$u.post(savePromiseUrl, params);
	let editPromise = (params = {}) => vm.$u.post(editPromiseUrl, params);
	let promiseDetail = (params = {}) => vm.$u.get(promiseDetailUrl, params);


	// 将各个定义的接口名称，统一放进对象挂载到vm.$u.api(因为vm就是this，也即this.$u.api)下
	vm.$u.api = {
		queryLoveStart,
		createLoveStart,
		acceptLove,
		wechatLogin,
		wechatRegister,
		getLoginUserInfo,
		queryStoryList,
		queryStoryDetail,
		saveStory,
		saveStoryAndPromise,
		queryMessageList,
		saveMessage,
		queryResourceList,
		queryPromisePage,
		queryPromiseList,
		savePromise,
		editPromise,
		promiseDetail,
		queryAlbumList,
		queryAlbumContent,
		queryAlbumInfo,
		createAlbum,
		changeAlbum,
		ChangeUploadInfo,
	};
}

export default {
	install
}
