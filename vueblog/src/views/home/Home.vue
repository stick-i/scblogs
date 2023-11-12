<template>
	<div class="container">
		<top-bar class="top-bar"></top-bar>
		<div class="top-content">

			<div class="main-container">
				<div class="grid-content">
					<Swiper class="swiper"/>
					<div class="main-right-item" v-for="(item, index) in mainRightBlogList" :key="index">
						<router-link
							:to="{ name: 'BlogDetail', params: { blogId: item.id } }"
							target="_blank"
						>
							<div class="item-img">
								<img :src="item.coverImage?item.coverImage:require('../../assets/img/home/003.jpg')" alt=""/>
							</div>
							<div class="item-title">
								{{ item.title }}
							</div>
							<div class="item-name">{{ item.authorName }}</div>
						</router-link>
					</div>
				</div>
				<div class="roll-btn">
					<button>
						<span>&lt;&gt;</span>
						<span>换一换</span>
					</button>
				</div>
			</div>
		</div>

		<div class="content">
			<!-- 左边-->
			<div class="article-left">
				<!-- tabs标签-->
				<div class="tabs">
					<el-tabs v-model="activeName" @tab-click="handleClick">
						<el-tab-pane label="关注" name="first">
							<FollowArticleItem/>
						</el-tab-pane>
						<el-tab-pane label="推荐" name="second">
							<RecommendArticleItem style="margin-top: 2px"/>
						</el-tab-pane>
						<el-tab-pane label="最新" name="third">
							<NewArticleItem/>
						</el-tab-pane>
						<!--            <el-tab-pane label="热榜" name="fourth">热榜</el-tab-pane>-->
					</el-tabs>
				</div>
			</div>

			<!-- 右边-->
			<div class="article-right">
				<!--        关于我们-->
				<div class="template">
					<div class="template-title">
						<span>关于我们</span>
					</div>
					<div class="template-cont">
						<div class="hot1">
							<div class="our-content">
								<div>
									<div style="display: flex;justify-content: center;margin-bottom: 10px">
										<a target="_blank" href="https://github.com/stick-i/scblogs"
											 style="color:#555666;">GitHub地址&nbsp;</a>
										|
										<a target="_blank" href="https://gitee.com/sticki/scblogs"
											 style="color:#555666;">&nbsp;Gitee地址</a>
									</div>
									<a target="_blank" href="https://beian.miit.gov.cn/" style="
												display:flex;
												justify-content: center;
                        height: 20px;
                        line-height: 20px;
                        color: #939393;
                        margin-bottom: 10px
                      ">湘ICP备2021015916号-2</a>
									<a
										target="_blank"
										href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=43010502001444"
										style="
                      display: flex;
                      justify-content: center;
                      text-decoration: none;
                      height: 20px;
                      line-height: 20px;
                      margin-bottom: 10px
                    "
									>
										<img
											src="../../assets/img/home/备案图标.png"
											style="
                        width: 20px;
                        height: 20px;
                        line-height: 20px;
                      "
										/>
										<p
											style="
                        height: 20px;
                        line-height: 20px;
                        margin: 0px 0px 0px 5px;
                        color: #939393;
                      "
										>
											湘公网安备 43010502001444号
										</p>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
import TopBar from "../../components/content/topbar/TopBar";
import Swiper from "@/components/common/swiper/Swiper";
import RecommendArticleItem from "@/views/home/childComps/RecommendArticleItem";
import NewArticleItem from "@/views/home/childComps/NewArticleItem";
import FollowArticleItem from "@/views/home/childComps/FollowArticleItem";

export default {
	name: "Home",
	components: {
		Swiper,
		TopBar,
		RecommendArticleItem,
		NewArticleItem,
		FollowArticleItem,
	},
	data() {
		return {
			page: 1,
			activeName: "second",
			blogList: [],
			mainRightBlogList: [],
		};
	},
	created() {
		this.getData()
	},
	methods: {
		handleClick(tab, event) {
			console.log(tab, event);
		},
		getData() {
			this.$axios
				.get("/blog/list/recommend?page=" + this.page, {
					headers: {token: localStorage.getItem("token")},
				})
				.then((res) => {
					if (res.data.data.records.length) {
						this.blogList = res.data.data.records
						this.mainRightBlogList = this.blogList.slice(0, 6)
					}
				});
		}
	},
};
</script>

<style scoped>
/* main新增开始 */
.main-container {
	position: relative;
	width: 1380px;
	margin: 0 auto;
	padding: 25px;
}

