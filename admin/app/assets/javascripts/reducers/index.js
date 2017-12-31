import { GET_COMMON_ASPECTS } from "../actions/ActionTypes"


const initialState = {
  commonAspects: []
};

const getAspects = (state = initialState, action) => {
  switch (action.type) {
    case GET_COMMON_ASPECTS:
      console.log(action.aspects);
      return action.aspects;
    default:
      return state
  }
};

export default getAspects