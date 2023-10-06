<template>
  <div class="body">
    <div class="content">
        <div class="mytext">
            我的粉丝
        </div>
		<div v-if="nofanstip"  class="nofans">你还没有粉丝哦，可以试试写一些有意义的文章</div>

        <div class="attention" v-for="(item) in FansLists" :key="item.followId">
            <div class="img">
                <img :src="item.avatarUrl" alt="">
            </div>
            <div class="introduce">
                <div class="nickname">{{item.nickname}}</div>
                <div class="">关注时间:{{item.createTime}}</div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    name:'Fans',
    props:{
		userId: {
            require:true,
            default: 0,
            type:Number,
        },
	},
    data(){
        return {
            // 关注列表
            FansLists:[],
            config:{
                params:{
                    userId: this.userId,
                    page:0
                },
                headers:{
                    'token':localStorage.getItem('token')
                }
            },
			nofanstip:false
        }
    },
    created(){
        this.GetData()
    },
    methods:{
        GetData(){
            this.$axios.get('/user/fans',this.config).then(res=>{
                console.log("获取所有粉丝列表",res)
				if(res.data.status){
					if(res.data.data.total!==0) {
						this.nofanstip=false
						this.FansLists = this.FansLists.concat(res.data.data.records)
						this.config.params.page++
					}else{
						this.nofanstip=true
					}
				}else{
					this.$message({
						message:"获取粉丝数据失败",
						type:"error"
					})
				}
            })
        }
    }
}
</script>


<style scoped lang="less">
.body{
    width: 100%;
    min-height: 200px;
    background: linear-gradient(45deg, rgb(255, 126, 99), rgb(210, 149, 253));
    border-radius: 10px;
    padding: 15px 20px;
}
.content{
    width: 100%;
    min-height:100%;
	.mytext{
		display: inline-block;
		font-size: 24px;
		font-weight: 600;
		color: white;
		letter-spacing:10px;
	}
	.nofans{
		margin: 20px;
		font-size: 2rem;
		font-weight: 600;
		text-align: center;
		color: black;
	}
}
.attention{
    width: 100%;
    height: 100px;
    padding: 10px 10px;
    display: flex;
    align-items: center;
    margin: 10px auto;
    border: 2px solid white;
    border-radius: 5px;
    transition:all 0.2s;
    &:hover{
        transform: scale(1.02);
    }
}
.attention .img{
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
}
.attention .img img{
    width: 100%;
		height: 100%;
}
.attention .introduce{
    height: 100%;
    margin-left: 20px;
    display: flex;
    flex-direction: column;
}
.attention .nickname{
    font-size: 20px;
    font-weight: 400;
    color: white;
    margin: 11px 0;
}
</style>
