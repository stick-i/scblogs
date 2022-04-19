<template>
    <div class="body">
        <meta name="referrer" content="no-referrer">
        <!-- 弹性导航条html+css -->
        <div class="content">
            <div class="navi-1">
                <div class="navi-1-up">
                    <ul>
                        <li @click="click">文章</li>
                        <li @click="click">资源</li>
                        <li @click="click">问答</li>
                        <li>帖子</li>
                        <li>视频</li>
                        <li>课程</li>
                        <li>更多</li>
                    </ul>
                    <div class="navi-1-up-search">
                        <i class="iconfont icon-sousuo"></i>
                    </div>
                </div>
                <div class="navi-1-down">
                    <ul>
                        <li>关注的人</li>
                        <li>粉丝</li>
                        <li>关注的收藏夹</li>
                        <li>关注的社区</li>
                        <li>普通专栏</li>
                    </ul>                    
                </div>
            </div>
            <img :src="naviImg" alt="">
        </div>
    </div>
</template>
<script>
export default {
    name:"navigationbar",
    props:{
            naviImg:{
                type:String,
                required:false,
                default:"https://img-home.csdnimg.cn/images/20210310101013.png"
            }
        },
    data(){
        return{
            // 导航部分的背景图片
            naviImg:"https://img-home.csdnimg.cn/images/20210310101013.png"
        }
    },
    methods:{
        // click(){
        //         this.
        // }
    }
}
</script>
<style scoped>
*{
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}
.content{
    width: 760px;
    height: 500px;
    background: none;
}
li{
    list-style: none;
}
img{
    display: none;
}
.navi-1{
    width: 100%;
    height: 100px;
    border-radius: 5px;
    background: white;
    box-shadow: 0 2px 4px 0 rgb(0 0 0 / 5%);
}
.navi-1-up{
    width: 100%;
    height: 50px;
    border-bottom:1px solid  rgb(243, 244, 246);
    display: flex;
    align-items: center;
}
.navi-1-up ul{
    width: 650px;
    height: 100%;
    margin-left: 20px;
}
.navi-1-up li{
    float: left;
    line-height: 50px;
    margin: 0 10px;
    font-size: 16px;
    color: rgb(74, 75, 75);
}
.navi-1-up .navi-1-up-search{
    float: right;
}
.navi-1-down{
    width: 100%;
    height: 50px;
    margin-bottom: 15px;
    display: flex;
    align-items: center;
}
.navi-1-down ul{
    width: 100%;
    height: 100%;
    margin-left: 20px;
}
.navi-1-down li{
    float: left;
    line-height: 50px;
    margin:0 10px;
    font-size: 16px;
    color: rgb(74, 75, 75);
}
</style>
