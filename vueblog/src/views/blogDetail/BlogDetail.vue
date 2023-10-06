<template>
  <div class="blogDetail">
    <TopBar />
    <div class="blog-main">
      <div class="main">
        <!-- 左边box -->
        <div class="main-lt" id="div1">
          <!-- 侧边盒子1 个人 -->
          <div class="box asideProfile">
            <!-- 个人介绍 -->
            <div class="profile-intro">
              <div class="avatar-box" @click="gotoUserHome">
                <a href="#">
                  <img :src="profile.avatarUrl" alt="" />
                </a>
              </div>
              <div class="user-info">
                <div>
                  <span class="name">{{ profile.author }}</span>
                </div>
                <div class="user-info-bottom">
                  <span class="year">码龄3年</span>
                  <!--                  <span>暂无认证</span>-->
                </div>
              </div>
              <div class="focus-btn">
                <div class="btn" @click="followUser()">
                  <a
                    href="javascript:;"
                    v-if="isShowFollow"
                    style="color: #999aaa"
                    >已关注</a
                  >
                  <a href="javascript:;" v-else style="color: #555666">关注</a>
                </div>
              </div>
            </div>
            <!-- 个人数据介绍1 -->
            <div class="data-info">
              <dl>
                <dt class="count">{{ UserCreationStatistics.blogNum }}</dt>
                <dd class="font">原创</dd>
              </dl>
              <dl>
                <dt v-if="UserCreationStatistics.weekRank" class="count">{{ UserCreationStatistics.weekRank }}</dt>
                <dt v-else>暂无</dt>
                <dd class="font">周排名</dd>
              </dl>
              <dl>
                <dt v-if="UserCreationStatistics.totalRank" class="count">{{ UserCreationStatistics.totalRank }}</dt>
                <dt v-else>暂无</dt>
                <dd class="font">总排名</dd>
              </dl>
              <dl>
                <dt class="count">{{ UserCreationStatistics.viewNum }}</dt>
                <dd class="font">总访问</dd>
              </dl>
            </div>
            <!-- 分割线 -->
            <div class="item-rank"></div>
            <!-- 个人数据介绍2 -->
            <div class="data-info">
              <dl>
                <dt class="count">{{ UserCreationStatistics.fansNum }}</dt>
                <dd class="font">粉丝</dd>
              </dl>
              <dl>
                <dt class="count">{{ UserCreationStatistics.likeNum }}</dt>
                <dd class="font">获赞</dd>
              </dl>
              <dl>
                <dt class="count">{{ UserCreationStatistics.commentNum }}</dt>
                <dd class="font">评论</dd>
              </dl>
              <dl>
                <dt class="count">{{ UserCreationStatistics.collectNum }}</dt>
                <dd class="font">收藏</dd>
              </dl>
            </div>
          </div>
          <!-- 侧边盒子2 搜索 -->
          <!--          <div class="box asideSearchArticle">-->
          <!--            <div class="search-box">-->
          <!--              <input type="text" placeholder="搜博主文章"/>-->
          <!--              <a href="#">-->
          <!--                <img src="../../assets/img/blogDetail/search.png" alt=""/>-->
          <!--              </a>-->
          <!--            </div>-->
          <!--          </div>-->

					<!-- 侧边盒子2 目录 -->
					<div class="box asideHotArticle" v-show="navList.length!=0">
						<h3 class="aside-title">目录</h3>
						<div class="aside-content">
							<ul class="nav-list" v-for="item in navList" :key="item.name">
								<li :class="item.localName=='h2'? 'h2active':item.localName=='h3'? 'h3active':item.localName=='h4'? 'h4active':item.localName=='h5'? 'h5active':item.localName=='h6'? 'h6active':''">
									<a @click="scrollToPosition(item.href)">{{item.name}}</a>
<!--									<a :href="'#'+item.href" >{{item.name}}</a>-->
								</li>
							</ul>
						</div>
					</div>
        </div>
        <!-- 右边博客内容 -->
        <div class="main-rt textScroll">
          <el-card class="box-card">
            <div class="text item" v-cloak>
              <h1>{{ blogDetail.title }}</h1>
              <!--文章信息开始-->
              <div class="article-info">
