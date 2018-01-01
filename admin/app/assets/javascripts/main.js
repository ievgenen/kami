import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import { createLogger } from 'redux-logger';
import thunk from 'redux-thunk';
import reducer from './reducers';
import DataRetriever from '../javascripts/components/containers/DataRetriever';
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
    <DataRetriever/>
  </Provider>
  ),
  document.getElementById('app')
);

