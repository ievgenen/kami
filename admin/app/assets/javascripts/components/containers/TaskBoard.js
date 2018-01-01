import React from 'react';
import PropTypes from 'prop-types'
import { connect } from 'react-redux'

import ListHeader from "../presentation/task/ListHeader";
import TaskList from "../presentation/task/TaskList";

import {loadAll as loadAllAspects} from "../../actions/index"


const TaskBoard = ({tasks, onLoadAll}) => (
  <div className={'mb-3'}>
    <ListHeader caption={"Commons"} onLoadAll={onLoadAll} />
    <TaskList tasks={tasks}/>
  </div>
);

TaskBoard.propTypes = {
  tasks: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    count: PropTypes.number,
    updated: PropTypes.string,
    status: PropTypes.string
  })).isRequired
};

const getCommonAspects = (state) => {
  return state.commonAspects
};

const mapStateToProps = (state) => ({
  tasks: getCommonAspects(state)
});

const mapDispatchToProps = (dispatch) => {
      return {
        onLoadAll: () => {
          console.log("Load All Clicked!");
          dispatch(loadAllAspects)
        }
      }
    };

export default connect(mapStateToProps, mapDispatchToProps)(TaskBoard)
