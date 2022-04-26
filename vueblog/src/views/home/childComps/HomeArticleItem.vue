<template>
  <div>
    <div class="article-main" v-for="(item,index) in blogList" :key="index">
      <div class="article-item">
        <div class="article-title">
          <a href="#">
            <span>
              <router-link :to="{name:'BlogDetail',params:{blogId:item.id}}" target="_blank">
                {{item.title}}
              </router-link>
            </span>
          </a>
        </div>
        <div class="article-content-item">
          <div class="article-img-left">
            <a target="_blank" href="#">
              <img src="../../../assets/img/home/003.jpg" alt="">
            </a>
          </div>
          <div class="article-content-right">
            <a target="_blank" href="#">
              <div class="article-content">
                {{item.description}}
              </div>
            </a>
            <div class="article-evaluation">
              <div class="article-good">
                <img @click="isShowGoodIcon=false" v-if="isShowGoodIcon" src="../../../assets/img/home/good.png" alt="">
                <img @click="isShowGoodIcon=true" v-if="!isShowGoodIcon" src="../../../assets/img/home/good_active.png" alt="">
                {{ item.likeNum }} <span>赞</span>
              </div>
              <div class="article-author">作者：<span>{{item.author}}</span></div>
              <div class="article-date">发布时间：<span>{{ item.releaseTime }}</span></div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--infinite-loading这个组件要放在列表的底部，滚动的盒子里面-->
    <infinite-loading
      spinner="spiral"
      @infinite="infiniteHandler"
      :distance="200"
      class="infinite-loading-wrap"
    >
      <div slot="spinner">Loading...</div>
      <div slot="no-more">No more Data</div>
      <div slot="no-results">No results Data</div>
      <div slot="error" slot-scope="{ trigger }">
        Error Data, click <a href="javascript:;" @click="trigger">here</a> toretry
      </div>
    </infinite-loading>

  </div>
</template>

<script>
  import qs from "qs";

  import InfiniteLoading from 'vue-infinite-loading'

  export default {
    name: "",
    data(){
      return{
        page:1,
        blogList:[],
        isShowGoodIcon:true,
      }
    },
    components: {
      InfiniteLoading
    },
    mounted() {
      // this.goApi();
      // this.$axios
      //   .get("/blog/list")
      //   .then((res) => {
      //     console.log(res.data.data);
      //     this.blogList = res.data.data
      //   })
    },
    methods:{
      // async goApi() {
      //   this.$axios
      //     .get("/blog/list?page="+this.page)
      //     .then((res) => {
      //       console.log(res.data.data);
      //       this.blogList.push(...res.data.data); // 追加数据
      //     })
      // },
      async infiniteHandler($state) {
        this.$axios
          .get("/blog/list?page="+this.page)
          .then((res) => {
            if(res.data.data.length) {
              this.page +=1;  // 下一页
              this.blogList = this.blogList.concat(res.data.data);
              console.log(this.blogList)
              $state.loaded();
            }else {
              $state.complete();
            }
          })
      },
    }
  }
</script>

<style scoped>
    .article-main {
        border-radius: 5px;
        margin-right: 15px;
        border-bottom: 1px solid #f0f0f2;
        /*padding: 15px 10px 0;*/
        padding: 15px 0px 0;
    }

    .article-main:hover {
        background-color: #fafafa;
    }


    .article-title a {
        margin-bottom: 10px;
        font-size: 18px;
        font-weight: 700;
        color: #222226;
        overflow: hidden;
        white-space: normal;
        word-break: break-word;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        line-height: 25px;
    }
    .article-title a span:hover {
        /*color: pink;*/
        text-decoration: underline;
    }

    .article-img-left {
        margin-right: 16px;
    }
    .article-img-left a {
        position: relative;
        border-radius: 2px;
        width: 134px;
        height: 84px;
        display: inline-block;
        background-size: cover;
        background-position: 50%;
        border: 1px solid #f5f6f7;
        overflow: hidden;
        background: #f5f6f7;
    }

    .article-img-left img {
        /*width: 132px;*/
        /*height: 100px;*/
        width: 100%;
        left: 50%;
        top: 50%;
        -webkit-transform: translate(-50%,-50%);
        transform: translate(-50%,-50%);
        height: auto;
        position: absolute;
    }

    /*图片文章并排显示*/
    .article-content-item {
        display: flex;
        padding-bottom: 15px;
    }
    .article-content-right {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        /*margin-top: 4px;*/
        font-size: 14px;
        font-weight: 400;
        min-height: 6px;
        min-height: 40px;
        color: #555666;
    }
    .article-content-right .article-content {
        color: #555666;
        font-size: 14px;
        font-weight: 400;
        line-height: 22px;
    }

    /*评价*/
    .article-evaluation {
        display: flex;
        align-items: center;
    }
    .article-evaluation img {
        width: 16px;
        height: 16px;
        margin-right: 3px;
    }
    .article-evaluation .article-good {
        display: flex;
        align-items: center;
        margin-right: 30px;
    }
    .article-evaluation .article-author {
        margin-right: 30px;
    }
</style>
