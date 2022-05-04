<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <div class="register-login-box">
        <register-item></register-item>
      </div>
    </div>
  </div>
</template>

<script>
  import TopBar from "@/components/content/topbar/TopBar";
  import RegisterItem from "@/views/register/childComps/RegisterItem";

  export default {
    components: {
      TopBar,
      RegisterItem
    },

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
        /*padding-left: 0px;*/
        /*background-color: rgba(255,255,255,0.5);*/
        background-color: #fff;
        top: 50px;
        left: calc(50% - 300px);
        border-radius: 20px;
        box-sizing: border-box;
    }


</style>
