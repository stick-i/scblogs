<template>
<div >
  <el-tabs  v-model="activeName" @tab-click="handleClick">
    <!-- <div class="F-1"> -->
    <el-tab-pane label="最近" @scroll="scrollBottom($event)" name="1">
      <div  v-for="(item,index) in recentList"  @click="TurnToShow(item.id)">
        <div class="BlogContent-a">
          <div class="BlogContent-1">{{ item.title }}</div>
          <div class="BlogContent-2">{{ item.description }}</div>
          <div class="BlogContent-3">
            {{ item.releaseTime }}.
            <span>{{ item.viewNum }}阅读 .</span>
            <span>{{ item.likeNum }}点赞 .</span>
            <span>{{ item.commentNum }}评论 .</span>
            <span>{{ item.collectionNum }}收藏</span>
          </div>
        </div>
      </div>
      <!-- <infinite-loading
          spinner="spiral"
          @infinite="infiniteHandler"
          :distance="200"
          class="infinite-loading-wrap">
          <div slot="spinner">Loading...</div>
          <div slot="no-more">No more Data</div>
          <div slot="no-results">No results Data</div>
          <div slot="error" slot-scope="{ trigger }">
            Error Data, click <a href="javascript:;" @click="trigger">here</a> toretry
          </div>
        </infinite-loading> -->
    </el-tab-pane>

    <el-tab-pane label="文章" name="2">
      <el-tabs v-model="activeName2" @tab-click="handleClick">
        <el-tab-pane label="按最后发布时间" name="1">
            <div class="F-1" v-for="(item,index) in LastPublishTimeList"  @click="TurnToShow(item.id)" :key="index">
            <div class="BlogContent-a">
              <div class="BlogContent-1">{{ item.title }}</div>
              <div class="BlogContent-2">{{ item.description }}</div>
              <div class="BlogContent-3">
                {{ item.releaseTime }}.
                <span>{{ item.viewNum }}阅读 .</span>
                <span>{{ item.likeNum }}点赞 .</span>
                <span>{{ item.commentNum }}评论 .</span>
                <span>{{ item.collectionNum }}收藏</span>
              </div>
            </div>
            <!-- <infinite-loading
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
              </infinite-loading>  -->
          </div>
        </el-tab-pane>
        <el-tab-pane label="按访问量" name="2">
          <div class="F-1" v-for="(item,index) in maxViewList"  :key="index" @click="TurnToShow(item.id)">
            <div class="BlogContent-a">
              <div class="BlogContent-1">{{ item.title }}</div>
              <div class="BlogContent-2">{{ item.description }}</div>
              <div class="BlogContent-3">
                {{ item.releaseTime }}.
                <span>{{ item.viewNum }}阅读 .</span>
                <span>{{ item.likeNum }}点赞 .</span>
                <span>{{ item.commentNum }}评论 .</span>
                <span>{{ item.collectionNum }}收藏</span>
              </div>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="创作历程" name="3">创作历程</el-tab-pane>
      </el-tabs>
    </el-tab-pane>

    <el-tab-pane label="资源" name="3">资源</el-tab-pane>
    <el-tab-pane label="问答" name="4">
      <el-tabs v-model="activeName3" @tab-click="handleClick">
        <el-tab-pane label="回答的问题" name="1">回答的问题</el-tab-pane>
        <el-tab-pane label="发布的问题" name="2">发布的问题</el-tab-pane>
      </el-tabs>
    </el-tab-pane>

    <el-tab-pane label="帖子" name="5">
      <el-tabs v-model="activeName4" @tab-click="handleClick">
        <el-tab-pane label="回答的帖子" name="1">回答的帖子</el-tab-pane>
        <el-tab-pane label="发布的帖子" name="2">发布的帖子</el-tab-pane>
      </el-tabs>
    </el-tab-pane>

    <el-tab-pane label="视频" name="6">视频</el-tab-pane>
    <el-tab-pane label="课程" name="7">课程</el-tab-pane>

    <el-tab-pane label="关注/订阅/互动" name="8">
      <el-tabs v-model="activeName5" @tab-click="handleClick">
        <el-tab-pane label="关注的人" name="1">回答的帖子</el-tab-pane>
        <el-tab-pane label="粉丝" name="2">发布的帖子</el-tab-pane>
        <el-tab-pane label="关注的收藏夹" name="3">发布的帖子</el-tab-pane>
        <el-tab-pane label="关注的社区" name="4">发布的帖子</el-tab-pane>
        <el-tab-pane label="发布的评论" name="5">发布的帖子</el-tab-pane>
        <el-tab-pane label="收到的评论" name="6">发布的帖子</el-tab-pane>
        <el-tab-pane label="普通专栏" name="7">发布的帖子</el-tab-pane>
      </el-tabs>
    </el-tab-pane>
    <el-tab-pane label="收藏" name="9">定时任务补偿</el-tab-pane>
    <!-- </div> -->
  </el-tabs>
