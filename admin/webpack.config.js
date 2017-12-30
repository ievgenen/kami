const path = require('path');
const raw = 'public/javascripts';
const appPath = {
  output: path.resolve(__dirname, raw, 'dist'),
  appEntryPoint: path.join(path.join(__dirname, raw), 'main.js')
};

module.exports = {
  entry: {
    app: appPath.appEntryPoint
  },
  output: {
    path: appPath.output,
    filename: 'app.js',
    publicPath: ''
  },

  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            cacheDirectory: true,
            presets: ['react', 'env'],
            sourceMap: false
          }
        }
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      },
      {
        test: /\.scss$/,
        use: [{
          loader: 'style-loader'
        }, {
          loader: 'css-loader'
        }, {
          loader: 'sass-loader'
        }]
      },
      {
        test: /\.(png|jpg|jpeg|gif|ico)$/,
        use: [
          {
            // loader: 'url-loader'
            loader: 'file-loader',
          }
        ]
      },
      {
        test: /\.(ttf|eot|svg|woff(2)?)(\?[a-z0-9=&.]+)?$/,
        loader: 'file-loader'
      },
    ]
  },
  resolve: {
    extensions: ['.js', '.jsx']
  },
};