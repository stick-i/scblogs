<template>
    <div class="body">
        <meta name="referrer" content="no-referrer">
        <!-- 个人博客界面显示 -->
        <!-- 顶部导航栏部分 -->
        <div class="header">
            <div class="header-content">
                <!-- 内容区 -->
                <div class="header-element">
                    <!-- 元素区 -->
                    <div class="header-left">
                        <!-- 左边图片 -->
                        <img :src="symbolImg" alt="">
                        <!-- 左侧元素块 -->
                        <ul >
                            <li><a href="#">博客</a></li>
                            <li><a href="#">开发者文库</a></li>
                            <li><a href="#">课程</a></li>
                            <li><a href="#">问答</a></li>
                            <li><a href="#">社区</a></li>
                            <li><a href="#">插件</a></li>
                            <li><a href="#">认证</a></li>
                            <li><a href="#">开源</a></li>
                        </ul>
                    </div>
                    <!-- 中部搜索框 -->
                    <div class="header-middle">
                        <form action="">
                            <input v-model="inputValue"  type="text">
                            <button class="submit"><span class="iconfont icon-sousuo">提交</span></button>
                        </form>
                    </div>
                    <!-- 右边元素块 -->
                    <div class="header-right">
                        <ul >
                            <li class="vip"><a href="#">会员中心<img :src="showImg" alt=""></a></li>
                            <li><a href="#">足迹</a></li>
                            <li><a href="#">动态</a></li>
                            <li><a href="#">消息</a></li>
                        </ul>
                    </div>
                </div>    
            </div>
        </div>
    </div>
</template>

<script>
    export default{
        name:"TopBarA",
        props:{
            symbolImg:{
                type:String,
                required:false,
                default:"https://img-home.csdnimg.cn/images/20201124032511.png"
            },
            showImg:{
                type:String,
                required:false,
                default:"https://img-home.csdnimg.cn/images/20210918025138.gif"
            }
        },
        data(){
            return{
                inputValue:"",
                symbolImg:"",
                showImg:"",
            }
        },
        components: {},
        methods:{
            
        }
    }
    
</script>

<style >
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
/* 导航栏顶部固定 */
/* .header{
    position: fixed;
    top: 0;
} */
/* .a2{
    width: 100px;
    height: 6000px;
    background: rgb(232, 46, 46);
} */

.body{
    background: rgb(243, 244, 246);
}
.header{
    height: 60px;
    width: 100%;
    background: rgb(255,255,255);
    box-shadow: 0 2px 4px 0 rgb(0 0 0 / 5%);
}
.header-content{
    width: 1280px;
    height: 100%;
    margin: 0 auto;
}
.header-content li{
    list-style-type: none;
    float: left;
    width: auto;
    height: 100%;
    padding:0 10px;
    background-color: white;
} 
.header-content li:hover{
    background-color: rgb(240,240,245);
} 

.header-element{
    color: black;
    line-height: 60px;
}
.header-element a{
    color: black;
    font-size: 14px;
    text-decoration: none;
}

.header-left ul,img{
    float: left;
}
.header-left img{
    display: block;
    width: 80px;
    height: 44px;
    min-width: 80px;
    margin: 8px 5px 0 0;
}
.header-middle{
    float: left;
    max-width: 720px;
    height: 32px;
    line-height: 32px;
    margin-top:14px;
    position: relative;
}
.header-middle input{
    display: inline-block;
    box-sizing: border-box;
    /* 行内元素垂直居中 */
    vertical-align: top;
    font-size: 14px;
    width: 400px;
    height: 32px;
    border: 1px solid #e8e8e8;
    /* 右侧边界消失 */
    border-right: none;
    border-radius: 16px 0 0 16px;
    background: #f5f6f7;
    color: rgb(3, 3, 3);
}
.header-middle input.active input{
    border-color: rgb(255,80,27);
}
.header-middle button{
    display: inline-block;
    box-sizing: border-box;
    height: 32px;
    width: 88px;
    border: none;
    background-color: rgb(255, 80, 27);
    text-align: left;
    cursor: pointer;
    border-radius: 0 16px 16px 0;
}
.header-middle button span{
    color: white;
    margin: 15px;
}
.header-right ul{
    float: right;
}
.header-right .vip{
    position: relative;
}
.header-right a{
    display: block;
    box-sizing: border-box;
}
.header-right img{
    position:relative;
    width: 14px;
    top: 18.21px;
    margin-right: 5px;
    vertical-align: middle;
    display: inline-block;
}

</style>