.grid-content {
	display: grid;
	grid-template-columns: repeat(5, 1fr);
	grid-gap: 20px 12px;
}

.grid-content .swiper {
	grid-row: 1/3;
	grid-column: 1/3;
}

.main-right-item {
	width: 100%;
	height: 100%;
	/* background-color: pink; */
}

/*.main-right-item .item-img img {*/
/*  !*width: 100%;*!*/
/*  width: 266px;*/
/*}*/
.main-right-item .item-img img {
	width: 100%;
	/*width: 266.4px;*/
	height: 140px;
	/* background-color: #d3dce6; */
	border-radius: 6px;
	object-fit: cover;
	transition: all 0.3s;
}

.main-right-item .item-img img:hover {
	transform: scale(1.05);
}

.main-right-item .item-title {
	height: 44px;
	color: #18191c;
	font-size: 15px;
	font-weight: 500;
	line-height: 22px;

	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
	overflow: hidden;
	word-break: break-all;
}

.main-right-item .item-name {
	color: #9499a0;
	line-height: 16px;
}

.main-container .roll-btn {
	position: absolute;
	top: 0;
	left: calc(100% - 20px);
	z-index: 2;
	transform: translateX(10px) translateY(15px);
}

.main-container .roll-btn button {
	flex-direction: column;
	margin-left: 0;
	height: auto;
	width: 40px;
	padding: 9px;
	border-radius: 8px;
	font-size: 12px;
	color: #18191c;
	border: 1px solid #e3e5e7;
	background-color: #ffffff;
	cursor: pointer;
}

.main-container .roll-btn button:hover {
	background-color: #e3e5e7;
}

/* main新增结束 */

.top-content {
	width: 100%;
	background-color: hsla(0, 0%, 98%, 0.8);
	padding-top: 10px;
	/*background-color: #fc5531;*/
}

/* navbar新增开始 */
.top-navbar {
	background-color: #ffffff;
	width: 1380px;
	margin: 0 auto;
	padding: 10px;
	box-shadow: 0 4px 30px 0 rgb(232 232 237 / 50%);
}

.li-content {
	display: grid;
	grid-template-columns: repeat(12, 1fr);
	grid-template-rows: repeat(2, 1fr);
	grid-gap: 10px;
}

.li-content li a {
	font-size: 13px;
	height: 26px;
	line-height: 26px;
	display: inline-block;
	box-sizing: content-box;
	width: 100%;
	border: 1px solid #f1f2f3;
	border-radius: 6px;
	background-color: #f6f7f8;
	color: #61666d;
	text-align: center;
	font-weight: 400;
}

.li-content li a:hover {
	background-color: #e1e3e5;
}

/* navbar新增结束 */

/* navbar开始 */
/* .top-navbar {
  background-color: #ffffff;
  width: 1380px;
  height: 72px;
  margin: 0 auto;
  box-shadow: 0 4px 30px 0 rgb(232 232 237 / 50%);
  overflow: hidden;
  padding: 28px 0;
}
.top-navbar:hover {
  overflow: inherit;
}
.nav-content ul {
  position: relative;
  background-color: #fff;
  box-shadow: 0 4px 5px 0 rgb(232 232 237 / 50%);
  z-index: 5;
}
.nav-content ul li {
  display: inline-block;
  cursor: pointer;
  margin-bottom: 20px;
  height: 24px;
  margin-right: 20px;
  margin-left: 20px;
  background-color: #fff;
}
.nav-content ul li a {
  font-size: 15px;
  color: #555666;
}
.nav-content ul li a:hover {
  font-size: 15px;
  color: #222226;
}

.top-navbar .small-box {
  position: absolute;
  z-index: 11;
  width: 32px;
  height: 12px;
  background-image: url("../../assets/img/home/down.png");
  background-size: 32px 12px;
  left: 50%;
  top: 118px;
  transform: translateX(-50%);
}
.top-navbar:hover .small-box {
  background-image: url("../../assets/img/home/up.png");
  top: 206px;
} */

/* navbar结束 */

