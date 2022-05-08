const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
const webpack = require("webpack")
module.exports = {

  devServer: {
    proxy: {
      '/api': {
        // target:'https://192.168.201.130/api/v1',
        target: 'https://api.scblogs.cn/v1',// 后端接口
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '/api': ''
        }
      }
    }
  }
}