<!--                <img-->
<!--                  class="article-type-img"-->
<!--                  src="../../assets/img/blogDetail/original.png"-->
<!--                  alt=""-->
<!--                />-->
                <div class="info-box">{{ profile.author }}</div>
                <img
                  class="icon"
                  src="../../assets/img/blogDetail/time.png"
                  alt=""
                />
                <div class="info-box">
                  于&nbsp;{{ blogDetail.releaseTime }}&nbsp;发布
                </div>
                <img
                  class="icon"
                  src="../../assets/img/blogDetail/view.png"
                  alt=""
                />
                <div class="info-box">浏览量：{{ blogDetail.viewNum }}</div>
                <!-- 收藏 -->
                <div class="collection" @click="addCollectionNum()">
                  <img
                    class="icon"
                    :src="
                      isCollect
                        ? require('../../assets/img/blogDetail/collection_active.png')
                        : require('../../assets/img/blogDetail/collection.png')
                    "
                    alt=""
                  />
                  <div class="info-box">
                    收藏&nbsp;{{ blogDetail.collectionNum }}
                  </div>
                </div>
              </div>
              <!--文章信息结束-->
              <el-divider></el-divider>
							<!--文章内容-->
              <div class="markdown-body" ref="content" v-html="blogDetail.content"></div>
            </div>
          </el-card>
          <!-- 底部文章信息 -->
          <div class="right-toolbox">
            <div class="toolbox-left">
              <div class="profile-box">
                <img class="profile-img" :src="profile.avatarUrl" alt="" />
                <span class="profile-name">{{ profile.author }}</span>
              </div>
              <div class="profile-attend" @click="followUser()">
                <a
                  href="javascript:;"
                  v-if="isShowFollow"
                  style="color: #999aaa"
                  >已关注</a
                >
                <a href="javascript:;" v-else>关注</a>
              </div>
            </div>
            <div class="toolbox-middle">
              <div class="item-box like" @click="addLikeNum()">
                <img
                  :src="
                    isLike
                      ? require('../../assets/img/blogDetail/good_active.png')
                      : require('../../assets/img/blogDetail/good.png')
                  "
                  alt=""
                />
                <span>{{ blogDetail.likeNum }}</span>
              </div>
              <div class="item-box comment">
                <img src="../../assets/img/blogDetail/comment.png" alt="" />
                <span>{{ blogDetail.commentNum }}</span>
              </div>
              <div class="item-box collection" @click="addCollectionNum()">
                <img
                  :src="
                    isCollect
                      ? require('../../assets/img/blogDetail/collection2_active.png')
                      : require('../../assets/img/blogDetail/collection2.png')
                  "
                  alt=""
                />
                <span>{{ blogDetail.collectionNum }}</span>
              </div>
            </div>
          </div>
          <!-- 评论 -->
          <BlogComment
            :facomment="comment"
            @func="getComment"
            @recordsChange="recordsChange"
          />
        </div>
      </div>
    </div>


	</div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";
import BlogComment from "@/views/blogDetail/childComps/BlogComment";
import "github-markdown-css/github-markdown.css";
import qs from "qs";
import {offsetDomTop, scrolltoToc} from "@/utils";

