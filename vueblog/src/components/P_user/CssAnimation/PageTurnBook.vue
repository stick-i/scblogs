<template>
<!-- 翻页书 -->
  <div class="contain">
      <div class="book">
          <div class="image" >
              <img :src="imgone" alt="">
          </div>
          <div class="text">
              <p>{{text}}</p>
          </div>
      </div>
  </div>
</template>

<script>
export default {
    name:'Book',
    props:{
        imgone:{
            type:String,
            default:'data:image/jpeg;base64,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',
            require:false,
        },
        text:{
            type:String,
            default:'长风破浪会有时,直挂云帆济沧海',
            require:false,
        }
    },
    data(){
        return {
        }
    }
}
</script>

<style>
    .book{
        width: 90px;
        height: 120px;
        background: rgb(255, 255, 255);
        transform-style: preserve-3d;
        transform:  perspective(-2000px);
        transition: all 0.4s;
        box-shadow: inset 90px 0 30px rgb(0, 0, 0,0.5),0 20px 100px rgb(0, 0, 0,0.5);
        text-align: center;
        position: relative;
    }
    .book:hover{
        transform: rotateZ(-20deg);
        box-shadow: inset 5px 0 30px  rgb(0, 0, 0,0.5),0 5px 100px  rgb(0, 0, 0,0.5);
    }
    /* 右边黑边 */
    .book:after{
        content: '';
        position: absolute;
        top: 0;
        right: -2px;
        width: 2px;
        height: 100%;
        border-radius: 0 2px 0 0;
        background: rgb(161, 161, 161);
        transform-origin: left;
        transform: skewY(-45deg);
    }
    .book .image{
       width: 100%;
       height: 100%;
       /* background: rgb(175, 9, 9); */
       position: absolute;
       top: 0;
       left: 0;
       transform-origin: left;
       transition: all .5s;
       color:black
    }
    .book .image img{
       width: 100%;
       height: 100%;
    }
    .book:hover .image{
       transform:rotateY(-140deg);
    }
    .book .text{
       padding: 10px;
    }
</style>
