<template>
<!-- 个人主页部分中部大导航区域 -->
    <div class="body">
        <div class="contentleft">
            <ul>
                <li v-for="(item,index) in navList" :key="index" @click="ChoseModel(index)"
                    :style="{'background-color': item.chose ?'rgb(240, 240, 240)':'',
                    'color':item.chose?'rgb(61, 160, 0)':'black'}">
                    {{item.name}}
                </li>
            </ul>
        </div>
        <div class="contentright">
            <div v-if="navList[0].chose">
                <home :userId="userId"></home>
            </div>
            <div v-if="navList[1].chose">
                <Dynamic :userId="userId"></Dynamic>
            </div>
            <div v-if="navList[2].chose">
                <MyAttention :userId="userId"></MyAttention>
            </div>
            <div v-if="navList[3].chose">
                <Fans :userId="userId"></Fans>
            </div>
        </div>
    </div>
</template>
<script>
//导入下拉至底部触发刷新的组件
import InfiniteLoading from 'vue-infinite-loading'
import Home from '@/components/P_user/NewPersonBlogContent/ChildrenPage/home.vue'
import Dynamic from '@/components/P_user/NewPersonBlogContent/ChildrenPage/Dynamic.vue'
import MyAttention from '@/components/P_user/NewPersonBlogContent/ChildrenPage/MyAttention.vue'
import Fans from '@/components/P_user/NewPersonBlogContent/ChildrenPage/Fans.vue'
export default {
    name:"NewPersonBlogContent",
    components:{
        InfiniteLoading,
        Home,
        Dynamic,
        MyAttention,
        Fans
    },
    props:{
        userId: {
            require:true,
            default: 0,
            type:Number,
        },
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
            navList:[{name:"博客",chose:true,},
            {name:"动态",chose:false},
            {name:"关注",chose:false},
            {name:"粉丝",chose:false},],
            // 切换模块时数据获取的激活条件
            getmessageindex:0,
            iconList:['#icon-dianzan_kuai','#icon-pinglun','#icon-zhuanfa','#icon-gengduo'],
        }
    },
    created(){
    },
    methods:{
        // 大中部导航的模块选择部分
        ChoseModel(index){
            console.log("进行了函数的执行")

            this.navList.forEach(element => {
                element.chose=false
            });
            console.log("数据列表",this.navList[index])
            this.navList[index].chose=true
            this.getmessageindex=index
        },
    },
    mounted() {
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
    font-size: 1rem;
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
</style>
