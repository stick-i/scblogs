<template>
  <div class="container">
      <TopBar></TopBar>
        <!-- 将此页面分成上，中，下三个部分 -->
      <div class="main">
            <div class="up">
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
                        <div class="button">
                            <button class="edit" @click="TurnToEdit()">编辑资料</button>
                            <button class="set">设置</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="middle">
                <!-- 中部导航部分使用封装的子组件NewPersonBlogContent-->
                <NewPersonBlogContent></NewPersonBlogContent>
            </div>
            <div class="down">
                <!-- 底部信息 -->
            </div>
      </div>
  </div>
</template>

<script>
// 导入顶部导航条
import TopBar from "../components/content/topbar/TopBar.vue"
import qs from 'qs'
// 引入中部大导航
import NewPersonBlogContent from "@/components/P_user/NewPersonBlogContent/NewPersonBlogContent.vue"
export default {
    data() {
        return {
            userMessage:{},
            config:{
                headers:{
                    token: localStorage.getItem("token"),
                }
            }
        };
    },
    components: {
         TopBar,
         NewPersonBlogContent
    },
    mounted() {
    this.GetData();
    console.log("主页回去完成的数据",this.userMessage);
  },
    methods:{
        GetData() {
            let params = {
                username: "123",
                password: "123456",
            };
            this.$axios
                .post("/login/login", qs.stringify(params))
                .then((res) => {
                console.log("登录成功",res.data.data)
                // 将token设置到本地浏览器中
                window.localStorage.setItem("token", res.headers.token);
                window.localStorage.setItem(
                    "userMessage",
                    JSON.stringify(res.data.data)
                );
                this.userMessage = res.data.data;
                });
            // 获取用户个人信息
            this.$axios
                .get("/user", this.config)
                .then((res) => {
                localStorage.setItem("userMessage", JSON.stringify(res.data.data));
                this.userMessage = res.data.data
                })
            },
            // 跳转至编辑个人信息页面
            TurnToEdit(){
                this.$router.push("/PersonalMessageEdit");
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
}
.up{
    width: 1300px;
    height: 200px;
    margin:0 auto;
    background: url('../assets/img/newPersonBlog/tip.webp');
}
.my{
    width: 100%;
    height: 100px;
    padding:100px 0 0;
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
