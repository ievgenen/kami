import database from "../api/database";
import * as types from "../actions/ActionTypes"


const receiveAspects = aspects => ({
  type: types.GET_COMMON_ASPECTS,
  aspects
});

export const getCommonAspects = () => dispatch => {
  database.getCommonAspects(aspects => {
    dispatch(receiveAspects(aspects))
  })
};

export const loadAll = () => ({
  type: types.LOAD_ALL_ASPECTS
});