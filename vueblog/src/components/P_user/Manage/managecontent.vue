<template>
  <div class="managecontent">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="文章" name="first">
            <!-- <div class="searchResult">
            </div> -->
            <div class="firstmodel">
                <div class="screening-conditions">
                    <ul>
                        <li v-for="(item,index) in ScreenList" :key="index" @click="ChoseScreen(index)"
                        :style="{'color':item.chose?'black':''}">{{item.title}}</li>
                    </ul>
                </div>
                <div class="screening-conditionsB">
                    <div class="time-chose">
                        <!-- 月份选择 -->
                        <div class="chosemonth">
                            <el-date-picker
                            v-model="timechose"
                            type="month"
                            placeholder="选择月">
                            </el-date-picker>
                        </div>
                        <!-- 对博客列表进行搜索 -->
                        <div class="search">
                            <el-input placeholder="请输入内容" v-model="searchblog" class="input-with-select">
                                <el-button @click="SearchBlog" slot="append" icon="el-icon-search"></el-button>
                            </el-input>
                        </div>
                    </div>
                    <div ref="writeBlog" class="writeBlog">
                        <div>这里啥也没有啊</div>
                        <button @click="TurnToWriteBlog">写博客</button>
                    </div>
                    <div ref="noneSearch" class="nonesearch">
                        啥也没搜到！
                    </div>
                    <div ref="firstContent" class="show-content">
                        <div class="F-1" v-for="(item,index) in List"  :key="index" >
                            <div class="BlogContent-a">
                                <div @click="TurnToShow(item.id)" class="BlogContent-1">{{ item.title }}</div>
                                <div class="BlogContent-2">{{ item.description }}</div>
                                <div class="BlogContent-3">
                                    {{ item.releaseTime }}.
                                    <span>{{ item.viewNum }}阅读 .</span>
                                    <span>{{ item.likeNum }}点赞 .</span>
                                    <span>{{ item.commentNum }}评论 .</span>
                                    <span>{{ item.collectionNum }}收藏</span>
                                </div>
                            </div>
                            <div class="BlogContent-b">
                                <el-popover
                                    placement="top"
                                    width="160"
                                    v-model="visible2">
                                    <p>确定删除吗？</p>
                                    <div style="text-align: right; margin: 0">
                                    <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
                                    <el-button type="primary" size="mini" @click="visible2 = false">确定</el-button>
                                    </div>
                                    <el-button style="pointer-events: auto;" slot="reference">删除</el-button>
                                </el-popover>
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
                </div>
            </div>
        </el-tab-pane>
        <el-tab-pane label="下载" name="second">

        </el-tab-pane>
        <el-tab-pane label="问答" name="third">

        </el-tab-pane>
        <el-tab-pane label="视频" name="fourth">

        </el-tab-pane>
      </el-tabs>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading'
export default {
    name:"ManageContent",
    components: {
      InfiniteLoading
    },
    data(){
        return{
            // status选择部分列表
            ScreenList:[{title:"状态"},{title:"全部",chose:true},{title:"全部可见",chose:false},{title:"仅我可见",chose:false},{title:"审核",chose:false},{title:"草稿箱",chose:false},{title:"回收站",chose:false}],
            // 显示全部的列表
            allList:[],
            // 头部导航选择的部分
             activeName: 'first',
            //  时间选择
             timechose:"",
            // 显示遍历的列表
            List: [],
            // 博客页数
            page:0,
            // 搜索博客
            searchblog:"",
            config:{
                params:{status:"0",page:"0"},
                headers:{
                    'token':localStorage.getItem('token')
                }
            },
            visible2:false
        }
    },
    async mounted(){
        // 获取全部用户博客数据显示各种状态下的数据
    //    await this.GetData()
    this.$refs.noneSearch.style.display="none"
    },
    // watch:{
    //     config:{
    //         handler(newName, oldName) {
    //             console.log('注意此时的config对象变化了',this.config);
    //             },
    //             immediate: true
    //     }
    // },
    methods:{
       async GetData(){
            this.List=[]
           await this.$axios.get("/blog-console/blog-list",this.config).then(res=>{
                this.allList=res.data.data.records
                // this.page=res.data.data.pages
                this.List=this.List.concat(this.allList)
                // 将传参的页数标记增大1
                this.config.params.page+1
                if(this.List.length>0){
                    this.$refs.writeBlog.style.display="none"
                }
            })
        },
        // 手动选择部分
        handleClick(tab, event) {
        console.log(tab, event);
        },
    //   滑动触底时调用
    async infiniteHandler($state) {
        await this.$axios
          .get("/blog-console/blog-list",this.config)
          .then((res) => {
              if(res.data.data.records.length>0) {
              this.config.params.page +=1;  // 下一页
              this.allList = this.allList.concat(res.data.data.records);
              this.List=this.allList
              $state.loaded();
            }else {
                $state.complete();
            }
          })
           if(this.List.length>0){
                this.$refs.writeBlog.style.display="none"
                this.$refs.noneSearch.style.display="none"
            }
        console.log("此时所有的博客列表是",this.List)
      },
    //   查找关键字博客列表
    SearchBlog(){
        this.$axios.get("/blog/search",{params:{
            key:this.searchblog,
        }}).then(res=>{
            console.log("搜索返回的数据是",res.data)
            if(res.data.data.records.length==0){
                // 显示啥都没搜到
                this.$refs.firstContent.style.display="none"
                this.$refs.noneSearch.style.display="block"
            }else{
                this.$refs.firstContent.style.display="block"
                this.$refs.noneSearch.style.display="none"
                this.List=res.data.records
            }
        })
    },
    // 跳转至博客具体内容列表
    TurnToShow(index){
        var routeUrl= this.$router.resolve({name:'BlogDetail',params:{blogId:index}})
        window.open(routeUrl.href, '_blank');
    },
    // 点击后跳转至编写博客页面
    TurnToWriteBlog(){
        this.$router.push('/blog/add')
    },
    // 选择限制条件
    ChoseScreen(index){
        if(index!=0){
            for(let i=1;i<this.ScreenList.length;i++){
                        this.ScreenList[i].chose=false
                    }
                this.ScreenList[index].chose=true
                this.config.params.status=(index-1).toString()
                this.config.params.page=1
                this.GetData()//所有博客
            }
        }
    }
}
</script>

