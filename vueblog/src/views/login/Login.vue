<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <div class="register-login-box">
        <login-item></login-item>
      </div>
    </div>

  </div>
</template>

<script>
  import TopBar from "@/components/content/topbar/TopBar";
  import LoginItem from "@/views/login/childComps/LoginItem";

  export default {
    components: {
      TopBar,
      LoginItem
    },
    data() {
      return{
        // avatarUrl:'',
      }
    }

  };
</script>

<style scoped>
    .container {
        position: relative;
        width: 100%;
        height: calc(100vh - 48px);
        /*background-color: skyblue;*/
        background: url("../../assets/img/cool-background02.png");
        background-size: cover;
    }
    .register-login-box {
        position: absolute;
        width: 600px;
        padding: 30px;
        /*background-color: rgba(255,255,255,0.5);*/
        background-color: #fff;
        top: 50px;
        left: calc(50% - 300px);
        border-radius: 20px;
        box-sizing: border-box;
    }
</style>
