<template>
  <div class="managecontent">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="文章" name="first">
            <div class="firstmodel">
                <div class="screening-conditions">
                    <ul>
                        <li v-for="(item,index) in ScreenList" :key="index" @click="ChoseScreen(index)"
                        :style="{'color':item.chose==true?'black':''}">{{item.title}}</li>
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
                    <div class="writeBlog">
                        <div>这里啥也没有啊</div>
                        <button @click="TurnToWriteBlog">写博客</button>
                    </div>
                    <div class="show-content">
                        <div class="F-1" v-for="(item,index) in List"  :key="index" @click="TurnToShow(item.id)">
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
export default {
    name:"ManageContent",
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
            List: this.allList,
            // 搜索博客
            searchblog:"",
            config:{
                headers:{
                    'token':localStorage.getItem('token',{
                        params:{
                            status:""
                        }
                    })
                }
            }
        }
    },
    created(){
        // 获取全部用户博客数据显示各种状态下的数据
        this.GetData()
    },
    methods:{
        GetData(){
            console.log("开始调用")
            this.$axios.get("/blog-console/blog-list",this.config).then(res=>{
                this.allList=res.data.data.blogList
            })
        },
        // 手动选择部分
        handleClick(tab, event) {
        console.log(tab, event);
      },
    //   查找关键字博客列表
    SearchBlog(){
        this.$axios.get("/blog/search",{params:{
            key:this.searchblog,
        }}).then(res=>{
            console.log("搜索博客返回的数据",res)
        })
    },
    // 跳转至博客具体内容列表
    TurnToShow(index){
      console.log("获取到的文章ID是",index)
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
            }
        }
    }
}
</script>

<style>
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
    height: 500px;
    text-align: center;
}
.screening-conditionsB .writeBlog{
    width: 100%;
    height: 100%;
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
</style>
