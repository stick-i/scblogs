<template>
    <div class="body">
        <div class="ContentA">
            <!-- 顶部导航栏部分 -->
            <TopBarA></TopBarA>
            <!-- 中间部分内容区域 -->
            <div class="centerContent">
                <div class="contentMiddle">
                    <div class="contentleft">
                        <ul class="contentleftA">
                            <!-- :style="{'background-color': (index2+1) ==item[0].rightChoice[0] ? '#1cbe29': ''}" -->
                            <li :style="{'background-color': item.chose ? 'rgb(240,240,245)': ''}"
                                v-for="(item,index) in leftNavigation" @click="ChoseModel(index)" :key="index">
                                <a href="#">{{item.name}}</a>
                             </li>
                        </ul>
                        <div class="contentleftB">
                            <div class="contentleftB-1">
                                    <div class="contentleftB-1-a">
                                        对我们的服务满意吗？
                                    </div>
                                    <div class="contentleftB-1-b">
                                        <ul>
                                            <li>
                                                <i class="iconfont "></i><span>非常不满意</span>
                                            </li>
                                            <li>
                                                <i></i><span>不满意</span>
                                            </li>
                                            <li>
                                                <i></i><span>一般</span>
                                             </li>
                                            <li>
                                                <i></i><span>满意</span>
                                            </li>
                                            <li>
                                                <i></i><span>非常满意</span>
                                             </li>
                                        </ul>
                                    </div>
                            </div>
                        </div>
                    </div>

                    <div ref="tabs1" v-show="leftNavigation[0].chose" class="contentright">
                        <TabsContent></TabsContent>
                        <!-- && content1Seens -->
                    </div>
                    <div ref="tabs2" v-show="leftNavigation[1].chose" class="contentright">
                        <TabsContent2></TabsContent2>
                    </div>
                    <div ref="tabs5" v-show="leftNavigation[4].chose" class="contentright">
                        <TabsContent5></TabsContent5>
                    </div>
                    <div ref="tabs8" v-show="leftNavigation[7].chose" class="contentright">
                        <TabsContent8></TabsContent8>
                    </div>
                </div>
            </div>
            <!-- 页面底部视图 -->
            <ButtomView></ButtomView>
        </div>

    </div>
</template>
<script>
import TopBarA from "@/components/content/topbar/TopBar";
import ButtomView from "@/components/P_user/ButtomView/ButtomView.vue"
import TabsContent from "@/components/P_user/PMELeftTabs/lefttabs.vue";
import TabsContent2 from "@/components/P_user/PMELeftTabs/lefttabs2.vue"
import TabsContent5 from "@/components/P_user/PMELeftTabs/lefttabs5.vue"
import TabsContent8 from "@/components/P_user/PMELeftTabs/lefttabs8.vue"
export default{
    components:{
        TopBarA,
        ButtomView,
        TabsContent,
        TabsContent2,
        TabsContent5,
        TabsContent8,
    },
    data(){
        return {
            leftNavigation:[{name:"个人资料",chose:true},{name:"账号设置",chose:false},
            {name:"隐私设置",chose:false},{name:"信息认证",chose:false}
            ,{name:"我的收藏",chose:false},{name:"浏览历史",chose:false},
            {name:"内容管理",chose:false},{name:"我的点赞",chose:false}],
            userMessage:{
                username:"默认参数P",
                nickname:"默认参数P",
                // 获取的个人头像照片地址
                avatarUrl:"https://profile.csdnimg.cn/2/8/8/1_qq_55817438",
                registerTime:""
            },
            config:{
                headers:{
                    'token':localStorage.getItem('token')
                }
            },
            // 用户数据加载完成之后显示
            content1Seen:false,
        }
    },
    async created() {
        await this.GetData()
        window.parentMounted = true	// _isMounted是当前实例mouned()是否执行 此时为true
        // 使得账号设置模块消失,模块选择可以变动
        this.$refs.tabs2.style.display="none"
    },
    beforeCreate(){
        window.parentMounted = this._isMounted  // _isMounted是当前实例mouned()是否执行 此时为false
    },
    methods:{
       async GetData(){
           await this.$axios.get('/user',this.config).then((res)=>{
                localStorage.setItem('userMessage',JSON.stringify(res.data.data))
            }).then(res=>{
                this.userMessage=JSON.parse(localStorage.getItem('userMessage'))
                this.content1Seen=true
            })
        },
        ChoseModel(index){
            // 选择模块
            for(let i=0;i<this.leftNavigation.length;i++){
                this.leftNavigation[i].chose=false
            }
            this.leftNavigation[index].chose=true

            if(index==6){
                this.$router.push('/ContentManagement')
            }
        }
    }
}
</script>

<style scoped>
*{
    padding: 0;
    margin:0;
}
body{
    padding: 0 !important;
    /* overflow:  hidden; */
}
.body{
    width: 100%;
    height: 100vh;
    background: rgb(243,244,246);
    display: flex;
    justify-content: center;
}
.ContentA{
    width: 1280px;
    height: 100%;
    /* display: flex; */
    /* justify-content: center; */
    /* background: #000; */
}
.centerContent{
    display: -webkit-box;
    display: -ms-flexbox;
    /* display: flex;
    justify-content: center;
     align-items: center; */
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    min-height: calc(100% - 200px);
    margin-bottom: 32px;
}
.contentMiddle{
    position: relative;
    width: 1280px;
    min-height: 800px;
    background: transparent;
    margin: 10px auto 0;
}
.contentleft{
    position: absolute;
    width: 200px;
    top: 0;
    left: 0;
    float: left;
    /* background: white; */
}
.contentleftA{
    list-style: none;
    width: 100%;
    background: white;
    border-radius: 5px;
}
.contentleftA li{
    width: 100%;
    height: 50px;
    line-height: 50px;
    /* padding: 20px; */
    text-align: center;
    margin : 5px 0;
}
.contentleftA li a{
    font-size:16px;
    color: black;
}
.contentleftA li:hover{
    background: rgb(240,240,245);
}
.contentleftB{
    width: 100%;

    padding-top: 15px;
    /* background: #000; */
}
.contentleftB .contentleftB-1{
    width:100%;
    background: white;
    padding-top: 20px;
    border-radius: 5px;
}
.contentleftB-1 .contentleftB-1-a{
    width: 100%;
    height: 40px;
    text-align: center;
    line-height: 40px;
}
.contentleftB-1 .contentleftB-1-b{
    width: 100%;
    margin-top:10px ;
    margin-bottom: 10px;
}
.contentleftB-1 .contentleftB-1-b ul {
    list-style: none;
}
.contentleftB-1 .contentleftB-1-b ul li{
    width: 100%;
    height: 60px;
    line-height: 60px;
    text-align: center;
    font-size: 16px;
    color: rgb(153,154,170);
}

.contentright{
    width: 83%;
    margin-left: 10px;
    min-height: 800px;
    position: absolute;
    top: 0;
    left: 200px;
    float: left;
}
</style>