export default {
  name: "BlogDetail",
  components: {
    TopBar,
    BlogComment,
  },
  data() {
    return {
      isfixTab: true,
      UserCreationStatistics: {
        id: 0,
        userId: 0,
        likeNum: 0,
        fansNum: 0,
        commentNum: 0,
        collectNum: 0,
        viewNum: 0,
        blogNum: 0,
        weekRank: 0,
        totalRank: 0,
        deleted: 0,
      },
      // 点赞收藏id
      blogIdForm: {
        blogId: "",
      },
      isLike: false,
      isCollect: false,
      blogDetail: {
        id: 1,
        title: "",
        content: "",
        releaseTime: "",
        viewNum: "",
        likeNum: "",
        collectionNum: "",
        commentNum: "",
      },
      profile: {
        authorId: 0,
        author: "",
        avatarUrl: "",
      },
      // 评论
      comment: {},
      // 关注用户的id
      followIdForm: {
        followId: "",
      },
      isShowFollow: false,
			// 目录
			navList:[],
      config:{
        params: {
          userId: 0,
        },
				headers:{
					token:localStorage.getItem('token')
				}
			},
    };
  },
  created() {
  },
	mounted() {
		this.getArticleDetail();
    // this.getUserCreationStatistics();
	},

  // 滚动开始
  // 监听页面滚动
  // mounted () {
  //   window.addEventListener('scroll', this.handleTabFix, true)
  // },
  // //离开当前组件前一定要清除滚动监听，否则进入其他路由会报错
  // beforeRouteLeave (to, from, next) {
  //   window.removeEventListener('scroll', this.handleTabFix, true)
  //   next()
  // },
  // 滚动结束

  methods: {
    // getUserCreationStatistics() {

    // },
    gotoUserHome() {
        // console.log(this.profile.authorId);
        var routeUrl= this.$router.resolve({name:'UserHome',params:{userId:this.profile.authorId}})
        window.open(routeUrl.href, '_blank');
    },
		async getArticleDetail() {
			// 显示文章详情
			const blogId = this.$route.params.blogId;
			this.blogIdForm.blogId = this.$route.params.blogId;
			const _this = this;
			await this.$axios.get("/blog/detail?id=" + blogId).then((res) => {
				console.log("返回的所有数据", res);
				const blog = res.data.data;

				// 渲染md文档
				// var MarkdownIt = require("markdown-it");
				// var md = new MarkdownIt();
				// var result = md.render(blog.content.content);
				var result = blog.content.content;

				_this.blogDetail = {
					id: blog.content.blogId,
					title: blog.info.title,
					content: result,
					releaseTime: blog.info.releaseTime,
					viewNum: blog.info.viewNum,
					likeNum: blog.info.likeNum,
					collectionNum: blog.info.collectionNum,
					commentNum: blog.info.commentNum,
				};
				_this.profile = {
					author: blog.author.nickname,
					avatarUrl: blog.author.avatarUrl,
          authorId: blog.author.id
				};
				_this.followIdForm = {
          followId: blog.author.id,
        };
        document.title = _this.blogDetail.title + " - " + _this.profile.author + " - 校园博客"
        this.config.params.userId = blog.author.id;
        this.$axios.get('/user/general', this.config).then((res) => {
          // console.log(res.data.data);
          this.UserCreationStatistics = res.data.data;
        })
				// _this.comment = blog.comment;
				// console.log(_this.comment);
			});
			// 获取目录
			const aArr = this.$refs.content.querySelectorAll("a");
			let navList = [];
			for (let i = 0; i < aArr.length; i++) {
				if (aArr[i].id) {
					let href = aArr[i].id;
					let name = aArr[i].parentNode.innerText;
					let localName = aArr[i].parentNode.localName
					navList.push({
						localName,
						href: href,
						name,
					});
				}
			}
			this.navList = navList;
		},
		// 目录滚动
		scrollToPosition(id) {
			let position = offsetDomTop(document.getElementById(id));
			// position.top = position.top - 80;
			scrolltoToc(position.top);
		},
    // recordsChange(records) {
    //   this.comment = records; //在父组件修改值
    // },
    // 关注
    followUser() {
      this.$axios
        .post("/user/follow", qs.stringify(this.followIdForm), {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          if (
            res.data.code == 200 &&
            res.data.data == true &&
            res.data.status == true
          ) {
            this.isShowFollow = res.data.data;
            console.log("关注成功");
          } else if (
            res.data.code == 200 &&
            res.data.data == false &&
            res.data.status == true
          ) {
            this.isShowFollow = res.data.data;
            console.log("取消关注成功");
          } else if (
            res.data.code == 200 &&
            res.data.data == null &&
            res.data.status == false
          ) {
            console.log("不能关注自己哦~");
          }
        });
    },
    // 获取评论
    // getComment() {
    //   console.log("获取评论");
    //   const blogId = this.$route.params.blogId;
    //   const _this = this;
    //   this.$axios.get("/blog/blog?id=" + blogId).then((res) => {
    //     console.log(res);
    //     const blog = res.data.data;
    //     _this.comment = blog.comment;
    //     console.log(_this.comment);
    //   });
    // },
    // 点赞
    addLikeNum() {
      this.$axios
        .post("/blog/action/like", qs.stringify(this.blogIdForm), {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          if (res.data.data.status) {
            this.blogDetail.likeNum++;
            this.isLike = true;
          } else {
            this.blogDetail.likeNum--;
            this.isLike = false;
          }
        });
    },
    // 收藏
    addCollectionNum() {
      this.$axios
        .post("/blog/action/collect", qs.stringify(this.blogIdForm), {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          // this.isCollect = res.data.data.status
          if (res.data.data.status) {
            this.blogDetail.collectionNum++;
            this.isCollect = true;
          } else {
            this.blogDetail.collectionNum--;
            this.isCollect = false;
          }
        });
    },

    // 滚动
    // 先分别获得id为testNavBar的元素距离顶部的距离和页面滚动的距离
    // 比较他们的大小来确定是否添加fixedNavbar样式
    handleTabFix() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      // var offsetTop = document.querySelector('#testNavBar').offsetTop
      var offsetTop = document.getElementById("testNavBar").offsetTop;
      console.log(scrollTop);
      console.log("--------------");
      console.log(offsetTop);
      console.log("===============");
      if (scrollTop > 11590) {
        this.isfixTab = true;
      } else {
        this.isfixTab = false;
      }
      // scrollTop > offsetTop ? this.isfixTab = true : this.isfixTab = false
    },
    // 滚动结束
  },
};
</script>

