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
              <div class="avatar-box">
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
                  <span>暂无认证</span>
                </div>
              </div>
            </div>
            <!-- 个人数据介绍1 -->
            <div class="data-info">
              <dl>
                <dt class="count">567</dt>
                <dd class="font">原创</dd>
              </dl>
              <dl>
                <dt class="count">21万+</dt>
                <dd class="font">周排名</dd>
              </dl>
              <dl>
                <dt class="count">2万+</dt>
                <dd class="font">总排名</dd>
              </dl>
              <dl>
                <dt class="count">10万+</dt>
                <dd class="font">访问</dd>
              </dl>
              <dl>
                <dt class="count">5</dt>
                <dd class="font">等级</dd>
              </dl>
            </div>
            <!-- 分割线 -->
            <div class="item-rank"></div>
            <!-- 个人数据介绍2 -->
            <div class="data-info">
              <dl>
                <dt class="count">1324</dt>
                <dd class="font">积分</dd>
              </dl>
              <dl>
                <dt class="count">145</dt>
                <dd class="font">粉丝</dd>
              </dl>
              <dl>
                <dt class="count">98</dt>
                <dd class="font">获赞</dd>
              </dl>
              <dl>
                <dt class="count">76</dt>
                <dd class="font">评论</dd>
              </dl>
              <dl>
                <dt class="count">324</dt>
                <dd class="font">收藏</dd>
              </dl>
            </div>
            <!-- 关注按钮 -->
            <div class="focus-btn">
              <div class="btn">私信</div>
              <div class="btn">关注</div>
            </div>
          </div>
          <!-- 侧边盒子2 搜索 -->
          <div class="box asideSearchArticle">
            <div class="search-box">
              <input type="text" placeholder="搜博主文章" />
              <a href="#">
                <img src="../../assets/img/blogDetail/search.png" alt="" />
              </a>
            </div>
          </div>
          <!-- 侧边盒子3 热门文章 -->
          <div class="box asideHotArticle">
            <h3 class="aside-title">热门文章</h3>
            <div class="aside-content">
              <ul>
                <li v-for="(item, index) in 5" :key="index">
                  WPF使用MaterialDesign -- 好看的控件先从button开始
                  <img src="../../assets/img/blogDetail/view.png" alt="" />
                  <span>124</span>
                </li>
              </ul>
            </div>
          </div>
          <!-- 侧边盒子4 推荐文章 -->
          <div class="box asideHotArticle">
            <h3 class="aside-title">推荐文章</h3>
            <div class="aside-content">
              <ul>
                <li v-for="(item, index) in 5" :key="index">
                  WPF使用MaterialDesign -- 好看的控件先从button开始
                  <img src="../../assets/img/blogDetail/view.png" alt="" />
                  <span>124</span>
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
                <img
                  class="article-type-img"
                  src="../../assets/img/blogDetail/original.png"
                  alt=""
                />
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
              <div class="markdown-body" v-html="blogDetail.content"></div>
            </div>
          </el-card>
          <!-- 底部文章信息 -->

          <!-- <div id="testNavBar" :class="isfixTab == false ? 'fixed1' : 'fixed2'">
            <div>1234</div>
          </div> -->
          <div class="right-toolbox">
            <div class="toolbox-left">
              <div class="profile-box">
                <img class="profile-img" :src="profile.avatarUrl" alt="" />
                <span class="profile-name">{{ profile.author }}</span>
              </div>
              <div class="profile-attend">
                <a href="javascript:;">关注</a>
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
                <span>{{blogDetail.likeNum}}</span>
              </div>
              <div class="item-box comment">
                <img src="../../assets/img/blogDetail/comment.png" alt="" />
                <span>{{blogDetail.commentNum}}</span>
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
          <BlogComment/>
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
export default {
  name: "BlogDetail",
  components: {
    TopBar,
    BlogComment
  },
  data() {
    return {
      isfixTab: true,
      // 收藏id
      blogIdForm: {
        blogId: "",
      },
      isLike:false,
      isCollect: false,
      blogDetail: {
        id: 1,
        title: "",
        content: "",
        releaseTime: "",
        viewNum: "",
        likeNum:'',
        collectionNum: "",
        commentNum:"",
      },
      profile: {
        author: "",
        avatarUrl: "",
      },
    };
  },
  created() {
    // 显示文章详情
    const blogId = this.$route.params.blogId;
    this.blogIdForm.blogId = this.$route.params.blogId;
    const _this = this;
    this.$axios.get("/blog/blog?id=" + blogId).then((res) => {
      console.log(res);
      const blog = res.data.data;

      // 渲染md文档
      var MarkdownIt = require("markdown-it");
      var md = new MarkdownIt();
      var result = md.render(blog.content.content);

      _this.blogDetail = {
        id: blog.content.blogId,
        title: blog.info.title,
        content: result,
        releaseTime: blog.info.releaseTime,
        viewNum: blog.info.viewNum,
        likeNum:blog.info.likeNum,
        collectionNum: blog.info.collectionNum,
        commentNum: blog.info.commentNum,
      };
      _this.profile = {
        author: blog.info.author,
        avatarUrl: blog.author.avatarUrl,
      };
    })
    // 显示点赞收藏
    // this.$axios
    //   .post("/blog/like", qs.stringify(this.blogIdForm), {
    //     headers: { token: localStorage.getItem("token") },
    //   })
    //   .then((res) => {
    //     this.isLike = res.data.data.status;
    //     this.$axios.post("/blog/collect", qs.stringify(this.blogIdForm), {
    //       headers: { token: localStorage.getItem("token") },
    //     }).then((response)=>{
    //       this.isCollect = res.data.data.status;
    //     })
    //   });
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
    // 点赞
    addLikeNum() {
      this.$axios
        .post("/action/blog/like", qs.stringify(this.blogIdForm), {
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
        .post("/action/blog/collect", qs.stringify(this.blogIdForm), {
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
  width: 100%;
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
  flex-direction: row;
  justify-content: space-between;
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
  padding: 8px 16px 20px 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.focus-btn .btn {
  font-size: 14px;
  width: 126px;
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
  padding: 16px 16px 0;
  font-size: 14px;
  color: #333;
  font-weight: bold;
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
