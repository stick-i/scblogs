<template>
<!-- 个人主页部分中部大导航区域 -->
    <div class="body">
        <div class="contentleft">
            <ul>
                <li v-for="(item,index) in navList" :key="index"
                    :style="{'background-color': item.chose ?'rgb(240, 240, 240)':'',
                    'color':item.chose?'rgb(61, 160, 0)':'black'}" @click="ChoseModel(index)">
                    {{item.name}}
                </li>
            </ul>
        </div>
        <div class="contentright">
            <div class="F-1"
            v-for="(item,index) in List"
            :key="index"
            @click="TurnToShow(item.id)"
            v-if="navList[0].chose">
                <div class="BlogContent-image">
                    <img :src="item">
                </div>
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
            <div class="F-2" v-if="navList[1].chose">
                <!-- 个人动态显示 -->
                <div class="F-2-content">
                    <div class="F-2-A"><!--头部展示-->
                        <div class="A-1">
                            我的动态
                        </div>
                        <div class="A-2">
                            +发布动态
                        </div>
                        <div class="A-3">
                            <span>允许他人查看</span>
                            <div class="switch">
                                <el-form ref="form" :model="form" >
                                    <el-form-item >
                                        <el-switch v-model="allowview"></el-switch>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </div>
                    <div class="F-2-B"><!--列表展示-->
                        <!-- 头部头像条 -->
                        <div class="headurl">
                            <div class="img">
                                <img :src="avatarUrl" alt="">
                            </div>
                            <div class="username">
                                {{username}}
                            </div>
                        </div>
                        <!-- 中部内容部分 -->
                        <div class="textcontent">
                            <div class="text">
                                <!-- 用户动态展示列表文字内容部分 -->
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                                的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的的是大大大大大大大大大大大大大大大大大
                            </div>
                        </div>
                        <!-- 底部点赞，评论，转发，删除 -->
                        <div class="clickfunction">
                            <ul>
                                <li v-for="(item,index) in iconList" :key="index">
                                    <svg class="icon"
                                        :class="{active:likeactive}"
                                        @click="Like(index)"
                                        aria-hidden="true">
                                        <use :xlink:href="item"></use>
                                        <!-- use是复制一个图标的意思 -->
                                    </svg>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 给下拉刷新预留位置 -->
            <infinite-loading
                spinner="spiral"
                @infinite="infiniteHandler"
                :distance="200"
                class="infinite-loading-wrap"
                >
                <div slot="spinner">别催，正在加载中，等会儿！</div>
                <div slot="no-more">别划了，到底了^_^</div>
                <div slot="no-results">看啥看，你啥都没写！</div>
                <div slot="error" slot-scope="{ trigger }">
                    Error Data, click <a href="javascript:;" @click="trigger">here</a> toretry
                </div>
            </infinite-loading>
        </div>
    </div>
</template>
<script>
//导入下拉至底部触发刷新的组件
import InfiniteLoading from 'vue-infinite-loading'
export default {
    name:"NewPersonBlogContent",
    components:{
        InfiniteLoading
    },
    props:{
        avatarUrl:{
            require:true,
            default:'https://img2.baidu.com/it/u=590821804,2188087390&fm=253&fmt=auto&app=138&f=JPEG?w=350&h=350',
            type:String
        },
        username:{
            require:true,
            default:'ABC',
            type:String
        }
    },
    data(){
        return{
            // 左侧导航条列表
            navList:[{name:"首页",chose:true},
            {name:"动态",chose:false},
            {name:"表白墙",chose:false},],
            //博客列表显示部分
            List:[],
            // 切换模块时数据获取的激活条件
            getmessageindex:0,
            config:{
                params:{status:"0",page:"0"},
                headers:{
                    'token':localStorage.getItem('token')
                }
            },
            // 是否允许他人查看
            allowview:false,
            iconList:['#icon-dianzan_kuai','#icon-pinglun','#icon-zhuanfa','#icon-gengduo'],
            // 点赞的激活状态
            likeactive:false,
        }
    },
    created(){
    },
    async mounted(){
        await this.GetData()
        console.log("获取到的博客列表数据",this.List)
        console.log("从父组件中获取到的数据",this.username,this.headurl)
    },
    methods:{
        Like(index){
            if(index==0){
                this.likeactive=!this.likeactive
                if(this.likeactive){
                    // 执行点赞接口
                }
            }
        },
        // 大中部导航的模块选择部分
        ChoseModel(index){
            this.navList.forEach(element => {
                element.chose=false
            });
            this.navList[index].chose=true
            this.getmessageindex=index
        },
        // 初始化获取数据
        async GetData(){
            this.List=[]
            await this.$axios.get("/blog-console/blog-list",this.config).then(res=>{
                this.List=res.data.data.records
                // 将传参的页数标记增大1
                this.config.params.page++
            })
        },
        // 底部刷新函数
        async infiniteHandler($state){
            // 个人博客列表数据获取
            if(this.getmessageindex==0){
                await this.$axios
                .get("/blog-console/blog-list",this.config)
                .then((res) => {
                    console.log("触底刷新之前的config数据是",this.config,res.data.data.records)
                    if(res.data.data.records.length>0) {
                    this.config.params.page ++;  // 下一页
                    this.List = this.List.concat(res.data.data.records);
                    console.log("触底刷新之后的config数据是",this.config)
                    $state.loaded();
                }else {
                    $state.complete();
                }
                })
            }else if(this.getmessageindex==1){
                // 个人动态页面数据获取
                this.$axios.get()
            }
        },
        // 跳转去显示博客的详情
        TurnToShow(id){
            var routeUrl= this.$router.resolve({name:'BlogDetail',params:{blogId:id}})
            window.open(routeUrl.href, '_blank');
        }
    }
}
</script>
<style scoped>
*{
    padding: 0;
    margin: 0;
}
.body{
    width: 100%;
    height: 100%;
    display: flex;
}
.contentleft{
    width: 280px;
    margin-right: 30px;
}

