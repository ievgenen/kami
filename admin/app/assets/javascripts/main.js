import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import { createLogger } from 'redux-logger';
import thunk from 'redux-thunk';
import reducer from './reducers';
import Collector from '../javascripts/containers/Collector';
import { getCommonAspects } from "../javascripts/actions/index"

const middleware = [thunk];
middleware.push(createLogger());

const store = createStore(
  reducer,
  applyMiddleware(...middleware)
);

store.dispatch(getCommonAspects());

ReactDOM.render((
  <Provider store={store}>
    <Collector/>
  </Provider>
  ),
  document.getElementById('app')
);

