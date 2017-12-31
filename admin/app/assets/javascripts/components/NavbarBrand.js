import React, {Component} from 'react';


class NavbarBrand extends Component {
  render() {
    return (
      <div className="navbar-header pull-left">
        <a href="#" className="navbar-brand">
          <small>
            <img src="assets/images/logo.png" alt="Workingstats" />
          </small>
        </a>
      </div>
    );
  }
};

export default NavbarBrand