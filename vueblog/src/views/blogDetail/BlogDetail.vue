<template>
  <div class="blogDetail">
    <TopBar />
    <div class="blog-main">
      <div class="main">
        <!-- 左边box -->
        <div class="main-lt">
          <!-- 侧边盒子 -->
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
            <div></div>
          </div>
          <div class="box"></div>
        </div>
        <!-- 右边博客内容 -->
        <div class="main-rt">
          <el-card class="box-card">
            <div class="text item">
              <h2>{{ blogDetail.title }}</h2>
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
              <div v-html="blogDetail.content"></div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";
export default {
  name: "BlogDetail",
  components: {
    TopBar,
  },
  data() {
    return {
      blogDetail: {
        id: 1,
        title: "标题",
        content: "内容",
        releaseTime: "发表时间",
        viewNum: "",
        collectionNum: "",
      },
      profile: {
        author: "作者",
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
      _this.blogDetail.content = blog.content.content;
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
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.blog-main {
  width: 100%;
  /* background-color: skyblue; */
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
  height: 300px;
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
/* 个人box1结束 */

/* 左边结束 */
</style>
