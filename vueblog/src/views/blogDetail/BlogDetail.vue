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
                  <img src="../../assets/img/home/default_avatar.jpg" alt="" />
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
                <img
                  class="icon"
                  src="../../assets/img/blogDetail/collection.png"
                  alt=""
                />
                <div class="info-box">
                  收藏&nbsp;{{ blogDetail.collectionNum }}
                </div>
              </div>
              <el-divider></el-divider>
              <div class="markdown-body" v-html="blogDetail.content"></div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";
import "github-markdown-css/github-markdown.css"
export default {
  name: "BlogDetail",
  components: {
    TopBar,
  },
  data() {
    return {
      blogDetail: {
        id: 1,
        title: "",
        content: "",
        releaseTime: "",
        viewNum: "",
        collectionNum: "",
      },
      profile: {
        author: "",
      },
    };
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    this.$axios.get("/blog/blog?id=" + blogId).then((res) => {
      console.log(res);
      const blog = res.data.data;
      _this.blogDetail.id = blog.content.blogId;
      _this.blogDetail.title = blog.info.title;

      // 渲染md文档
      var MarkdownIt = require("markdown-it")
      var md = new MarkdownIt()
      var result = md.render(blog.content.content)
      _this.blogDetail.content = result;

      _this.blogDetail.releaseTime = blog.info.releaseTime;
      _this.blogDetail.viewNum = blog.info.viewNum;
      _this.blogDetail.collectionNum = blog.info.collectionNum;
      _this.profile.author = blog.info.author;
    });
  },
  methods: {},
};

</script>

<style scoped>

  [v-cloak] {
    display: none;
  }

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
   background:url("../../assets/img/blogDetail/bgc.png");
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
