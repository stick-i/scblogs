<template>
    <div class="fixed">
        <div class="round fixed-up">
            <a href="#"><i class="iconfont icon-erweima"></i></a>
        </div>
        <div class="round fixed-down">
            <a href="#"><i class="iconfont icon-kefu"></i><span>客服</span></a>
        </div> 
   </div>
</template>
<script>
export default {
    name:"FixedRightA"

}
</script>
<style >
.fixed{
    position: fixed;
    width: 50px;
    /* height: 200px; */
    right:40px;
    bottom:200px;
    /* background: white; */
}
.fixed i{
    font-size:35px;
    color: rgb(153, 154, 170);
}
.fixed .round{
    width:100%;
    height: 50px;
    background: white;
    border-radius:50% ;
    display: flex;
    justify-content: center;
    align-items: center;
    margin:10px 0;
    box-shadow: 0 2px 4px 0 rgb(0 0 0 / 5%);
}
.fixed .round a{
    position: relative;
    text-align: center;
    display: flex;
    flex-direction: column;
}
.fixed-down a i{
    position: absolute;
    bottom:-12px;
    background: white;
}
.fixed-down span{
    /* display: none; */
    /* visibility: hidden; */
    font-size: 16px;
    color:rgb(153, 154, 170);
    /* z-index: 0; */
}
.fixed-down:hover i{
    display: none;
}
</style>
