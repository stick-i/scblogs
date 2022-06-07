<template>
    <div class="content">
        <div class="myLike">
            <div class="myLikeB">我赞过的</div>
            <div v-if="noneLike" class="noneLike">
                你都舍不得点赞吗？
            </div>
            <div v-if="!noneLike" class="myLikeA" v-for="(item,index) in likeList" :key="item.id" >
                <div class="myLikeTitle" @click="TurnToDetail(item.id)">{{item.title}}</div>
                <div class="myLikeStar">
					{{item.thumbs}}
					<svg class="icon"
						 :class="{'active':item.thumbs}"
						 @click="Like(item.id,item.authorId,index)"
						 aria-hidden="true">
						<use xlink:href="#icon-dianzan_kuai"></use>
						<!-- use是复制一个图标的意思 -->
					</svg>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name:"MyThumbs_up",
    data(){
        return {
			likeList: [],
			config: {
				headers: {
					'token': localStorage.getItem('token')
				}
			},
			// 后端没有数据返回时显示此部分
			noneLike: false,
			// fff
		}
    },
    mounted(){
        this.GetData()
    },
    methods:{
        GetData(){
            this.$axios.get("/blog/action/like",this.config).then(res=>{
                console.log("点赞列表返回数据",res)
                if(res.data.data.records!=null){
                    if(res.data.data.records.length>0){
                        this.likeList=res.data.data.records
                    }else{
                        // this.$refs.noneLike.style.display="block"
                        this.noneLike=true
                    }
                }else{
                        this.noneLike=true
                }
                //为所有数据添加字段状态thumbs
                this.AddStation()
				console.log("添加字段状态thumbs后",this.likeList)
            })
        },
        //添加收藏列表的初始状态true
        AddStation(){
            this.likeList.forEach(item=>{
                item.thumbs=true
            })
        },
        ChangeStar(index){
            this.likeList[index].starStation=!this.likeList[index].starStation
            // 开始收藏状态修正
            this.Like(index)
        },
        Like(blogid,authorId,index){
			let formdata = new FormData()

			formdata.append("blogId", blogid)
			formdata.append("id",authorId)

			this.$axios.post('/blog/action/like',formdata,this.config).then(res=>{
					if(res.status){
						this.likeList[index].thumbs=!this.likeList[index].thumbs
						this.$message({
							message: '操作失败',
							type:'success',
						});
					}else {
						this.$message({
							message: '操作失败',
							type: 'error'
						});
					}
                })
        }
    }
}
</script>

<style scoped lang="less">
.content{
    width: 100%;
    background: linear-gradient(to top, rgb(230, 113, 186), #FF834FFF);
    border-radius: 5px;
	padding: 10px 20px;
}
.myLike{

}
.myLike .noneLike{
    width:100%;
    height: 500px;
    display:block;
    font-size: 36px;
    font-weight: bold;
    line-height: 500px;
    text-align: center;
    color: #9dffe4;
}
.myLike .myLikeB{
    width: 100%;
    height: 100px;
    line-height: 100px;
    font-size: 28px;
    font-weight: 800;
    color: #fff7ec;
}
.myLikeA{
    width: 100%;
    min-height: 100px;
    border: 1px solid rgb(213, 211, 211);
    border-radius: 10px;
    display: flex;
    align-items: center;
    margin-bottom: 20px;
	display: flex;
	justify-content: space-between;
	transition: all 0.2s ease-in;
}
.myLikeA:hover{
	transform: scale(1.02);
    box-shadow:  0 4px 8px 0 rgba(0, 0, 0, 0.2);
}
.myLikeA .myLikeTitle{
    font-size: 20px;
    margin-left: 40px;
    font-weight: 600;
    color: rgb(56, 52, 52);
    display: inline-block;
	cursor: pointer;
}
.myLikeA .myLikeStar{
    width: 10%;
    height: 100%;
    margin-left: 40px;
    text-align: center;
}
.myLikeA .myLikeStar svg{
	width: 30px;
	height: 30px;
	fill: white;
		&.active{
			fill:red;
		}
}



</style>
