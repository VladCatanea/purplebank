"use client"

import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';

const AppNavbar = () => {

  const [isOpen, setIsOpen] = useState(false);

  return (
    <Navbar color="dark" dark expand="md">
      <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
      <NavbarToggler onClick={() => { setIsOpen(!isOpen) }}/>
      <Collapse isOpen={isOpen} navbar>
        <Nav className="justify-content-end" style={{width: "100%"}} navbar>
          <NavItem>
            <NavbarBrand tag={Link} to="/deposits">Deposits</NavbarBrand>
          </NavItem>
          <NavItem>
            <NavbarBrand tag={Link} to="/savings">Savings</NavbarBrand>
          </NavItem>
          <NavItem>
            <NavbarBrand tag={Link} to="/accounts">Accounts</NavbarBrand>
          </NavItem>
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default AppNavbar;