<template>
    <div class="body">
        <meta name="referrer" content="no-referrer">
        <!-- 弹性导航条html+css -->
        <div class="content">
            <div class="navi-1">
                <div class="navi-1-up">
                    <ul>
                        <li>文章</li>
                        <li>资源</li>
                        <li>问答</li>
                        <li>帖子</li>
                        <li>视频</li>
                        <li>课程</li>
                        <li>更多</li>
                    </ul>
                    <div class="navi-1-up-search">
                        <i class="iconfont icon-sousuo"></i>
                    </div>
                </div>
                <!-- <div class="navi-1-down">
                    <ul>
                        <li>关注的人</li>
                        <li>粉丝</li>
                        <li>关注的收藏夹</li>
                        <li>关注的社区</li>
                        <li>普通专栏</li>
                    </ul>                    
                </div> -->
                <div class="navi-1-down">
                    <ul>
                        <li
                        @click="Checked">按最后发布时间<img src="https://img-home.csdnimg.cn/images/20210127051652.png" alt=""></li>
                        <li>按访问量<img src="https://img-home.csdnimg.cn/images/20210127051654.png" alt=""></li>
                        <li>创作历程<i class="iconfont "></i></li>
                    </ul>                    
                </div>
            </div>
            <div class="navi-2">
                <div class="img">
                       <img :src="naviImg" alt="">
                </div>
            </div>
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
            naviImg:"https://img-home.csdnimg.cn/images/20210310101013.png",
            // 小图标颜色切换
            img_1:"https://img-home.csdnimg.cn/images/20210127051652.png",
            img_2:"https://img-home.csdnimg.cn/images/20210127051654.png"
        }
    },
    methods:{
        Checked(){
            console.log("点击触发了")
                .style.color="black"
        }
    }
}
window.onload=function(){
      var img_1="https://img-home.csdnimg.cn/images/20210127051652.png";
      var img_2="https://img-home.csdnimg.cn/images/20210127051654.png"
    var Lilist=document.querySelector(".navi-1-up").querySelectorAll('li')
    console.log("获取到的li标签列表",Lilist)
   
    for(let i=0;i<Lilist.length;i++){
        Lilist[i].addEventListener('click',function ChangeBottomColor(){
            console.log("获取到的标签属性",Lilist[i].style)
            // 清空被选中的属性
             for(let i=0;i<Lilist.length;i++){
                    Lilist[i].style.borderBottom="none"
                }
            Lilist[i].style.borderBottom="2px solid black"
        })
    }
    
    // navi-1-down下的ul下的li点击操作
    var down_ul=document.querySelector(".navi-1-down").querySelector("ul")
    console.log("获取元素成功",down_ul.children)
    for(let i=0;i<down_ul.children.length;i++){
            down_ul.children[i].addEventListener("click",function(){
                // 流量图标部分
                var down_Img=down_ul.querySelectorAll("img")
                for(let i=0;i<down_Img.length;i++){
                    down_Img[i].src=img_2
                }
                // 文字部分                
                for(let i=0;i<down_ul.children.length;i++){
                        down_ul.children[i].style.color="black"
                }
                down_ul.children[i].style.color='rgb(252,85,49)'
                down_ul.children[i].children[0].src=img_1
            })
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
    display: flex;
    position: relative;
}
li{
    list-style: none;
}

.img img{
    width: 200px;
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
    width: 25px;
    height: 25px;
    line-height: 25px;
    text-align: center;
    border-radius: 50%;
    color: rgb(85,85,85);
}
.navi-1-up .navi-1-up-search:hover{
    background: rgb(248,248,248);
    color: rgb(252,85,49);
}
.navi-1-up .navi-1-up-search .icon-sousuo{
    font-size: 20px;
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
    display: flex;
    align-items: center;
    float: left;
    line-height: 50px;
    margin:0 10px;
    font-size: 16px;
    color: rgb(74, 75, 75);
}
.navi-1-down li:first-child{
    color: rgb(253,108,78);
}
.navi-1-down li img{
    width: 20px;
    /* margin:15px 0; */
}
/* 内容显示区域 */
.navi-2{
    width: 100%;
    height: 79%;
    position: absolute;
    bottom: 0;
    border-radius: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
<style scoped>

</style>