<style scoped>
[v-cloak] {
  display: none;
}

/*目录开始*/
.h2active {
	margin-left: 20px;
}
.h3active {
	margin-left: 40px;
}
.h4active {
	margin-left: 60px;
}
.h5active {
	margin-left: 80px;
}
.h6active {
	margin-left: 100px;
}
.nav-list a {
	color: #555666;
}
.nav-list a:hover {
	color: #16a0f8;
}
/*目录结束*/

/*文章点赞收藏底部开始*/
.fixed1 {
  width: 1070px;
  height: 48px;
  background-color: pink;
  /*position: relative;*/
  position: fixed;
  /*bottom: 0;*/
  top: 735px;
  z-index: 10;
}

.fixed2 {
  width: 1070px;
  height: 48px;
  background-color: green;
}

.fixedNavbar {
  width: 1070px;
  height: 48px;
  background-color: pink;
  /*position: relative;*/
  position: fixed;
  /*bottom: 0;*/
  top: 735px;
  z-index: 10;
}

.right-toolbox {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
  align-items: center;
  padding: 17px 24px;
  height: 64px;
  box-sizing: border-box;
  background: #fff;
  box-shadow: 0 -1px 8px 0 rgba(0, 0, 0, 0.06);
  border-bottom-left-radius: 2px;
  border-bottom-right-radius: 2px;
}

.toolbox-left {
  display: flex;
  align-items: center;
}

.profile-box {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
}

.profile-box .profile-img {
  width: 32px;
  height: 32px;
  border-radius: 32px;
  border: 1px solid #f5f6f7;
  margin-right: 8px;
}

.profile-box .profile-name {
  height: 24px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 16px;
  font-weight: 600;
  color: #222226;
  line-height: 24px;
  margin-right: 8px;
}

.profile-attend {
  position: relative;
}

.profile-attend a {
  display: block;
  min-width: 60px;
  height: 28px;
  background: #fff;
  border-radius: 16px;
  font-size: 14px;
  line-height: 28px;
  text-align: center;
  border: 1px solid #ccccd8;
  color: #555666;
}

.profile-attend a:hover {
  border: 1px solid #555666;
}

.toolbox-middle {
  display: flex;
}

.toolbox-middle .item-box {
  display: flex;
  align-items: center;
  margin-right: 20px;
}

.toolbox-middle .item-box img {
  width: 24px;
  height: 24px;
  margin-right: 4px;
}

.toolbox-middle .item-box span {
  color: #999aaa;
  font-size: 14px;
}

/*文章点赞收藏底部结束*/

.text {
  font-size: 14px;
}

.item {
  padding-bottom: 18px;
}

/*标题*/
.item h1 {
  font-size: 28px;
  color: #222226;
  font-weight: 600;
  word-break: break-all;
  margin-bottom: 8px;
  margin-top: 0px;
}

.blog-main {
  min-width: 1380px;
  min-height: 100vh;
  background: url("../../assets/img/blogDetail/bgc.png");
  background-size: 16px 16px;
  padding-top: 10px;
}

/*main*/
.main {
  width: 1380px;
  display: flex;
  margin: auto;
  /*flex-direction: row;*/
  justify-content: space-between;
  flex-direction: row-reverse;
  /*background-color: palevioletred;*/
}

/*博客详情开始*/
.main-rt {
  width: 1070px;
  position: relative;
}

.article-info {
  display: flex;
  align-items: center;
  position: relative;
  background: #f8f8f8;
  border-radius: 4px;
  line-height: 32px;
}

.article-info .info-box {
  margin-right: 12px;
}

.article-info .article-type-img {
  width: 36px;
  height: 32px;
  line-height: 32px;
  margin-right: 12px;
}

.article-info .icon {
  width: 16px;
  height: 16px;
  line-height: 32px;
}

