<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <!-- 搜索框 -->
      <div class="search-toolbar">
        <div class="so-toolbar">
          <div class="toolbar-main">
            <div class="search-box">
              <input type="text" placeholder="vue的常用指令" v-model="key" />
              <button id="search" @click="searchBlogs">搜索</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 搜索展示 -->
      <div class="main">
        <div class="main-lt">
          <div class="list-container">

            <BlogSearchArticleItem :searchkey="key" :blogsearchlist="blogSearchList"/>

          </div>
        </div>
        <div class="main-rt">
          <div class="box">
            <h3>全站热搜榜</h3>
            <ul>
              <li><span>1</span><span></span></li>
              <li><span>2</span><span></span></li>
              <li><span>3</span><span></span></li>
              <li><span>4</span><span></span></li>
              <li><span>5</span><span></span></li>
              <li><span>6</span><span></span></li>
              <li><span>7</span><span></span></li>
              <li><span>8</span><span></span></li>
              <li><span>9</span><span></span></li>
              <li><span>10</span><span></span></li>
            </ul>
          </div>
          <div class="box">box2-{{key}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";
import BlogSearchArticleItem from "@/views/blogSearch/childComps/BlogSearchArticleItem";

export default {
  components: {
    TopBar,
    BlogSearchArticleItem
  },
  data() {
    return {
      key: this.$store.state.searchKey,
      blogSearchList: [],
    };
  },
  methods: {
    // 搜索博客
    searchBlogs() {
      this.$axios.get("/blog/search?key=" + this.key).then((res) => {
        console.log("加载后再次点击搜索",res);
        this.blogSearchList = res.data.data;
      });
    },
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #f2f2f2;
}

/* 搜索框开始 */
.so-toolbar {
  display: block;
  width: 100%;
  background: #fff;
  box-shadow: 0 4px 14px 0 rgb(0 0 0 / 5%);
  padding: 8px 0;
}
.so-toolbar .toolbar-main {
  width: 1382px;
  /* background-color: pink; */
  margin: 0 auto;
}
.so-toolbar .toolbar-main .search-box {
  width: 1034px;
  display: flex;
  background-color: palegreen;
}
.so-toolbar .toolbar-main .search-box input {
  flex: 1;
  line-height: inherit;
  padding: 8px 30px 8px 0;
  outline: 0;
  color: #000;
  font-size: 14px;
  text-indent: 16px;
  vertical-align: top;
  text-overflow: ellipsis;
  white-space: nowrap;
  border: 1px solid rgba(0, 0, 0, 0.06);
  border-radius: 2px 0 0 2px;
  background: #f5f6f7;
}
.so-toolbar .toolbar-main .search-box button {
  float: right;
  width: 120px;
  height: 44px;
  border: 0 none;
  background-color: #fc5531;
  color: #ffffff;
  border-radius: 0 2px 2px 0;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.2s ease-in;
}
/* 搜索框结束 */

.main {
  width: 1382px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding-top: 10px;
}

/* 左边博客开始 */
.main-lt {
  width: 1034px;
}
/*item*/

/* 左边博客结束 */

/* 右边推荐开始 */
.main-rt .box {
  width: 338px;
  /*height: 100px;*/
  /*float: right;*/
  background-color: #ffffff;
  margin-bottom: 10px;
  padding: 16px;
}
/* 右边推荐结束 */
</style>