<style scoped>
.managecontent{
    width:calc(100%- 40px);
    height: calc(100%- 40px);
    margin: 20px;
}
/* 顶部筛选条件 */
.screening-conditions{
    width:100%;
    height: 50px;
}
.screening-conditions ul{
    width: 100%;
    height: 100%;
    list-style: none;
}
.screening-conditions ul li{
    width:13%;
    height: 100%;
    float: left;
    font-size: 16px;
    color: rgb(136, 137, 138);
}
.time-chose{
    width: 100%;
    height: 60px;
    border-bottom:2px solid rgb(217, 217, 217);
}
.time-chose .chosemonth{
    width: 30%;
    height: 100%;
    float: left;
}
.time-chose .search{
    width: 40%;
    height: 100%;
    float: right;

}
.screening-conditionsB{
    width: 100%;
    /* height: 500px; */

}
.screening-conditionsB .writeBlog{
    width: 100%;
    height: 500px;
    text-align: center;
    /* display: flex;
    align-items: center;
    justify-content: center; */
}
.screening-conditionsB .nonesearch{
    width: 100%;
    height: 500px;
    font-size: 20px;
    font-weight: 800;
    color: red;
    text-align: center;
    /* display: flex;
    align-items: center;
    justify-content: center; */
}
.screening-conditionsB .writeBlog div{
    width: 100%;
    height: 20%;
    font-size: 20px;
    font-weight: 800;
    position: relative;
    top: 30%;
    transform: translateY(-50%);
}
.screening-conditionsB .writeBlog button{
    width: 150px;
    height: 50px;
    border: none;
    background: rgb(242, 88, 28);
    color: white;
    border-radius: 20px;
    font-size: 20px;
    font-weight: 600;
    position: relative;
    top: 20%;
    transform: translateY(-50%);
}
.screening-conditionsB .show-content{
    width: 100%;
    height: 100%;
}
.screening-conditionsB .show-content .F-1{
    width: 100%;
    height: 30%;
}
.BlogContent-a {
  width: 80%;
  height: 100%;
  display: inline-block;
  padding: 10px 0;
  border-bottom: 1px solid #b7b8bb;
}
.BlogContent-b {
  width: 20%;
  height: 100%;
  display: inline-block;
  /* border-bottom: 1px solid #b7b8bb; */
}
.BlogContent-1 {
  width: 100%;
  height: 30%;
  font-size: 22px;
  font-weight: 600;
  color: black;
}
.BlogContent-1:hover {
  color: rgb(252, 85, 49);
}
.BlogContent-2 {
  width: 100%;
  height: 20%;
  margin: 10px 0;
  font-size: 16px;
  font-weight: 600;
  color: #555666;
}
.BlogContent-3 {
  width: 100%;
  height: 20%;
  font-size: 16px;
  font-weight: 600;
  color: #555666;
}
.BlogContent-3 span {
  margin: 0 5px;
}
</style>
