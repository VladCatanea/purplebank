"use client"

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import "./App.css"
import Home from "./Home"
import DepositList from "./DepositList"
import EditDeposit from "./EditDeposit"
import CreateSavings from "./CreateSavings"
import SavingsList from "./SavingsList"

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path='/deposits' exact={true} element={<DepositList/>}/>
        <Route path='/deposits/edit/:id' element={<EditDeposit/>}/>
        <Route path='/savings/create/:id' element={<CreateSavings/>}/>
        <Route path='/savings' exact={true} element={<SavingsList/>}/>
      </Routes>
    </Router>
  )
}

export default App
