<template>
    <div class="bottom">
        <div class="content">
                <ul class="content-list">
                    <li><a href="#">关于我们</a></li>
                    <li><a href="#"><i class="iconfont icon-24gf-telephone2"></i>联系电话</a></li>
                    <li><a href="#"><i class="iconfont icon-a-youjianchakanyoujianfasongyoujianshouyoujian-06"></i>电子邮件</a></li>
                    <li><a href="#">工作时间</a></li>
                    <li><a href="#">商务合作</a></li>
                </ul>
        </div>
    </div>
</template>
<script>
    export default{
        name:"ButtomA"
    }
</script>

<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.bottom{
    width: 100%;
    height: 150px;
    background: rgb(255, 255, 255);
}
.content{
    height: 150px;
    margin:0 auto;
}
.content-list{
    width: 100%;
    height: 40px;
    display: flex;
    justify-content: center;
    margin: 20px auto; 
}
.content-list a{
    font-size: 16px; 
    color: rgb(153, 154, 170);
}
.content-list li{
    list-style: none;   
    float: left; 
    margin:0 10px;
}
</style>