"use client"

import React, { useEffect, useLayoutEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import "./App.css"
import Home from "./Home"
import DepositList from "./DepositList"

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path='/deposits' exact={true} element={<DepositList/>}/>
      </Routes>
    </Router>
  )
}

export default App
