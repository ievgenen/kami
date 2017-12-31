import React, {Component} from 'react';
import NavbarBrand from '../components/NavbarBrand';

import {
  Navbar
} from 'reactstrap';


class Header extends Component {
  render() {
    return (
      <header className={"app-header header"}>
          <Navbar light>
            <NavbarBrand/>
          </Navbar>
      </header>

    );
  }
}

export default Header;