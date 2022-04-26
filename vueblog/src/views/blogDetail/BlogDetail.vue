<template>
  <div class="blogDetail">
    <TopBar />
    <div class="blog-main">
      <div class="main">
        <!-- 左边作者 -->
        <div class="main-lt">
          <div class="box">{{ profile.author }}</div>
          <div class="box">box2</div>
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
                <img class="icon" src="../../assets/img/blogDetail/time.png" alt="">
                <div class="info-box">
                  于&nbsp;{{ blogDetail.releaseTime }}&nbsp;发布
                </div>
                <img class="icon" src="../../assets/img/blogDetail/view.png" alt="">
                <div class="info-box">浏览量：{{ blogDetail.viewNum }}</div>
                <img class="icon" src="../../assets/img/blogDetail/collection.png" alt="">
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
  background-color: skyblue;
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

.main-lt {
  width: 300px;
  /* background-color: plum; */
}
.main-lt .box {
  height: 300px;
  background-color: #ffffff;
  margin-bottom: 10px;
}
</style>
