<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <!-- 搜索框 -->
      <div class="search-toolbar">
        <div class="so-toolbar">
          <div class="toolbar-main">
            <div class="search-box">
              <input type="text" placeholder="vue的常用指令" />
              <button id="search">搜索</button>
            </div>
          </div>
        </div>
      </div>
      <!-- 搜索展示 -->
      <div class="main">
        <div class="main-lt">
          <div class="list-container">
            <!-- 文章item -->
            <div class="list-item" v-for="(item, index) in 10" :key="index">
              <div class="item">
                <!-- 标题 -->
                <div class="item-hd">
                  <h3 class="title">
                    <a target="_blank" href="#">早睡早起身体好</a>
                  </h3>
                </div>
                <!-- 摘要、浏览、图片 -->
                <div class="item-bd">
                  <div class="item-bd_cont">
                    <div class="bdc-lt">
                      <!-- 摘要 -->
                      <p class="row1">饭后走一走活到九十九</p>
                      <!-- 浏览 -->
                      <div class="row2">
                        <div class="row2-lt">
                          <div>浏览量</div>
                          <div>点赞量</div>
                          <div>评论量</div>
                        </div>
                        <div class="row2-rt">
                          <div>作者</div>
                          <span>发布时间</span>
                        </div>
                      </div>
                    </div>
                    <div class="bgc-rt"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="main-rt">{{key}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";

export default {
  components: {
    TopBar,
  },
  data(){
    return{
      key:'',
    }
  },
  mounted() {
    this.bus.$on('content',(value)=>{
      this.key = value;
      console.log(this.key)
    })
    // console.log(this.key)
  },
  methods:{
    // 搜索博客
    searchBlogs() {
      this.$axios.get("/blog/search?key="+this.key).then(res => {
        console.log(res)
      })
    },
  }
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
  /* outline: 0; */
  border: 0 none;
  background: url(data:image/png;base64,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)
    no-repeat 50%;
  background-size: 120px 50px;
  border-radius: 0 2px 2px 0;
  font-size: 0;
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
/* item开始 */
.list-item {
  border-bottom: 1px solid #ededed;
  background: #fff;
}
.list-item .item {
  padding: 16px 24px;
}
.item .item-hd {
  width: 100%;
  line-height: 22px;
  margin-bottom: 4px;
}
.item-hd .title {
  font-size: 18px;
  vertical-align: top;
}
.item-hd .title a {
  color: #222226;
}
.item-hd .title a:hover {
  color: #fc5531;
}
.item-bd .item-bd_cont {
  display: flex;
  align-items: flex-start;
  flex-shrink: 0;
  color: #555666;
  line-height: 24px;
  overflow-x: auto;
}
.item-bd .item-bd_cont .bdc-lt {
  min-height: 74px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.item-bd .item-bd_cont .bdc-lt .row1 {
  color: #999aaa;
}
.item-bd .item-bd_cont .bdc-lt .row2 {
  margin-top: 8px;
  display: flex;
  height: 20px;
  justify-content: space-between;
  align-items: center;
  color: #999aaa;
  line-height: 16px;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-lt {
  display: flex;
}
.item-bd .item-bd_cont .bdc-lt .row2 .row2-rt {
  display: flex;
}
/* item结束 */

/* 左边博客结束 */

/* 右边推荐 */
.main-rt {
  width: 338px;
  height: 100px;
  /*float: right;*/
  background-color: red;
}
</style>
