<template>
  <div class="contain">
        <div class="F-3">
                <!-- 表白墙的部分 -->
                <div class="F-3-content">
                    <!-- 预留动画部分 -->
                    <div class="publishlove">
                        <a @click="Publish">我要发布</a>
                    </div>
                    <!-- 表白墙正文显示区域 -->
                    <div class="heartsay" v-for="item in 6">
                        <div class="heartA">
                            <div class="img">
                                <img :src="avatarUrl" alt="">
                            </div>
                            <div class="username">
                                {{username}}
                            </div>
                        </div>
                        <div class="heartB">
                            <!-- 表白墙动态内容部分 -->
                        </div>
                        <div class="heartC">
                            <ul>
                                <li v-for="(item,index) in iconList">
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
  </div>
</template>

<script>
export default {
        data(){
            return {
                avatarUrl:'',
                username:'',
                iconList:['#icon-dianzan_kuai','#icon-pinglun','#icon-zhuanfa','#icon-gengduo'],
                likeactive:false
            }
        },
        methods:{
            Publish(){
                var routeUrl= this.$router.resolve({path:'./DynamicEdit'})
                window.open(routeUrl.href, '_blank');
            },
            // 点赞函数
            Like(index){
                if(index==0){
                    this.likeactive=!this.likeactive
                }
                if(this.likeactive){
                    // 执行点赞接口
                }else{
                    // 执行取消点赞接口
                }
            }
        }
}
</script>

<style scoped>
.contain{
    /* margin-top: 200px; */
}
.F-3{
    width: 100%;
    min-height: 200px;
    border-radius: 5px;
    /* display: flex; */
    background: linear-gradient(to right,pink,rgb(251, 205, 213),pink);
}
.F-3 .F-3-content{
    width: 100%;
    /* padding-top:px; */
}
.F-3 .F-3-content .publishlove{
    width: 100%;
    line-height: 100px;
    text-align:right;
    font-size: 20px;
    font-weight: 600;
    padding-right: 15%;
    color: rgb(64, 189, 251);
}
.F-3 .F-3-content .publishlove a{
    cursor: pointer;
}
.F-3 .F-3-content .heartsay{
    width: 100%;
    min-height: 200px;
    border-bottom: 1px solid white;
} 
.F-3 .F-3-content .heartsay .heartA{
    width: 100%;
    height: 100px;
    padding-left:20px ;
    display: flex;
    align-items: center;

} 
.F-3 .F-3-content .heartsay .heartA .img{
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
    border: 2px solid rgb(205, 205, 205,0.5);
    /* display: flex; */
    text-align: center;
} 
.F-3 .F-3-content .heartsay .heartA .img img{
    width: 100%;
    height: 100%;
} 
.F-3 .F-3-content .heartsay .heartA .username{
    line-height: 100%;
    text-align: center;
    font-size: 20px;
    font-weight: 600;
    margin-left: 20px;
} 
.F-3 .F-3-content .heartsay .heartB{
    width: 95%;
    min-height: 200px;
    margin: 10px auto;
    border-radius: 5px;
    background: rgb(241, 234, 234,0.6);
    border-color: white;
    transition: all 0.3s;
} 
.F-3 .F-3-content .heartsay .heartB:hover{
    border: 4px solid rgba(255, 255, 255);
} 
.F-3 .F-3-content .heartsay .heartC{
    width: 100%;
} 
.F-3 .F-3-content .heartsay .heartC ul{
    width: 100%;
    height: 60px;
    display: flex;
} 
.F-3 .F-3-content .heartsay .heartC ul li{
    width: 25%;
    height: 100%;
    text-align: center;
} 
.F-3 .F-3-content .heartsay .heartC ul li svg{
    width: 50%;
    height: 50%;
    margin-top: 10px;
    fill: rgb(255, 255, 255);
    transition: all 0.2s linear;
    cursor: pointer;
} 
.F-3 .F-3-content .heartsay .heartC ul li svg:hover{
    transform: scale(1.3) ;
    fill: rgb(51, 239, 236);
}
.F-3 .F-3-content .heartsay .heartC ul li svg:active{
    transform: scale(0.8) ;
    fill: rgb(238, 90, 90);
}
.F-3 .F-3-content .heartsay .heartC ul li:nth-child(1) svg.active{
    fill:rgb(51, 239, 236);
} 
</style>