.contentleft li{
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: black;
    font-size: 18px;
    font-weight: 400;
    border-radius: 5px;
    background: white;
    transition: all .3s;
    margin-bottom: 5px;
}
.contentleft li:hover{
    background: rgb(240, 240, 240);
    transform: scale(1.2);
    color:black;
}
.contentleft li:active{
    background: rgb(38, 231, 4);
    transform: scale(0.9);
}
.contentright{
    width:990px;
    /* overflow: hidden; */
}
.contentright .F-1{
    width: 100%;
    height: 200px;
    margin-bottom:20px;
    border-radius: 5px;
    display: flex;
    background: white;
}
.contentright .F-1 .BlogContent-image{
    width: 200px;
    height: 150px;
    margin: auto 10px;
    border-radius: 10px;
    border: 3px solid grey;
}
.contentright .F-1 .BlogContent-a{
    /* width: ; */
    height: 150px;
    margin: auto 0;
}
.contentright .F-1 .BlogContent-a .BlogContent-1{
    /* width: 100%; */
    height: 60px;
    line-height: 60px;
    font-size: 28px;
    font-weight: 600;
    color: black;
}
.contentright .F-1 .BlogContent-a .BlogContent-2{
    /* width: 100%; */
    height: 40px;
    line-height: 40px;
    font-size: 20px;
    font-weight: 600;
    color: black;
}
.contentright .F-1 .BlogContent-a .BlogContent-3{
    /* width: 100%; */
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    font-weight: 400;
    color: black;
}
.contentright .F-1 .BlogContent-a .BlogContent-3 span{
    margin: 0 10px;
}

/* 动态模块部分 */
.F-2{
    width: 100%;
    border-radius: 5px;
    display: flex;
    background: rgb(255, 255, 255);
    border: 2px solid rgb(205, 205, 205)
}
.F-2 .F-2-content{
    width: 100%;
    padding: 0 15px 15px 15px;
}
/* 动态头部展示模块 */
.F-2 .F-2-content .F-2-A{
    width: 100%;
    height: 50px;
    display: flex;
}
.F-2 .F-2-content .F-2-A .A-1{
    width:150px;
    line-height: 50px;
    font-size: 20px;
    font-weight: 600;
    text-align: center;
    color: rgb(0, 0, 0);
}
.F-2 .F-2-content .F-2-A .A-2{
    width:100px;
    line-height: 50px;
    font-size: 16px;
    font-weight: 500;
    text-align: center;
    color: rgb(0, 160, 246);
    cursor: pointer;
}
.F-2 .F-2-content .F-2-A .A-3{
    width:400px;
    line-height: 50px;
    font-size: 16px;
    font-weight: 500;
    text-align: center;
    display: flex;
    margin-left: 100px;
    color: rgb(0, 0, 0);
}
.F-2 .F-2-content .F-2-A .A-3 div{
    display: flex;
    align-items: center;
    margin-left: 5px;
}
/* 动态列表部分展示模块 */
.F-2 .F-2-content .F-2-B{
    width: 100%;
    min-height: 200px;
    border: 1.5px solid rgb(183, 183, 183);
    border-radius: 10px;
}
/* 头像条 */
.F-2 .F-2-content .F-2-B .headurl{
    width: 100%;
    height: 70px;
    padding: 10px;
    display: flex;
}
.F-2 .F-2-content .F-2-B .headurl .img{
    width: 50px;
    height: 50px;
    overflow: hidden;
    /* border: 3px solid rgb(234, 234, 234); */
    border-radius: 50%;
    margin: auto 0;
}
.F-2 .F-2-content .F-2-B .headurl .img img{
    width: 100%;
}
.F-2 .F-2-content .F-2-B .headurl .username{
    width: 150px;
    margin: auto 20px;
    line-height: 50px;
    font-size: 20px;
    font-weight:600;
    color: rgb(79, 215, 239);
}
.F-2 .F-2-content .F-2-B  .textcontent{
    width: 900px;
    height: 200px;
    border-radius: 5px;
    background: rgb(245, 243, 243);
    margin: 0 auto 20px;
    color: black;
    font-size: 16px;
}
.F-2 .F-2-content .F-2-B  .clickfunction{
    width: 100%;
    height: 50px;
}
.F-2 .F-2-content .F-2-B  .clickfunction ul{
    display: flex;
}
.F-2 .F-2-content .F-2-B  .clickfunction li{
    width: 25%;
    height: 50px;
    text-align: center;
}
.F-2 .F-2-content .F-2-B  .clickfunction li svg{
    fill: rgb(118, 118, 118);
    transition: all .3s;
    cursor: pointer;
    width: 30px;
    height:30px;
}
.F-2 .F-2-content .F-2-B  .clickfunction li svg:hover{
    -webkit-transform:scale(1.3) translateY(-5px);
    fill: rgb(79, 224, 234);
}
.F-2 .F-2-content .F-2-B  .clickfunction li:nth-child(1) svg:hover{
    -webkit-transform:rotate(360deg) scale(1.3);
    fill: rgb(1, 221, 67);
}
.F-2 .F-2-content .F-2-B  .clickfunction li:nth-child(1) svg:active{
    transform:rotate(-360deg) scale(0.8);
    animation: rotation 3s linear infinite;
    fill: rgb(5, 245, 253);
}
/* 伪类表现激活后的样式 */
.F-2 .F-2-content .F-2-B  .clickfunction li:nth-child(1) svg.active{
    fill: rgb(5, 245, 253);
}
</style>
