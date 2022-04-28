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
                            <li :style="{'background-color': item.chose==true ? 'rgb(240,240,245)': ''}"
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
                    <div class="contentright">
                        <TabsContent v-if="leftNavigation[0].chose"></TabsContent>
                    </div>
                    <!-- <div class="contentright">
                        <TabsContent2 v-if="leftNavigation[1].chose"></TabsContent2>
                    </div> -->
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
export default{
    components:{
        TopBarA,
        ButtomView,
        TabsContent,
        TabsContent2
    },
    data(){
        return {
            leftNavigation:[{name:"个人资料",chose:true},{name:"账号设置",chose:false},
            {name:"隐私设置",chose:false},{name:"信息认证",chose:false}
            ,{name:"我的收藏",chose:false},{name:"浏览历史",chose:false},
            {name:"内容管理",chose:false}],
            userMessage:{
                username:"勇敢牛牛不怕困难",
                nickname:"超级飞侠",   
                // 获取的个人头像照片地址
                avatarUrl:"https://profile.csdnimg.cn/2/8/8/1_qq_55817438",
                registerTime:""
            },
            config:{
                headers:{
                    'token':localStorage.getItem('token')
                }
            }
        }
    },
    async mounted() {
        await this.GetData()
        window.parentMounted = this._isMounted	// _isMounted是当前实例mouned()是否执行 此时为true
    },
    beforeMount(){
        window.parentMounted = this._isMounted
    },
    methods:{
        GetData(){
            this.$axios.get('/user',this.config).then((res)=>{
                console.log("接口返回的用户公开信息数据",res.data)
                localStorage.setItem('userMessage',JSON.stringify(res.data.data))
            }).then(res=>{
                this.userMessage=JSON.parse(localStorage.getItem('userMessage'))
                console.log("我的本地数据",this.userMessage)
            }).then(res=>{
                this.userMessage=JSON.parse(localStorage.getItem('userMessage'))
                console.log("获取个人信息页面得到的个人信息",this.userMessage)
            })
        },
        ChoseModel(index){
            // 选择模块
            console.log("模块选择成功")
            for(let i=0;i<this.leftNavigation.length;i++){
                this.leftNavigation[i].chose=false
            }
            this.leftNavigation[index].chose=true
            // this.$refs.upload.submit();
            if(index==6){
                this.$router.push('/ContentManagement')
            }
        }
    }
}
</script>

<style >
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
    width: auto;
    width: 1280px;
    min-height: 800px;
    background: transparent;
    margin: 10px auto 0;
}
.contentleft{
    position: absolute;
    width: 200px;
    top: 0;
    left: 0px;
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
