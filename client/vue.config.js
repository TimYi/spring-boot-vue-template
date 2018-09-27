module.exports = {

  chainWebpack: (config) => {
    if (process.env.NODE_ENV === 'production') {
      config
        .output
        .library('[name]')
        .publicPath('/')
        .end()
        .entry('config')
        .add('./src/config.js')
        .end()
        .plugin('html')
        .tap(args => {
          const first = args[0]
          first.chunksSortMode = function (chunk1, chunk2) {
            var order = ['config', 'app']
            var order1 = order.indexOf(chunk1.names[0])
            var order2 = order.indexOf(chunk2.names[0])
            return order1 - order2
          }
          return [first]
        })
    } else if (process.env.NODE_ENV === 'development') {
      config.output.globalObject('this')
    }
  }
}