.article-info .collection {
  display: flex;
  align-items: center;
  cursor: pointer;
}

/*博客详情结束*/

/* 左边开始 */
.main-lt {
  width: 300px;
  /* background-color: plum; */
}

.main-lt .box {
  /* height: 300px; */
  background-color: #ffffff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

/* 个人box1开始 */
/* 个人介绍 */
.profile-intro {
  padding: 16px 16px 6px 16px;
  display: flex;
}

.profile-intro .avatar-box {
  position: relative;
  width: 48px;
  height: 48px;
}

.profile-intro .avatar-box img {
  display: block;
  width: 48px;
  height: 48px;
  border-radius: 50%;
}

.profile-intro .user-info {
  margin-left: 8px;
  width: 156px;
  position: relative;
  padding-top: 4px;
  display: flex;
  flex-direction: column;
}

.profile-intro .user-info .name {
  width: 100%;
  font-size: 14px;
  font-weight: 500;
  height: 20px;
  line-height: 20px;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.profile-intro .user-info .user-info-bottom {
  display: flex;
}

.profile-intro .user-info .user-info-bottom .year {
  min-width: 62px;
}

.profile-intro .user-info .user-info-bottom span {
  color: #999aaa;
  font-size: 13px;
  line-height: 20px;
  height: 20px;
  overflow: hidden;
}

/* 数据信息 */
.data-info {
  display: flex;
  padding: 9px 10px;
}

.data-info dl {
  width: 100%;
  text-align: center;
}

.data-info dl dt {
  color: #4a4d52;
  font-size: 14px;
  font-weight: 500;
  line-height: 22px;
}

.data-info dl dd {
  color: #222226;
  font-size: 14px;
  line-height: 22px;
  padding: 3px 0;
}

/* 分割线 */
.item-rank {
  height: 1px;
  background-color: #f5f6f7;
  width: 268px;
  margin: auto;
}

/* 关注 */
.focus-btn {
  padding: 0px 5px 0px 0px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.focus-btn .btn {
  font-size: 14px;
  width: 80px;
  height: 28px;
  border-radius: 14px;
  text-align: center;
  color: #555666;
  line-height: 26px;
  border: 1px solid #ccccd8;
  cursor: pointer;
}

.focus-btn .btn:hover {
  border: 1px solid #222226;
  /*border: 1px solid #5dcdff;*/
}

/* 个人box1结束 */

/* 搜索box2开始 */
.asideSearchArticle {
  padding: 0 16px;
  overflow: hidden;
}

.asideSearchArticle .search-box {
  height: 32px;
  border-radius: 5px;
  background: #f0f0f5;
  margin: 8px 0;
  position: relative;
}

.asideSearchArticle .search-box input {
  font-size: 14px;
  color: #555666;
  display: block;
  float: left;
  width: 226px;
  height: 32px;
  padding-left: 16px;
  border: 0;
  border-radius: 5px;
  background: 0 0;
  outline: none;
}

.asideSearchArticle .search-box a {
  background-color: #e8e8ee;
  color: #ccc;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 32px;
  height: 32px;
  float: right;
  line-height: 32px;
  transition: background-color 0.5s;
  border-radius: 0 5px 5px 0;
}

.asideSearchArticle .search-box a img {
  width: 20px;
  height: 20px;
}

/* 搜索box2结束 */

/* 热门文章开始 */
.asideHotArticle .aside-title {
  position: relative;
  /*padding: 16px 16px 0;*/
  padding: 0px 16px 0;
  font-size: 14px;
  /*color: #333;*/
  color: #ffffff;
  background-color: #5dcdff;
  font-weight: bold;
  line-height: 40px;
  border-bottom: 1px solid #e8e8ed;
}

.asideHotArticle .aside-content {
  padding: 12px 16px 16px 16px;
  overflow: hidden;
}

.asideHotArticle .aside-content ul {
  list-style: none;
}

.asideHotArticle .aside-content ul li {
  cursor: pointer;
  display: block;
  color: #555666;
  margin-top: 8px;
}

.asideHotArticle .aside-content ul li:hover {
  color: #fc5531;
}

.asideHotArticle .aside-content ul li img {
  width: 18px;
  height: 18px;
  vertical-align: -3px;
  margin-right: 3px;
  margin-left: 4px;
}

.asideHotArticle .aside-content ul li span {
  font-size: 12px;
  color: #999aaa;
  line-height: 24px;
}

/* 热门文章结束 */

/* 左边结束 */
</style>
