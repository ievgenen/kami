import database from "../api/database";
import * as types from "../actions/ActionTypes"


const receiveAspects = aspects => ({
  type: types.GET_COMMON_ASPECTS,
  aspects
})

export const getCommonAspects = () => dispatch => {
  database.getCommonAspects(aspects => {
    dispatch(receiveAspects(aspects))
  })
};