"use client"

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import "./App.css"
import Home from "./Home"
import DepositList from "./DepositList"
import NewDeposit from "./NewDeposit"
import EditDeposit from "./EditDeposit"

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path='/deposits' exact={true} element={<DepositList/>}/>
        <Route path='/deposits/new' exact={true} element={<NewDeposit/>}/>
        <Route path='/deposits/edit/:id' element={<EditDeposit/>}/>
      </Routes>
    </Router>
  )
}

export default App
