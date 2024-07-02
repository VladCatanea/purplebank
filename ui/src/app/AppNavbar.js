"use client"

import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem } from 'reactstrap';
import { useCookies } from 'react-cookie';

const AppNavbar = () => {

  const [isOpen, setIsOpen] = useState(false);
  const[cookies, setCookie, removeCookie] = useCookies(["loggedIn"])

  const logout = async (id) => {
    await fetch('/logout', {method: 'POST'})
    window.location.href="/"
    setCookie("loggedIn", false)
  }

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
          <NavItem onClick={logout}>
            <NavbarBrand tag={Link}>Logout</NavbarBrand>
          </NavItem>
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default AppNavbar;