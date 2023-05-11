<template>
  <div>
       <div class="F-2">
            <!-- 个人动态显示 -->
            <div class="F-2-content">
                <div class="F-2-A"><!--头部展示-->
                    <div class="A-1">
                        我的动态
                    </div>
                    <div @click="TurnToDynamicEdit"
						 class="A-2">
                        +发布动态
                    </div>
                    <div class="A-3">
                        <span>允许他人查看</span>
                        <div class="switch">
                            <el-form ref="form" :model="form" >
                                    <el-switch v-model="allowview"></el-switch>
                            </el-form>
                        </div>
                    </div>
                </div>
                <div v-for="(item,index) in DynamicList" :key="item.id" class="F-2-B"><!--列表展示-->
                    <!-- 头部头像条 -->
<!--                    <div class="headurl">-->
<!--                        <div class="img">-->
<!--                            <img :src="avatarUrl" alt="">-->
<!--                        </div>-->
<!--                        <div class="username">-->
<!--                            {{username}}-->
<!--                        </div>-->
<!--                    </div>-->
                    <!-- 中部内容部分 -->
                    <div class="textcontent">
                        <div class="text">
                            <!-- 用户动态展示列表文字内容部分 -->
                            {{item.content}}
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
				<div v-if="nodynamictip" class="nodynamic">当前你还没有动态发布过哦！</div>
            </div>
        </div>
  </div>
</template>

<script>
export default {
    name:'Dynamic',
    props:{
		userId: {
            require:true,
            default: 0,
            type:Number,
        },
	},
    data(){
        return{
            // 是否允许他人查看
            DynamicList: [],
            allowview:false,
            iconList:['#icon-dianzan_kuai','#icon-pinglun','#icon-zhuanfa','#icon-gengduo'],
            // 点赞的激活状态
            likeactive:false,
			config:{
				params:{
                    userId: this.userId,
					page:0,
				},
				headers:{
					token:localStorage.getItem('token')
				}
			},
			nodynamictip:false
        }
    },
    created(){
        // 获取用户动态
        this.GetData()
    },
    methods:{
        GetData(){
            // 获取用户动态接口
            this.$axios.get('/blink/list/self',this.config).then(res=>{
				console.log("拿到的个人动态列表数据",res)
				if(res.status){
					if(res.data.data.total==0){
						this.nodynamictip=true
					}else{
						this.nodynamictip=false
                        console.log(res.data.data.records);
						this.DynamicList=this.DynamicList.concat(res.data.data.records)
						this.config.params.page++
					}
				}else{
					this.$message.error({
						message:"获取动态信息失败",
						type:"error"
					})
				}
			})
        },
        Like(index){
            if(index==0){
                this.likeactive=!this.likeactive
                if(this.likeactive){
                    // 执行点赞接口
                }
            }
        },
	//	跳转至编辑动态页面
		TurnToDynamicEdit(){
			var routeUrl= this.$router.resolve({path:'./DynamicEdit'})
			window.open(routeUrl.href, '_blank');
		}
    }
}
</script>

<style scoped lang="less">
/* 动态模块部分 */
.F-2{
    width: 100%;
    border-radius: 5px;
    display: flex;
    background: rgb(255, 255, 255);
}
.F-2 .F-2-content{
    width: 100%;
    padding: 0 15px 15px 15px;
	.nodynamic{
		width: 100%;
		font-size: 2rem;
		font-weight: 600;
		color: black;
		text-align: center;
		margin: 30px auto;
	}
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