/* 头条开始 */
/* .main-left {
  display: flex;
  flex-direction: row;
}
.main-left-top {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 24px;
  font-size: 18px;
  font-weight: 700;
  color: #222226;
  margin-bottom: 16px;
}

.main-left-top img {
  height: 24px;
  width: 24px;
  margin-right: 5px;
}

.main-left-center img {
  width: 413px;
  height: 240px;
  object-fit: cover;
  保持原有尺寸比例。保证替换内容尺寸一定大于容器尺寸，宽度和高度至少有一个和容器一致。因此，此参数可能会让替换内容（如图片）部分区域不可见。
}

.main-left-bottom .left-title {
  display: block;
  font-size: 17px;
  font-weight: 700;
  color: #222226;
  margin-top: 8px;
}
.main-left-bottom .left-span {
  display: block;
  -webkit-line-clamp: 1;
  font-size: 14px;
  color: #999;
  margin-top: 10px;
}

.main-left-right2 .hotswiper {
  padding-left: 0px;
}
.headswiper {
  padding: 0 24px;
}
.headswiper-top {
  height: 24px;
  margin-bottom: 16px;
}
.headswiper-content {
  height: 340px;
  width: 280px;
}
.headswiper-item {
  margin-top: 24px;
}
.headswiper-item .item-title {
  display: block;
  font-size: 16px;
  font-weight: 700;
  color: #222226;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.headswiper-item .item-title:hover {
  text-decoration: underline;
}
.headswiper-item .item-name {
  display: block;
  font-size: 14px;
  color: #999;
  margin-top: 8px;
}
.headswiper-content .item-first {
  margin-top: 16px;
} */

/* 头条结束 */

.top-content .main {
	width: 1380px;
	margin: 0 auto;
	padding-top: 26px;
	display: flex;
	/*background-color: #fff;*/
}

/*右边卡片*/
.content {
	margin: 0 auto 0 auto;
	width: 1380px;
	box-sizing: border-box;
	position: relative;
	display: flex;
	justify-content: space-between;
	padding-top: 16px;
}

.content .template {
	box-sizing: border-box;
	width: 336px;
	border: 1px solid #f0f0f2;
	border-radius: 5px;
	margin-bottom: 16px;

	-webkit-transform: perspective(1px) translateZ(0);
	transform: perspective(1px) translateZ(0);
	box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0);
	-webkit-transition-duration: 0.3s;
	transition-duration: 0.3s;
	-webkit-transition-property: box-shadow, transform;
	transition-property: box-shadow, transform;
}

.content .template:hover {
	box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5);
	-webkit-transform: scale(1.02);
	transform: scale(1.02);
}

.template-title {
	height: 48px;
	padding: 0 24px;
	font-size: 18px;
	font-weight: 500;
	line-height: 48px;
	color: #222226;
	border-bottom: 1px solid #e8e8ed;
}

.template-cont {
	padding: 24px 24px 8px;
}

/*右边卡片2*/
.hot {
	display: flex;
	align-items: center;
	height: 58px;
	margin-bottom: 16px;
}

.hot img {
	width: 100px;
	height: 58px;
	-o-object-fit: cover;
	object-fit: cover;
	border-radius: 2px;
	margin-right: 8px;
}

.hot .hot-cont {
	/*margin-top: 6px;*/
	font-size: 14px;
	font-weight: 400;
	color: #222226;
	line-height: 22px;
}

.hot .hot-cont div {
	color: #222226;
}

/*右边卡片1*/

.recommendation {
	display: flex;
	align-items: center;
	height: 44px;
	font-size: 14px;
	margin-bottom: 16px;
}

.recommendation .recommendation-img {
	position: relative;
}

.recommendation img {
	width: 44px;
	height: 44px;
	border-radius: 22px;
	border: 1px solid #e8e8ed;
	margin-right: 8px;
}

.recommendation .recommendation-cont {
	flex: 1;
}

.recommendation .recommendation-cont a {
	font-weight: 400;
	color: #222226;
}

.recommendation-cont .intro {
	font-size: 12px;
	color: #999aaa;
}

.recommendation .recommendation-btn {
	cursor: pointer;
	text-align: center;
	width: 48px;
	height: 24px;
	background: #fff;
	line-height: 24px;
	border-radius: 6px;
	border: 1px solid #ccccd8;
	font-size: 12px;
	margin-left: 16px;
}

/*tabs标签页*/
.tabs .el-tabs__item {
	padding: 0 28px;
	color: #999aaa;
}

.tabs .el-tabs__item:hover {
	color: #222226;
}

.tabs .el-tabs__item.is-active {
	color: #222226;
}

.tabs .el-tabs__active-bar {
	background-color: #222226;
}

.tabs .el-tabs__nav-wrap::after {
	height: 1px;
}

.tabs .el-tabs--top {
	padding-left: 10px;
}

.tabs .el-tabs__header {
	margin: 0;
}

.content .article-left {
	width: 996px;
}
</style>
