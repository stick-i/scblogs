<template>
  <div class="body">
    <!-- 用户登录界面左右变换 -->
    <div class="container">
      <!-- 注册页面 -->
      <div class="form-container sign-up-container">
        <form action="#">
          <h2>Sign Up</h2>
          <!-- 小图标显示 -->
          <div class="social-container">
            <a href="#" class="social"><i class="el-icon-grape"></i></a>
            <a href="#" class="social"><i class="el-icon-lollipop"></i></a>
            <a href="#" class="social"><i class="el-icon-apple"></i></a>
          </div>
          <input type="text" v-model="form.name" placeholder="Username...">
          <input type="mail"  v-model="form.mail" placeholder="E-mail...">
          <input type="password" v-model="form.password" placeholder="Password...">
          <button  type="submit">SIGN UP</button>
        </form>
      </div>

      <!-- 登录页面 -->
      <div class="form-container sign-in-container">
        <form action="#">
          <h2>Sign In</h2>
          <!-- 小图标显示 -->
          <div class="social-container">
            <a href="#" class="social"><i class="el-icon-grape"></i></a>
            <a href="#" class="social"><i class="el-icon-lollipop"></i></a>
            <a href="#" class="social"><i class="el-icon-apple"></i></a>
          </div>
          <input type="text" v-model="form.name" placeholder="Username...">
          <input type="password" v-model="form.password" placeholder="Password...">
          <a href="" class="forget">forget you password!</a>
          <button  type="submit">SIGN IN</button>
        </form>
      </div>

      <!-- 覆盖容器 -->
      <div class="overlay-container">
        <div class="container2">
          <!-- 左边覆盖 -->
          <div class="left-container overlay-panel">
            <h2>Welcome Back!</h2>
            <p>To keep connected with us please login with your personal info</p>
            <button type="submit" id="sign-in" class="">SIGN IN </button>
          </div>
          <!-- 右边覆盖 -->
          <div class="right-container overlay-panel">
            <h2>Hello Friend!</h2>
            <p>Into your personal details and start journey with us</p>
            <button type="submit" id="sign-up" class="">SIGN UP </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
*{
  padding: 0;
  margin:0;
  box-sizing: border-box;
}
.body{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: rgb(250, 252, 252);
}
h2{
  font-size: 36px;
  text-transform: capitalize;
}
a{
  display: inline-block;
  height: 20px;
  text-decoration: none;
  color: #bbb;
  text-transform: capitalize;
  font-size: 14px;
}
a:hover{
  /* 当鼠标覆盖时出现链接变化 */
  border-bottom: 2px solid rgb(181, 109, 2);
  color: rgb(139, 135, 135);
}
button{
  text-transform: uppercase;
  background: rgb(240, 83, 39);
  border-radius: 20px;
  border: 1px solid white;
  padding: 10px 20px;
  margin-top: 10px;
  color: white;
  /* 清除下方下划线 */
  outline:none;
  /* 变换时间 */
  transition: transform 80ms;
}
/* 当button的变换被激活之后 */
button:active{
  transform: scale(.9);
}
.container{
  position: relative;
  height: 480px;
  width: 768px;
  background: rgb(255, 255, 255);
  box-shadow:0 15px 15px rgb(94, 109, 109);
  border-radius: 10px;
  overflow: hidden;
}
.form-container{
  position: absolute;
  top: 0;
  width: 50%;
  height: 100%;
  background-color: rgb(255, 255, 255);
  /* ease-in 的详细解释-->  https://blog.csdn.net/u011143548/article/details/116756769 */
  transition: all .60s ease-in;
}
.sign-up-container{
  z-index: 1;
}
.sign-in-container{
  z-index: 2;

}
.form-container form{
  display: flex;
  height: 100%;
  width: 100%;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 0 50px;
}
.form-container input{
  margin: 6px 8px;
  padding: 16px;
  width: 100%;
  background-color: aliceblue;
  border: none;
}
.social-container{
  margin: 8px 0;
}
.social-container a{
  display: inline-block;
  width: 30px;
  height: 30px;
  margin:0 3px;
  text-align: center;
  line-height: 30px;
  color: black;
  border: 1px solid black;
  border-radius: 50%;
}
.social-container a:hover{
  color: white;
  background-color: black;
  border-color:aliceblue;
}
.overlay-container{
  position: absolute;
  width: 50%;
  height: 100%;
  top: 0;
  /* 将覆盖部分的层级设置最高，并且超出部分隐藏 */
  z-index: 100;
  /* 父元素遮罩层靠右 */
  right:0;
  overflow: hidden;
  transition:all .6s ease-in-out;
}
/* 这是父级元素通过遮罩显示的内容部分 */
.container2{
  position:absolute;
  /* 覆盖层的宽度应该占全部，取200%是因为父级元素只有一半 */
  width: 200%;
  height: 100%;
  /* left: -100%; */
  background-color: rgb(49, 176, 245);
  /* background: ; */
}
.overlay-panel{
  position: absolute;
  display: flex;
  flex-direction: column;
  width: 50%;
  height: 100%;
  justify-content: center;
  align-items: center;
  /* 文字颜色设置 左右间距 文字居中*/
  color:rgb(255, 255, 255);
  padding: 0 40px;
  text-align: center;
}
.overlay-panel button{
  /* 将按键的背景值设置为透明 */
  background-color: transparent;
  border: 1px solid white ;
}
.overlay-panel p{
  /*语句文字大小，上下间距 */
  font-size: 14px;
  margin: 10px 0 20px;
}
.left-container{
  right: 0;
}

/* 当container被激活时产生的CSS变换 */
.container.active .sign-up-container{
  /* 沿着X轴方向平移自身宽度 */
  z-index: 5;
  transform: translateX(100%);
}
.container.active .sign-in-container{
  /* 沿着X轴方向平移自身宽度 */
  transform: translateX(100%);
}
.container.active .overlay-container{
  /* 沿着X轴方向平移自身宽度 */
  transform: translateX(-100%);
}
.container.active .container2{
  transform: translateX(-50%);
}
</style>

<script >
window.onload=function(){
  // 通过JS部分对container添加激活的CSS变换类动作
  const container=document.getElementsByClassName('container')[0]
  const signIn=document.getElementById('sign-in')
  const signUp=document.getElementById('sign-up')
  console.log("获取节点成功获取到的节点是",container,signIn,signUp);
  console.log("获取DOM节点成功");
  signUp.onclick=function(){
    console.log("点击成功,登录页面样式为：",container.className)
    container.classList.add("active");
    console.log("登录页面的样式：",container.classList)
  }
  signIn.onclick=function(){
    console.log("点击成功,登录页面样式为：",container.className)
    container.classList.remove("active");
    console.log("登录页面的样式：",container.classList)
  }
}

export default {
  data() {
    return {
      form: {
        name: "",
        email: "",
        password: "",
      },
    }
  }
};
</script>