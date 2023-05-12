<template>
  <div class="container">
      <TopBar></TopBar>
        <!-- 将此页面分成上，中，下三个部分 -->
      <div class="main">
            <div class="up">
                <div class="decoration">
                    <div class="bgimage" v-for="item in bookList" :key="item.id">
                        <Book :text="item.text" :imgone="item.img"></Book>
                    </div>
                </div>
                <div class="my">
                    <div class="ke">
                        <div class="img">
                            <!-- 用户头像 -->
                            <img :src="userMessage.avatarUrl" alt="" />
                        </div>
                        <div class="name">
                            <!-- 用户姓名及后面的各种标志部分 -->
                            <div class="name_icon">
                                <div class="username">{{ userMessage.nickname }}</div>
                                <!-- 昵称边上的图标部分可以加在下面 -->
                            </div>
                            <!-- 用户个性签名部分-->
                            <div class="personwrite">
                                宁故中的扶手
                            </div>
                        </div>
                        <!-- 左部编辑，设置按钮 -->
                        <div v-if="localUserId === userMessage.id" class="button">
                            <button class="edit" @click="TurnToEdit()">编辑资料</button>
                            <button class="set">设置</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="middle">
                <!-- 中部导航部分使用封装的子组件NewPersonBlogContent-->
                <NewPersonBlogContent :userId="userMessage.id" :avatarUrl="userMessage.avatarUrl" :username="userMessage.nickname"/>
            </div>
<!--            <div class="down">-->
<!--                &lt;!&ndash; 底部信息 &ndash;&gt;-->
<!--            </div>-->
      </div>
  </div>
</template>

<script>
// 导入顶部导航条
import TopBar from "../components/content/topbar/TopBar.vue"
import qs from 'qs'
import Book from '@/components/P_user/CssAnimation/PageTurnBook.vue'
// 引入中部大导航
import NewPersonBlogContent from "@/components/P_user/NewPersonBlogContent/NewPersonBlogContent.vue"
export default {
    components: {
         TopBar,
         NewPersonBlogContent,
         Book
    },
    data() {
        return {
            userMessage:{},
            config:{
                headers:{
                    token: localStorage.getItem("token"),
                }
            },
            localUserId : localStorage.getItem("userMessage") == null ? 0 : JSON.parse(localStorage.getItem("userMessage")).id,
            // 书的信息
            bookList:[{
                text:'菩提本无树，明镜亦非台',
                img:'https://tse4-mm.cn.bing.net/th/id/OIP-C.LWGT942vEnu7BJUpYbAyogHaFP?w=213&h=180&c=7&r=0&o=5&dpr=1.12&pid=1.7',
                id:1
            },
            {
                text:'每天都有不一样的答案',
                // img:'',
                id:2
            },
            {
                text:'你想看啥',
                img:'https://img2.baidu.com/it/u=3496011114,654559464&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800',
                id:3
            },
            {
                text:'就知道你忍不住看',
                img:'https://img1.baidu.com/it/u=907523513,3087938631&fm=253&fmt=auto&app=138&f=JPEG?w=231&h=500',
                id:4
            },
            {
                text:'三万里河东入海，五千仞岳上摩天。',
                img:'https://img0.baidu.com/it/u=3872372553,3353954591&fm=253&fmt=auto&app=138&f=JPEG?w=485&h=500',
                id:5
            },
            {
                text:'昆仑山上玉楼前，五色祥光混紫烟。',
                img:'https://img1.baidu.com/it/u=1637045404,3074886103&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889',
                id:6
            },]
        };
    },
    created(){
        // console.log(this.$route.params.userId);
        // console.log(localStorage.getItem("token"));
        this.GetData();
    },
    methods:{
        async GetData() {
            this.$axios.get('/user', {params:{id:this.$route.params.userId},headers:{token: localStorage.getItem("token"),}}).then((res) => {
                // console.log(res.data);
                this.userMessage = res.data.data;
                // console.log(this.userMessage.id);
            } );

            // 获取用户个人信息
            //  console.log(JSON.parse(localStorage.getItem('userMessage')),"所有个人信息")
            },
            // 跳转至编辑个人信息页面
            TurnToEdit(){
                this.$router.push("/PersonalInfoEdit");
            }
    }
}
</script>

<style scoped>
.container{
    width: 100%;
    min-height: 100vh;
    background: rgb(247,248,249);
}
.main{
    width: 100%;
    padding-top: 2vh;
}
.up{
    width: 1300px;
    /* height: 200px; */
    margin:0 auto;
    position: relative;
    /* background: url('../assets/img/newPersonBlog/tip.webp') no-repeat; */
    background: linear-gradient(135deg,rgb(246, 167, 180),white,rgb(125, 224, 251));
    border-radius: 5px;
}
.up .decoration{
    width: 100%;
    top: 0;
    left: 0;
    display: flex;
    justify-content: space-around;
}
.up .bgimage{
    margin:20px 20px 20px 0;
    border: none;
}
.my{
    width: 100%;
    height: 100px;

}
/* 包裹上部分文字头像显示部分的壳 */
.ke{
    width: 100%;
    height: 100px;
    padding-left:20px;
    padding-bottom: 10px;
    display: flex;
    align-items: center;
}
/* 头像包裹部分 */
.img{
    width: 80px;
    height: 80px;
    overflow: hidden;
    border: 3px solid rgb(234, 234, 234);
    border-radius: 50%;
}
/* 头像本体 */
.img img{
    width: 100%;
		height: 100%;
}
/* 用户名字部分的区域 */
.name{
    width:60%;
    height: 100%;
    margin-left: 20px;
}
/* 昵称及其余图标部分 */
.name .name_icon{
    width: 100%;
    height: 35px;
    line-height: 40px;
    margin-top: 5px;
}
/* 用户昵称显示 */
.name .name_icon .username{
    font-size: 22px;
    font-weight: 800;
    color: white;
}
/* 个人签名部分 */
.name .personwrite{
    width: 100%;
    /* height: 40px; */
    margin-top: 10px;
    color:white
}
.ke .button{
    width: 20%;
    margin-left:50px;
}
.button button{
    height: 30px;
    border: none;
    border-radius: 5px;
    color: white;
    background: rgb(251, 88, 52);
    transition: all .5s
}
.button button:hover{
    background: rgb(251, 46, 0);
    box-shadow:0 2px 5px rgb(93, 93, 93);
}
/* 按钮被点击时 */
.button button:active{
    border:4px solid white;
    background: rgb(14, 199, 255);
}
.button .edit{
    width: 125px;
}

.button .set{
    margin-left: 20px;
    width: 100px;
}
/* 中部大导航部分 */
.middle{
    width: 1300px;
    margin:20px auto 0;
}
</style>
