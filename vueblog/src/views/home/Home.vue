<template>
  <div class="container">
    <top-bar class="top-bar"></top-bar>
    <!--    <router-link to="/blog/add"><a>点击</a></router-link>-->
    <div class="top-content">
      <!-- navbar -->
      <div class="top-navbar">
        <div class="nav-content">
          <ul>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">6</a></li>
            <li><a href="#">7</a></li>
            <li><a href="#">8</a></li>
            <li><a href="#">9</a></li>
            <li><a href="#">10</a></li>
            <li><a href="#">11</a></li>
            <li><a href="#">12</a></li>
            <li><a href="#">13</a></li>
            <li><a href="#">14</a></li>
            <li><a href="#">15</a></li>
            <li><a href="#">16</a></li>
            <li><a href="#">17</a></li>
            <li><a href="#">18</a></li>
            <li><a href="#">19</a></li>
            <li><a href="#">20</a></li>
            <li><a href="#">21</a></li>
            <li><a href="#">22</a></li>
            <li><a href="#">23</a></li>
            <li><a href="#">24</a></li>
            <li><a href="#">25</a></li>
            <li><a href="#">26</a></li>
            <li><a href="#">27</a></li>
            <li><a href="#">28</a></li>
            <li><a href="#">29</a></li>
            <li><a href="#">30</a></li>
            <li><a href="#">31</a></li>
            <li><a href="#">32</a></li>
            <li><a href="#">33</a></li>
            <li><a href="#">34</a></li>
            <li><a href="#">35</a></li>
            <li><a href="#">36</a></li>
            <li><a href="#">37</a></li>
            <li><a href="#">38</a></li>
            <li><a href="#">39</a></li>
            <li><a href="#">40</a></li>
          </ul>
        </div>
      </div>

      <!--  -->
      <div class="main">
        <!-- 左边头条 -->
        <div class="main-left">
          <!-- 图片展示 -->
          <div class="main-left-left">
            <div class="main-left-top">
              <img src="../../assets/img/home/toutiao.png" alt="头条" />
              <span>头条</span>
            </div>
            <div class="main-left-center">
              <img src="../../assets/img/home/003.jpg" alt="" />
            </div>
            <div class="main-left-bottom">
              <span class="left-title">好好学习天天向上</span>
              <span class="left-span">早睡早起身体好</span>
            </div>
          </div>
          <!-- 头条item展示 -->
          <div class="main-left-right1">
            <div class="headswiper">
              <div class="headswiper-top">头条左右箭头</div>
              <div class="headswiper-content">
                <div class="headswiper-item item-first">
                  <a target="_blank" href="#" class="item-title"
                    >饭后走一走活到九十九饭后走一走活到九十九</a
                  >
                  <a target="_blank" href="#" class="item-name">活到九十九</a>
                </div>
                <div class="headswiper-item" v-for="item in 4">
                  <a target="_blank" href="#" class="item-title"
                    >饭后走一走活到九十九</a
                  >
                  <a target="_blank" href="#" class="item-name">活到九十九</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 中间热点 -->
        <div class="main-center">
          <!-- 热点item展示 -->
          <div class="main-left-right2">
            <div class="main-left-top">
              <img src="../../assets/img/home/redian.png" alt="头条" />
              <span>热点</span>
            </div>

            <div class="headswiper hotswiper">
              <!--              <div class="headswiper-top">热点左右箭头</div>-->
              <div class="headswiper-content">
                <div class="headswiper-item item-first">
                  <a target="_blank" href="#" class="item-title"
                    >饭后走一走活到九十九</a
                  >
                  <a target="_blank" href="#" class="item-name">活到九十九</a>
                </div>
                <div class="headswiper-item" v-for="item in 4">
                  <a target="_blank" href="#" class="item-title"
                    >饭后走一走活到九十九</a
                  >
                  <a target="_blank" href="#" class="item-name">活到九十九</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 右边轮播图 -->
        <Swiper />
      </div>
    </div>
    <!--<swiper></swiper>-->

    <div class="content">
      <!-- 左边-->
      <div class="article-left">
        <!-- tabs标签-->
        <div class="tabs">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="关注" name="first">
              <HomeArticleItem />
            </el-tab-pane>
            <el-tab-pane label="推荐" name="second">
              <HomeArticleItem />
            </el-tab-pane>
            <el-tab-pane label="资讯" name="third">资讯</el-tab-pane>
            <el-tab-pane label="热榜" name="fourth">热榜</el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <!-- 右边-->
      <div class="article-right">
        <!--        卡片1-->
        <div class="template">
          <div class="template-title">
            <span>作者推荐</span>
          </div>
          <div class="template-cont">
            <div class="recommendation" v-for="item in 5">
              <a target="_blank" href="#" class="recommendation-img">
                <img src="../../assets/img/home/default_avatar.jpg" alt="" />
              </a>
              <div class="recommendation-cont">
                <div><a target="_blank" href="#">大哥</a></div>
                <div class="intro">此人很懒，什么都没有写</div>
              </div>
              <div class="recommendation-btn">关注</div>
            </div>
          </div>
        </div>
        <!--        卡片2-->
        <div class="template">
          <div class="template-title">
            <span>热门推荐</span>
          </div>
          <div class="template-cont">
            <div class="hot" v-for="item in 5">
              <a target="_blank" href="#" class="hot-img">
                <img src="../../assets/img/home/003.jpg" alt="" />
              </a>
              <div class="hot-cont">
                <a target="_blank" href="#">
                  <div>【JS】求斐波那契数列中的奇数之和</div>
                </a>
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
import HomeArticleItem from "@/views/home/childComps/HomeArticleItem";

export default {
  name: "Home",
  components: {
    Swiper,
    TopBar,
    HomeArticleItem,
  },
  data() {
    return {
      activeName: "second",
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
};
</script>

<style>
.top-content {
  width: 100%;
  background-color: hsla(0, 0%, 98%, 0.8);
  padding-top: 10px;
  /*background-color: #fc5531;*/
}

/* navbar开始 */
.top-navbar {
  /* display: block; */
  background-color: #ffffff;
  width: 1380px;
  margin: 0 auto;
  box-shadow: 0 4px 30px 0 rgb(232 232 237 / 50%);
  /* background-color: greenyellow; */
}
.nav-content ul li {
  display: inline-block;
  cursor: pointer;
  margin-bottom: 16px;
  height: 24px;
  margin-right: 31px;
}
.nav-content ul li a {
  font-size: 15px;
  color: #555666;
}

/* navbar结束 */

/* 头条开始 */
.main-left {
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
  /* 保持原有尺寸比例。保证替换内容尺寸一定大于容器尺寸，宽度和高度至少有一个和容器一致。因此，此参数可能会让替换内容（如图片）部分区域不可见。 */
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

/* item */
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
  /* background-color: pink; */
}
.headswiper-item {
  margin-top: 24px;
}
.headswiper-item .item-title {
  display: block;
  font-size: 16px;
  font-weight: 700;
  color: #222226;
  /*显示省略号*/
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
}

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
  /*height: 300px;*/
  border: 1px solid #f0f0f2;
  border-radius: 5px;
  margin-bottom: 16px;
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
.recommendation recommendation-img {
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
  border-radius: 12px;
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