</div>
</template>
<script>
import qs from "qs";
import InfiniteLoading from 'vue-infinite-loading'
export default {
  components: {
      InfiniteLoading
    },
  data() {
    return {
      activeName: "1",
      //  最近获取到的列表
      recentList: [],

      activeName2: "1",
      //   最后发布时间
      LastPublishTimeList:[],
      //   最大访问量
      maxViewList: [],

      activeName3: "1",
      activeName4: "1",
      activeName5: "1",
      page: 1, // 根据接口定义的初始值
      loadflag: false, // 是否可以加载数据
      config:{
        headers:{
          'token':localStorage.getItem('token')
        }
      }
    };
  },
  async created() {
    await this.GetData();
  },
  methods: {
    infiniteHandler(){
      console.log("到底了开始触发了方法调用数据")
      this.page++
      this.$axios.get('/blog-console/blog-list',{
        params:{page:this.page},
        headers:{'token':localStorage.getItem('token')}})
          .then((res) => {
            console.log("下拉至底部返回的数据之前的",this.recentList)
            if(res.data.data.blogList.length) {
              let arr = res.data.data.bloglist;
              // this.blogList = this.blogList.concat(res.data.data);
              this.recentList = [...this.recentList,...arr]
              console.log(this.recentList)
              $state.loaded();
            }else {
              $state.complete();
            }
          })
    },
    TurnToShow(index){
      console.log("获取到的文章ID是",index)
        var routeUrl= this.$router.resolve({name:'BlogDetail',params:{blogId:index}})
        window.open(routeUrl.href, '_blank');
    },
    // 获取数据
    async GetData() {
      this.recentList = [];
    // 获取文章列表
      await this.$axios.get("/blog-console/blog-list",{headers:{'token':localStorage.getItem('token')}}).then((res) => {
        console.log("获取博客列表成功",res)
        this.recentList = this.recentList.concat(res.data.data.records);
        console.log("获取到的博客列表数据", this.recentList);
        this.DataChange();
      });
    },
    handleClick(tab, event) {
        console.log(tab, event)

      if (tab._uid == 13) {
        //  按照访问量大小对文章进行排序
        console.log("获取到的MAX列表",this.maxViewList)
        this.ViewNumSort()
        console.log("MAX 排序完成",this.maxViewList)
        this.LastPublishTimeSort()
        console.log("Last 排序完成",this.LastPublishTimeList)
      }
    },
    // 按照发布时间最晚对文章列表进行排序
    LastPublishTimeSort(){
      this.LastPublishTimeList=[]
         this.LastPublishTimeList = this.LastPublishTimeList.concat(this.recentList);
        for (let j = 0; j < this.LastPublishTimeList.length - 1; j++) {
          for (let i = 0; i < this.LastPublishTimeList.length - 1 - j; i++){
                var thisTime1=this.LastPublishTimeList[i].releaseTime;
                var thisTime2=this.LastPublishTimeList[i].releaseTime
                thisTime1 = thisTime1.replace(/-/g, '/');
                thisTime2 = thisTime2.replace(/-/g, '/');
                var time1 = new Date(thisTime1);
                var time2 = new Date(thisTime2);
            if (time1.getTime()- time2.getTime()<0) {
              let temp = this.LastPublishTimeList[i];
              this.LastPublishTimeList[i] = this.LastPublishTimeList[i + 1];
              this.LastPublishTimeList[i + 1] = temp;
            }
            }
        }
    },
    // 按照访问量对博客列表进行排序
    ViewNumSort(){
      this.maxViewList=[]
        this.maxViewList = this.maxViewList.concat(this.recentList);
        for (let j = 0; j < this.maxViewList.length - 1; j++) {
          for (let i = 0; i < this.maxViewList.length - 1 - j; i++)
            if (this.maxViewList[i].viewNum < this.maxViewList[i + 1].viewNum) {
              let temp = this.maxViewList[i];
              this.maxViewList[i] = this.maxViewList[i + 1];
              this.maxViewList[i + 1] = temp;
            }
        }
    },
    // 修改时间格式
    DataChange() {
      for (let i = 0; i < this.recentList.length; i++) {
        let time = this.recentList[i].releaseTime;
        this.recentList[i].releaseTime = this.timeFormat(time);
      }
    },
    timeFormat(time) {
      var d = new Date(time);
      //  当前时间
      var t = new Date();

      var year = d.getFullYear(); //年
      var month = d.getMonth() + 1; //月
      var day = d.getDate(); //日

      var hh = d.getHours(); //时
      var mm = d.getMinutes(); //分
      var ss = d.getSeconds(); //秒
      if (year == t.getFullYear() && month == t.getMonth() + 1) {

        if (t.getDate() - day == 2) {
          clock = "前天" + " ";
          if (hh >= 12) {
            clock += "上午" + " ";
          } else {
            clock += "下午" + " ";
          }
        } else if (t.getDate() - day == 1) {
          clock = "昨天" + " ";
          if (hh >= 12) {
            clock += "下午" + " ";
          } else {
            clock += "上午" + " ";
          }
        } else {
          var clock = year + "/";

          if (month < 10) clock += "0";

          clock += month + "/";

          if (day < 10) clock += "0";
          clock += day + " ";
        }
      }
       else {
          var clock = year + "/";

          if (month < 10) clock += "0";

          clock += month + "/";

          if (day < 10) clock += "0";
          clock += day + " ";
        }
      if (hh < 10) clock += "0";

      clock += hh + ":";
      if (mm < 10) clock += "0";
      clock += mm + ":";

      if (ss < 10) clock += "0";
      clock += ss;
      return clock;
    },
  },
};
</script>
<style scoped>
.F-1 {
  width: 100%;
  height: 200px;
  overflow-y:scroll;
  background: rgb(255, 255, 255);
  display: flex;
  justify-content: center;
  align-items: center;
}
/* .el-tabs__content{
  overflow-y: scroll !important;  
} */
.BlogContent-a {
  width: 90%;
  height: 100px;
  padding: 10px 0;
  border-bottom: 1px solid #b7b8bb;
}
.BlogContent-1 {
  width: 100%;
  height: 30%;
  font-size: 18px;
  color: black;
}
.BlogContent-1:hover {
  color: rgb(252, 85, 49);
}
.BlogContent-2 {
  width: 100%;
  height: 20%;
  margin: 10px 0;
  font-size: 14px;
  color: #555666;
}
.BlogContent-3 {
  width: 100%;
  height: 20%;
  font-size: 14px;
  color: #555666;
}
.BlogContent-3 span {
  margin: 0 5px;
}
</style>
